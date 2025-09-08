package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.model.*
import br.com.fiap.ecotrack.service.RepositorioEnergia
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCalculadoraEnergia(
    onVoltarClick: () -> Unit = {},
    onMostrarResultados: (ComparacaoEmissaoEnergia) -> Unit = {}
) {
    val repositorioEnergia = remember { RepositorioEnergia() }
    val tiposEnergiaDisponiveis = remember { getTiposEnergiaDisponiveis() }

    var tipoEnergiaSelecionado by remember { mutableStateOf<TipoEnergia?>(null) }
    var horasUso by remember { mutableStateOf("") }
    var periodoSelecionado by remember { mutableStateOf(PeriodoTempo.HORA) }
    var estadoSelecionado by remember { mutableStateOf("SP") }
    var tipoConsumidorSelecionado by remember { mutableStateOf(TipoConsumidor.RESIDENCIAL) }
    var carregando by remember { mutableStateOf(false) }
    var mensagemErro by remember { mutableStateOf("") }
    var deveCalcular by remember { mutableStateOf(false) }

    LaunchedEffect(deveCalcular) {
        if (deveCalcular && tipoEnergiaSelecionado != null && horasUso.isNotBlank()) {
            val horasUsoValor = horasUso.toDoubleOrNull()
            if (horasUsoValor != null && horasUsoValor > 0) {
                try {
                    carregando = true
                    mensagemErro = ""

                    // Calcular emissões para energia selecionada
                    val tipoEnergia = tipoEnergiaSelecionado ?: return@LaunchedEffect
                    val resultadoSelecionado = repositorioEnergia.calcularEmissoesEnergia(
                        tipoEnergia,
                        horasUsoValor,
                        periodoSelecionado,
                        estadoSelecionado,
                        tipoConsumidorSelecionado
                    ).getOrElse {
                        // Fallback para cálculo local
                        val consumoKwh =
                            (tipoEnergia.potenciaMedia * horasUsoValor * periodoSelecionado.multiplicador) / 1000.0
                        val custoEstimado = calcularCustoEnergia(consumoKwh, estadoSelecionado, tipoConsumidorSelecionado)
                        ResultadoEmissaoEnergia(
                            tipoEnergia = tipoEnergia,
                            consumoKwh = consumoKwh,
                            horasUso = horasUsoValor,
                            periodo = periodoSelecionado,
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
                            tipoConsumidor = tipoConsumidorSelecionado
                        )
                    }

                    // Calcular emissões para alternativas
                    val resultadosAlternativos = tiposEnergiaDisponiveis
                        .filter { it.id != tipoEnergia.id }
                        .map { tipoEnergia ->
                            repositorioEnergia.calcularEmissoesEnergia(
                                tipoEnergia,
                                horasUsoValor,
                                periodoSelecionado,
                                estadoSelecionado,
                                tipoConsumidorSelecionado
                            ).getOrElse {
                                // Fallback para cálculo local
                                val consumoKwh =
                                    (tipoEnergia.potenciaMedia * horasUsoValor * periodoSelecionado.multiplicador) / 1000.0
                                val custoEstimado = calcularCustoEnergia(consumoKwh, estadoSelecionado, tipoConsumidorSelecionado)
                                ResultadoEmissaoEnergia(
                                    tipoEnergia = tipoEnergia,
                                    consumoKwh = consumoKwh,
                                    horasUso = horasUsoValor,
                                    periodo = periodoSelecionado,
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
                                    tipoConsumidor = tipoConsumidorSelecionado
                                )
                            }
                        }

                    val comparacao = repositorioEnergia.criarComparacaoEnergia(
                        resultadoSelecionado,
                        resultadosAlternativos
                    )

                    EstadoEnergia.definirComparacaoEnergia(comparacao)

                    carregando = false
                    deveCalcular = false
                    onMostrarResultados(comparacao)
                } catch (e: Exception) {
                    carregando = false
                    deveCalcular = false
                    mensagemErro = "Erro ao calcular emissões: ${e.message ?: "Erro desconhecido"}"
                }
            } else {
                carregando = false
                deveCalcular = false
                mensagemErro = "Por favor, insira um número válido de horas"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Calculadora de Energia",
                    color = EcoTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onVoltarClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = EcoTextPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = EcoDark
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Título
            Text(
                text = "Calcule suas emissões de energia",
                color = EcoTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Selecione o aparelho e o tempo de uso",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de horas de uso
            OutlinedTextField(
                value = horasUso,
                onValueChange = { horasUso = it },
                label = { Text("Horas de uso", color = EcoTextSecondary) },
                placeholder = { Text("Ex: 2", color = EcoTextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = EcoTextPrimary,
                    unfocusedTextColor = EcoTextPrimary,
                    focusedBorderColor = EcoGreen,
                    unfocusedBorderColor = EcoTextSecondary,
                    focusedLabelColor = EcoGreen,
                    unfocusedLabelColor = EcoTextSecondary
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            // Debug: mostrar horas digitadas
            if (horasUso.isNotBlank()) {
                val horasUsoValor = horasUso.toDoubleOrNull()
                if (horasUsoValor != null && horasUsoValor > 0) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = EcoGreen.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = "Horas",
                                tint = EcoGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Horas: $horasUsoValor ${periodoSelecionado.displayName.lowercase()}",
                                color = EcoGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Seleção de período
            Text(
                text = "Período de Cálculo",
                color = EcoTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Seleção de Período
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = EcoDarkSurface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Período de Cálculo",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = EcoTextPrimary
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            PeriodoTempo.values().forEach { periodo ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected = (periodoSelecionado == periodo),
                                            onClick = { periodoSelecionado = periodo }
                                        )
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = (periodoSelecionado == periodo),
                                        onClick = { periodoSelecionado = periodo },
                                        colors = RadioButtonDefaults.colors(selectedColor = EcoGreen)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = periodo.displayName,
                                        fontSize = 16.sp,
                                        color = EcoTextPrimary
                                    )
                                }
                            }
                        }
                    }
                }

                // Seleção de Estado
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = EcoDarkSurface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Estado/Região",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = EcoTextPrimary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            val estados = getTarifasPorEstado()
                            val estadosAgrupados = estados.groupBy { it.regiao }
                            
                            estadosAgrupados.forEach { (regiao, estadosRegiao) ->
                                Text(
                                    text = regiao,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = EcoGreen,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                                
                                estadosRegiao.forEach { estado ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .selectable(
                                                selected = (estadoSelecionado == estado.sigla),
                                                onClick = { estadoSelecionado = estado.sigla }
                                            )
                                            .padding(vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = (estadoSelecionado == estado.sigla),
                                            onClick = { estadoSelecionado = estado.sigla },
                                            colors = RadioButtonDefaults.colors(selectedColor = EcoGreen)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "${estado.sigla} - ${estado.estado}",
                                            fontSize = 14.sp,
                                            color = EcoTextPrimary
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }

                // Seleção de Tipo de Consumidor
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = EcoDarkSurface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Tipo de Consumidor",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = EcoTextPrimary
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            TipoConsumidor.values().forEach { tipo ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected = (tipoConsumidorSelecionado == tipo),
                                            onClick = { tipoConsumidorSelecionado = tipo }
                                        )
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = (tipoConsumidorSelecionado == tipo),
                                        onClick = { tipoConsumidorSelecionado = tipo },
                                        colors = RadioButtonDefaults.colors(selectedColor = EcoGreen)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = tipo.displayName,
                                        fontSize = 16.sp,
                                        color = EcoTextPrimary
                                    )
                                }
                            }
                        }
                    }
                }

                // Seleção de Tipo de Energia
                item {
                    Text(
                        text = "Tipo de Aparelho",
                        color = EcoTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Debug: mostrar seleção atual
                tipoEnergiaSelecionado?.let { aparelho ->
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = EcoGreen.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Selecionado",
                                    tint = EcoGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Selecionado: ${aparelho.nome}",
                                    color = EcoGreen,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // Lista de tipos de energia
                items(tiposEnergiaDisponiveis) { tipoEnergia ->
                    CardoSelecaoEnergia(
                        tipoEnergia = tipoEnergia,
                        selecionado = tipoEnergiaSelecionado?.id == tipoEnergia.id,
                        onClick = { tipoEnergiaSelecionado = tipoEnergia }
                    )
                }
            }

            // Mensagem de erro
            if (mensagemErro.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = EcoError.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Erro",
                            tint = EcoError,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = mensagemErro,
                            color = EcoError,
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Indicador de status
            if (tipoEnergiaSelecionado != null && horasUso.isNotBlank()) {
                val horasUsoValor = horasUso.toDoubleOrNull()
                if (horasUsoValor != null && horasUsoValor > 0) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = EcoGreen.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Pronto",
                                tint = EcoGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Pronto para calcular! ${tipoEnergiaSelecionado?.nome ?: "Aparelho"} - ${horasUsoValor}h ${periodoSelecionado.displayName.lowercase()}",
                                color = EcoGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Botão de calcular
            Button(
                onClick = {
                    if (tipoEnergiaSelecionado != null && horasUso.isNotBlank()) {
                        val horasUsoValor = horasUso.toDoubleOrNull()
                        if (horasUsoValor != null && horasUsoValor > 0) {
                            carregando = true
                            mensagemErro = ""
                            deveCalcular = true
                        } else {
                            mensagemErro = "Por favor, insira um número válido de horas"
                        }
                    } else {
                        mensagemErro = "Por favor, selecione um aparelho e insira as horas de uso"
                    }
                },
                enabled = !carregando && tipoEnergiaSelecionado != null && horasUso.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EcoGreen,
                    disabledContainerColor = EcoTextSecondary
                )
            ) {
                if (carregando) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = EcoTextOnGreen,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = if (carregando) "Calculando..." else "Calcular Emissões",
                    color = if (carregando) EcoTextSecondary else EcoTextOnGreen,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun CardoSelecaoEnergia(
    tipoEnergia: TipoEnergia,
    selecionado: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = if (selecionado) CardDefaults.cardElevation(defaultElevation = 8.dp) else CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selecionado) EcoGreen.copy(alpha = 0.2f) else EcoDarkSurface
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = tipoEnergia.icone,
                contentDescription = null,
                tint = if (selecionado) EcoGreen else tipoEnergia.cor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = tipoEnergia.nome,
                    fontSize = 16.sp,
                    fontWeight = if (selecionado) FontWeight.Bold else FontWeight.Normal,
                    color = if (selecionado) EcoGreen else EcoTextPrimary
                )
                Text(
                    text = tipoEnergia.descricao,
                    fontSize = 12.sp,
                    color = EcoTextSecondary
                )
                Text(
                    text = "Potência: ${tipoEnergia.potenciaMedia}W | CO₂: ${tipoEnergia.co2PorKwh} kg/kWh",
                    fontSize = 11.sp,
                    color = if (selecionado) EcoGreen else EcoTextSecondary,
                    fontWeight = if (selecionado) FontWeight.Medium else FontWeight.Normal
                )
            }
            if (selecionado) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Selecionado",
                    tint = EcoGreen,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

