package br.com.fiap.ecotrack.service

import android.util.Log
import br.com.fiap.ecotrack.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositorioEnergia {
    private val chaveApi = "EWN9THXE5X3TNBG9ZD5KJ0VM0R"
    private val urlBase = "https://beta3.api.climatiq.io/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val servicoApi = retrofit.create(ServicoApiEnergia::class.java)

    suspend fun calcularEmissoesEnergia(
        tipoEnergia: TipoEnergia,
        horasUso: Double,
        periodo: PeriodoTempo,
        estadoSelecionado: String = "SP",
        tipoConsumidor: TipoConsumidor = TipoConsumidor.RESIDENCIAL
    ): Result<ResultadoEmissaoEnergia> = withContext(Dispatchers.IO) {
        Log.d("Energia", "⚡ Calculando emissões para: ${tipoEnergia.nome} - $horasUso horas - ${periodo.displayName}")
        try {
            // Calcular consumo em kWh
            val consumoKwh = (tipoEnergia.potenciaMedia * horasUso * periodo.multiplicador) / 1000.0
            
            // Calcular custo estimado
            val custoEstimado = calcularCustoEnergia(consumoKwh, estadoSelecionado, tipoConsumidor)
            
            val requisicao = RequisicaoEmissaoEnergia(
                emission_factor = FatorEmissaoEnergia(
                    activity_id = tipoEnergia.climatiqActivityId
                ),
                parameters = ParametrosEnergia(energy = consumoKwh)
            )
            
            Log.d("Energia", "📡 Chamando API: ${urlBase}estimate com activity_id: ${tipoEnergia.climatiqActivityId}, energy: $consumoKwh kWh")

            val resposta = servicoApi.estimarEmissoesEnergia(
                authHeader = "Bearer $chaveApi",
                request = requisicao
            )

            if (resposta.isSuccessful) {
                val respostaEmissao = resposta.body()!!
                Log.d("Energia", "✅ Resposta da API: ${respostaEmissao.co2e} ${respostaEmissao.co2e_unit}")
                
                val resultado = ResultadoEmissaoEnergia(
                    tipoEnergia = tipoEnergia,
                    consumoKwh = consumoKwh,
                    horasUso = horasUso,
                    periodo = periodo,
                    co2Emissoes = respostaEmissao.co2e,
                    co2Unidade = respostaEmissao.co2e_unit,
                    fatorEmissao = respostaEmissao.emission_factor,
                    custoEstimado = custoEstimado,
                    estadoSelecionado = estadoSelecionado,
                    tipoConsumidor = tipoConsumidor
                )
                Result.success(resultado)
            } else {
                val corpoErro = resposta.errorBody()?.string() ?: "N/A"
                Log.e("Energia", "❌ Erro na API: ${resposta.code()} - ${resposta.message()} - Body: $corpoErro")
                
                // Fallback para cálculo local
                val resultadoLocal = calcularEmissaoLocal(tipoEnergia, horasUso, periodo, consumoKwh, custoEstimado, estadoSelecionado, tipoConsumidor)
                Result.success(resultadoLocal)
            }
        } catch (e: Exception) {
            Log.e("Energia", "⚠️ Exceção ao chamar API: ${e.message}", e)
            
            // Fallback para cálculo local
            val consumoKwh = (tipoEnergia.potenciaMedia * horasUso * periodo.multiplicador) / 1000.0
            val custoEstimado = calcularCustoEnergia(consumoKwh, estadoSelecionado, tipoConsumidor)
            val resultadoLocal = calcularEmissaoLocal(tipoEnergia, horasUso, periodo, consumoKwh, custoEstimado, estadoSelecionado, tipoConsumidor)
            Result.success(resultadoLocal)
        }
    }

    private fun calcularEmissaoLocal(
        tipoEnergia: TipoEnergia,
        horasUso: Double,
        periodo: PeriodoTempo,
        consumoKwh: Double,
        custoEstimado: Double?,
        estadoSelecionado: String,
        tipoConsumidor: TipoConsumidor
    ): ResultadoEmissaoEnergia {
        Log.d("Energia", "🔄 Usando cálculo local para ${tipoEnergia.nome}")
        
        return ResultadoEmissaoEnergia(
            tipoEnergia = tipoEnergia,
            consumoKwh = consumoKwh,
            horasUso = horasUso,
            periodo = periodo,
            co2Emissoes = consumoKwh * tipoEnergia.co2PorKwh,
            co2Unidade = "kg",
            fatorEmissao = FatorEmissaoEnergiaResposta(
                activity_id = tipoEnergia.climatiqActivityId,
                activity_name = tipoEnergia.nome,
                category = "electricity",
                source = "local_calculation",
                year = "2024",
                region = "BR",
                unit = "kWh",
                unit_type = "energy"
            ),
            custoEstimado = custoEstimado,
            estadoSelecionado = estadoSelecionado,
            tipoConsumidor = tipoConsumidor
        )
    }

    fun criarComparacaoEnergia(
        resultadoSelecionado: ResultadoEmissaoEnergia,
        resultadosAlternativos: List<ResultadoEmissaoEnergia>
    ): ComparacaoEmissaoEnergia {
        val economias = resultadosAlternativos.map { alternativa ->
            val co2Economizado = resultadoSelecionado.co2Emissoes - alternativa.co2Emissoes
            val percentualEconomizado = if (resultadoSelecionado.co2Emissoes > 0) {
                (co2Economizado / resultadoSelecionado.co2Emissoes) * 100
            } else 0.0
            
            EconomiaEnergia(
                tipoEnergia = alternativa.tipoEnergia,
                co2Economizado = co2Economizado,
                percentualEconomizado = percentualEconomizado,
                beneficios = obterBeneficiosEnergia(alternativa.tipoEnergia),
                desvantagens = obterDesvantagensEnergia(alternativa.tipoEnergia)
            )
        }.filter { it.co2Economizado > 0 }
        
        return ComparacaoEmissaoEnergia(
            energiaSelecionada = resultadoSelecionado,
            alternativas = resultadosAlternativos,
            economias = economias
        )
    }
    
    private fun obterBeneficiosEnergia(tipoEnergia: TipoEnergia): List<String> {
        return when (tipoEnergia.id) {
            "energia_solar" -> listOf(
                "Zero emissões de CO₂",
                "Energia renovável e limpa",
                "Redução na conta de luz",
                "Independência energética",
                "Baixa manutenção"
            )
            "energia_eolica" -> listOf(
                "Zero emissões de CO₂",
                "Energia renovável",
                "Fonte inesgotável",
                "Baixo impacto ambiental",
                "Tecnologia madura"
            )
            "luzes_led" -> listOf(
                "Baixo consumo energético",
                "Longa durabilidade",
                "Menor geração de calor",
                "Economia na conta de luz",
                "Alta eficiência luminosa"
            )
            "geladeira" -> listOf(
                "Eficiência energética A++",
                "Tecnologia inverter",
                "Baixo consumo",
                "Controle de temperatura preciso"
            )
            "ar_condicionado" -> listOf(
                "Tecnologia inverter",
                "Eficiência energética",
                "Controle de temperatura preciso",
                "Filtros de ar"
            )
            else -> listOf("Alternativa energética disponível")
        }
    }
    
    private fun obterDesvantagensEnergia(tipoEnergia: TipoEnergia): List<String> {
        return when (tipoEnergia.id) {
            "energia_solar" -> listOf(
                "Custo inicial alto",
                "Depende da luz solar",
                "Requer espaço adequado",
                "Necessita baterias para armazenamento",
                "Manutenção periódica"
            )
            "energia_eolica" -> listOf(
                "Depende do vento",
                "Custo inicial alto",
                "Requer espaço adequado",
                "Pode gerar ruído",
                "Impacto visual"
            )
            "luzes_led" -> listOf(
                "Custo inicial maior",
                "Sensibilidade à temperatura",
                "Pode causar desconforto visual"
            )
            "geladeira" -> listOf(
                "Consumo contínuo",
                "Geração de calor",
                "Necessita manutenção"
            )
            "ar_condicionado" -> listOf(
                "Alto consumo energético",
                "Geração de calor externo",
                "Necessita manutenção",
                "Pode causar ressecamento do ar"
            )
            else -> listOf("Algumas limitações podem existir")
        }
    }
}
