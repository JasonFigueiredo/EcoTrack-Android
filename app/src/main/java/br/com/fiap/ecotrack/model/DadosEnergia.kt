package br.com.fiap.ecotrack.model

// Enum para período de tempo
enum class PeriodoTempo(val displayName: String, val multiplicador: Double) {
    HORA("Por Hora", 1.0),
    DIA("Por Dia", 24.0),
    MES("Por Mês", 24.0 * 30.0),
    ANO("Por Ano", 24.0 * 365.0)
}

// Request para API Climatiq
data class RequisicaoEmissaoEnergia(
    val emission_factor: FatorEmissaoEnergia,
    val parameters: ParametrosEnergia
)

data class FatorEmissaoEnergia(
    val activity_id: String,
    val data_version: String = "0.1.0"
)

data class ParametrosEnergia(
    val energy: Double,
    val energy_unit: String = "kWh"
)

// Response da API Climatiq
data class RespostaEmissaoEnergia(
    val co2e: Double,
    val co2e_unit: String,
    val emission_factor: FatorEmissaoEnergiaResposta
)

data class FatorEmissaoEnergiaResposta(
    val activity_id: String,
    val activity_name: String,
    val category: String,
    val source: String,
    val year: String,
    val region: String,
    val unit: String,
    val unit_type: String
)

// Resultado do cálculo de energia
data class ResultadoEmissaoEnergia(
    val tipoEnergia: TipoEnergia,
    val consumoKwh: Double,
    val horasUso: Double,
    val periodo: PeriodoTempo,
    val co2Emissoes: Double,
    val co2Unidade: String,
    val fatorEmissao: FatorEmissaoEnergiaResposta,
    val custoEstimado: Double? = null,
    val estadoSelecionado: String? = null,
    val tipoConsumidor: TipoConsumidor? = null
)

// Comparação de emissões de energia
data class ComparacaoEmissaoEnergia(
    val energiaSelecionada: ResultadoEmissaoEnergia,
    val alternativas: List<ResultadoEmissaoEnergia>,
    val economias: List<EconomiaEnergia>
)

// Economia de energia
data class EconomiaEnergia(
    val tipoEnergia: TipoEnergia,
    val co2Economizado: Double,
    val percentualEconomizado: Double,
    val beneficios: List<String>,
    val desvantagens: List<String>
)

// Estado global para energia
object EstadoEnergia {
    var comparacaoAtual: ComparacaoEmissaoEnergia? = null
        private set

    fun definirComparacaoEnergia(comparacao: ComparacaoEmissaoEnergia) {
        comparacaoAtual = comparacao
    }

    fun limparComparacaoEnergia() {
        comparacaoAtual = null
    }
}

annotation class DadosEnergia
