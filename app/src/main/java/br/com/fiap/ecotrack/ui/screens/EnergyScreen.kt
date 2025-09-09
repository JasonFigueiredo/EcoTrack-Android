package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*

enum class PeriodoFiltro {
    DIA, MES, ANO
}

enum class EstadoFiltro {
    SP, RJ, MG, RS, PR, SC, BA, GO, PE, CE
}

enum class TipoConsumidorFiltro {
    RESIDENCIAL, COMERCIAL, INDUSTRIAL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnergyScreen(
    onBackClick: () -> Unit = {},
    onAddEnergy: () -> Unit = {},
    onCalculadoraEnergia: () -> Unit = {}
) {
    var periodoSelecionado by remember { mutableStateOf(PeriodoFiltro.DIA) }
    var estadoSelecionado by remember { mutableStateOf(EstadoFiltro.SP) }
    var tipoConsumidorSelecionado by remember { mutableStateOf(TipoConsumidorFiltro.RESIDENCIAL) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Energia",
                    color = EcoTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        contentDescription = "Voltar",
                        tint = EcoTextPrimary,
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack
                    )
                }
            },
            actions = {
                IconButton(onClick = onCalculadoraEnergia) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = "Calculadora de Energia",
                        tint = EcoGreen
                    )
                }
                IconButton(onClick = onAddEnergy) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Help,
                        contentDescription = "Adicionar",
                        tint = EcoGreen
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = EcoDark
            )
        )
        
        // Conteúdo com scroll suave
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 100.dp)
                .padding(bottom = 45.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Filtros de período
            item {
                PeriodoFiltroCard(
                    periodoSelecionado = periodoSelecionado,
                    onPeriodoChange = { periodoSelecionado = it }
                )
            }
            
            // Filtros de estado e tipo de consumidor
            item {
                EstadoTipoFiltroCard(
                    estadoSelecionado = estadoSelecionado,
                    tipoConsumidorSelecionado = tipoConsumidorSelecionado,
                    onEstadoChange = { estadoSelecionado = it },
                    onTipoConsumidorChange = { tipoConsumidorSelecionado = it }
                )
            }
            
            // Resumo principal com gráfico
            item {
                EnergySummaryCard(
                    periodo = periodoSelecionado,
                    estado = estadoSelecionado,
                    tipoConsumidor = tipoConsumidorSelecionado
                )
            }
            
            // Gráfico de consumo por aparelho
            item {
                ConsumoAparelhosChart(
                    periodo = periodoSelecionado,
                    estado = estadoSelecionado,
                    tipoConsumidor = tipoConsumidorSelecionado
                )
            }
            
            // Insights importantes
            item {
                EnergyInsightsCard(
                    periodo = periodoSelecionado,
                    estado = estadoSelecionado,
                    tipoConsumidor = tipoConsumidorSelecionado
                )
            }
            
            // Lista de aparelhos com dados detalhados
            item {
                Text(
                    text = "Aparelhos Elétricos",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(getEnergyItems(periodoSelecionado, estadoSelecionado, tipoConsumidorSelecionado)) { energy ->
                EnergyCard(energy = energy)
            }
        }
    }
}

@Composable
fun PeriodoFiltroCard(
    periodoSelecionado: PeriodoFiltro,
    onPeriodoChange: (PeriodoFiltro) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PeriodoFiltro.values().forEach { periodo ->
                FilterChip(
                    onClick = { onPeriodoChange(periodo) },
                    label = {
                        Text(
                            text = when (periodo) {
                                PeriodoFiltro.DIA -> "Dia"
                                PeriodoFiltro.MES -> "Mês"
                                PeriodoFiltro.ANO -> "Ano"
                            },
                            color = if (periodoSelecionado == periodo) EcoTextOnGreen else EcoTextPrimary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(Alignment.CenterVertically)
                        )
                    },
                    selected = periodoSelecionado == periodo,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EcoGreen,
                        containerColor = EcoDarkSurface
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun EstadoTipoFiltroCard(
    estadoSelecionado: EstadoFiltro,
    tipoConsumidorSelecionado: TipoConsumidorFiltro,
    onEstadoChange: (EstadoFiltro) -> Unit,
    onTipoConsumidorChange: (TipoConsumidorFiltro) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Filtro de Estado
            Text(
                text = "Estado",
                color = EcoTextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Primeira linha de estados
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    EstadoFiltro.values().take(5).forEach { estado ->
                        FilterChip(
                            onClick = { onEstadoChange(estado) },
                            label = {
                                Text(
                                    text = estado.name,
                                    color = if (estadoSelecionado == estado) EcoTextOnGreen else EcoTextPrimary,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(Alignment.CenterVertically)
                                )
                            },
                            selected = estadoSelecionado == estado,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EcoGreen,
                                containerColor = EcoDarkSurface
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Segunda linha de estados
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    EstadoFiltro.values().drop(5).forEach { estado ->
                        FilterChip(
                            onClick = { onEstadoChange(estado) },
                            label = {
                                Text(
                                    text = estado.name,
                                    color = if (estadoSelecionado == estado) EcoTextOnGreen else EcoTextPrimary,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(Alignment.CenterVertically)
                                )
                            },
                            selected = estadoSelecionado == estado,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EcoGreen,
                                containerColor = EcoDarkSurface
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Filtro de Tipo de Consumidor
            Text(
                text = "Tipo de Consumidor",
                color = EcoTextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TipoConsumidorFiltro.values().forEach { tipo ->
                    FilterChip(
                        onClick = { onTipoConsumidorChange(tipo) },
                        label = {
                            Text(
                                text = when (tipo) {
                                    TipoConsumidorFiltro.RESIDENCIAL -> "Residencial"
                                    TipoConsumidorFiltro.COMERCIAL -> "Comercial"
                                    TipoConsumidorFiltro.INDUSTRIAL -> "Industrial"
                                },
                                color = if (tipoConsumidorSelecionado == tipo) EcoTextOnGreen else EcoTextPrimary,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(Alignment.CenterVertically)
                            )
                        },
                        selected = tipoConsumidorSelecionado == tipo,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EcoGreen,
                            containerColor = EcoDarkSurface
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun EnergySummaryCard(
    periodo: PeriodoFiltro,
    estado: EstadoFiltro,
    tipoConsumidor: TipoConsumidorFiltro
) {
    // Dados baseados no estado e tipo de consumidor
    val tarifaPorEstado = when (estado) {
        EstadoFiltro.SP -> 0.70
        EstadoFiltro.RJ -> 0.75
        EstadoFiltro.MG -> 0.68
        EstadoFiltro.RS -> 0.72
        EstadoFiltro.PR -> 0.65
        EstadoFiltro.SC -> 0.69
        EstadoFiltro.BA -> 0.73
        EstadoFiltro.GO -> 0.66
        EstadoFiltro.PE -> 0.74
        EstadoFiltro.CE -> 0.71
    }
    
    val multiplicadorTipo = when (tipoConsumidor) {
        TipoConsumidorFiltro.RESIDENCIAL -> 1.0
        TipoConsumidorFiltro.COMERCIAL -> 1.5
        TipoConsumidorFiltro.INDUSTRIAL -> 2.0
    }
    
    val (consumo, custo, unidade) = when (periodo) {
        PeriodoFiltro.DIA -> Triple(
            "${String.format("%.1f", 12.5 * multiplicadorTipo)}",
            "${String.format("%.2f", 12.5 * multiplicadorTipo * tarifaPorEstado)}",
            "kWh/dia"
        )
        PeriodoFiltro.MES -> Triple(
            "${String.format("%.1f", 375.0 * multiplicadorTipo)}",
            "${String.format("%.2f", 375.0 * multiplicadorTipo * tarifaPorEstado)}",
            "kWh/mês"
        )
        PeriodoFiltro.ANO -> Triple(
            "${String.format("%.1f", 4562.5 * multiplicadorTipo)}",
            "${String.format("%.2f", 4562.5 * multiplicadorTipo * tarifaPorEstado)}",
            "kWh/ano"
        )
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Consumo de Energia",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "$consumo $unidade",
                        color = EcoGreen,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Custo Total",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "R$ $custo",
                        color = EcoTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Informações adicionais
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Tarifa Média",
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "R$ ${String.format("%.2f", tarifaPorEstado)}/kWh",
                        color = EcoGreenAccent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Estado: ${estado.name}",
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = when (tipoConsumidor) {
                            TipoConsumidorFiltro.RESIDENCIAL -> "Residencial"
                            TipoConsumidorFiltro.COMERCIAL -> "Comercial"
                            TipoConsumidorFiltro.INDUSTRIAL -> "Industrial"
                        },
                        color = EcoGreenAccent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun ConsumoAparelhosChart(
    periodo: PeriodoFiltro,
    estado: EstadoFiltro,
    tipoConsumidor: TipoConsumidorFiltro
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = "Gráfico",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Consumo por Aparelho",
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Gráfico de barras melhorado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                EnergyBar("Ar Cond.", 0.8f, EcoGreen, "4.5 kWh")
                EnergyBar("Geladeira", 0.6f, EcoGreenLight, "2.4 kWh")
                EnergyBar("TV", 0.3f, EcoGreenAccent, "0.6 kWh")
                EnergyBar("Máq. Lavar", 0.7f, EcoWarning, "3.2 kWh")
                EnergyBar("Micro-ondas", 0.2f, EcoError, "0.8 kWh")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Legenda com dados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total: ${when (periodo) {
                        PeriodoFiltro.DIA -> "12.5 kWh"
                        PeriodoFiltro.MES -> "375.0 kWh"
                        PeriodoFiltro.ANO -> "4562.5 kWh"
                    }}",
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = "Média SP: ${when (periodo) {
                        PeriodoFiltro.DIA -> "15.2 kWh"
                        PeriodoFiltro.MES -> "456.0 kWh"
                        PeriodoFiltro.ANO -> "5472.0 kWh"
                    }}",
                    color = EcoGreenAccent,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun EnergyInsightsCard(
    periodo: PeriodoFiltro,
    estado: EstadoFiltro,
    tipoConsumidor: TipoConsumidorFiltro
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = "Insights",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Insights Importantes",
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val insights = when (periodo) {
                PeriodoFiltro.DIA -> listOf(
                    "Seu consumo está 18% abaixo da média estadual",
                    "Ar condicionado representa 36% do seu consumo diário",
                    "Economia potencial: R$ 1.58/dia com eficiência energética"
                )
                PeriodoFiltro.MES -> listOf(
                    "Consumo mensal 18% menor que a média de SP",
                    "Geladeira antiga pode estar consumindo 30% mais energia",
                    "Economia potencial: R$ 47.40/mês com aparelhos eficientes"
                )
                PeriodoFiltro.ANO -> listOf(
                    "Consumo anual 17% abaixo da média estadual",
                    "Substituir aparelhos antigos pode economizar R$ 568.80/ano",
                    "Sua pegada de carbono é 2.3 toneladas CO₂/ano"
                )
            }
            
            insights.forEach { insight ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Insight",
                        tint = EcoGreenAccent,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = insight,
                        color = EcoTextSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun EnergyBar(
    label: String,
    height: Float,
    color: Color,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(35.dp)
                .height((height * 80).dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(4.dp)
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = EcoTextSecondary,
            fontSize = 9.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = value,
            color = color,
            fontSize = 8.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun EnergyCard(
    energy: EnergyItem
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = energy.icon,
                    contentDescription = energy.type,
                    tint = energy.color,
                    modifier = Modifier.size(32.dp)
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = energy.type,
                        color = EcoTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = energy.details,
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = energy.consumption,
                        color = energy.color,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = energy.duration,
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Informações adicionais
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Custo",
                        color = EcoTextSecondary,
                        fontSize = 10.sp
                    )
                    Text(
                        text = energy.cost,
                        color = EcoTextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Potência",
                        color = EcoTextSecondary,
                        fontSize = 10.sp
                    )
                    Text(
                        text = energy.power,
                        color = EcoGreenAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Eficiência",
                        color = EcoTextSecondary,
                        fontSize = 10.sp
                    )
                    Text(
                        text = energy.efficiency,
                        color = if (energy.efficiency.contains("A")) EcoGreen else EcoWarning,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

data class EnergyItem(
    val type: String,
    val details: String,
    val icon: ImageVector,
    val color: Color,
    val consumption: String,
    val duration: String,
    val cost: String,
    val power: String,
    val efficiency: String
)

fun getEnergyItems(
    periodo: PeriodoFiltro,
    estado: EstadoFiltro,
    tipoConsumidor: TipoConsumidorFiltro
): List<EnergyItem> {
    val multiplicador = when (periodo) {
        PeriodoFiltro.DIA -> 1.0
        PeriodoFiltro.MES -> 30.0
        PeriodoFiltro.ANO -> 365.0
    }
    
    val tarifaPorEstado = when (estado) {
        EstadoFiltro.SP -> 0.70
        EstadoFiltro.RJ -> 0.75
        EstadoFiltro.MG -> 0.68
        EstadoFiltro.RS -> 0.72
        EstadoFiltro.PR -> 0.65
        EstadoFiltro.SC -> 0.69
        EstadoFiltro.BA -> 0.73
        EstadoFiltro.GO -> 0.66
        EstadoFiltro.PE -> 0.74
        EstadoFiltro.CE -> 0.71
    }
    
    val multiplicadorTipo = when (tipoConsumidor) {
        TipoConsumidorFiltro.RESIDENCIAL -> 1.0
        TipoConsumidorFiltro.COMERCIAL -> 1.5
        TipoConsumidorFiltro.INDUSTRIAL -> 2.0
    }
    
    return listOf(
        EnergyItem(
            type = "Ar Condicionado",
            details = "Sala de estar - 12.000 BTU",
            icon = Icons.Default.AcUnit,
            color = EcoGreen,
            consumption = "${String.format("%.1f", 4.5 * multiplicador)} kWh",
            duration = "${(3 * multiplicador).toInt()} horas",
            cost = "R$ ${String.format("%.2f", 4.5 * multiplicador * multiplicadorTipo * tarifaPorEstado)}",
            power = "1.500W",
            efficiency = "Classe A"
        ),
        EnergyItem(
            type = "Geladeira",
            details = "Cozinha - 2 portas",
            icon = Icons.Default.Kitchen,
            color = EcoGreenLight,
            consumption = "${String.format("%.1f", 2.4 * multiplicador)} kWh",
            duration = "${(24 * multiplicador).toInt()} horas",
            cost = "R$ ${String.format("%.2f", 2.4 * multiplicador * multiplicadorTipo * tarifaPorEstado)}",
            power = "100W",
            efficiency = "Classe A+"
        ),
        EnergyItem(
            type = "TV LED",
            details = "Sala de estar - 55\"",
            icon = Icons.Default.Tv,
            color = EcoGreenAccent,
            consumption = "${String.format("%.1f", 0.6 * multiplicador)} kWh",
            duration = "${(3 * multiplicador).toInt()} horas",
            cost = "R$ ${String.format("%.2f", 0.6 * multiplicador * multiplicadorTipo * tarifaPorEstado)}",
            power = "200W",
            efficiency = "Classe A"
        ),
        EnergyItem(
            type = "Máquina de Lavar",
            details = "Área de serviço - 8kg",
            icon = Icons.Default.LocalLaundryService,
            color = EcoWarning,
            consumption = "${String.format("%.1f", 3.2 * multiplicador)} kWh",
            duration = "${(2 * multiplicador).toInt()} horas",
            cost = "R$ ${String.format("%.2f", 3.2 * multiplicador * multiplicadorTipo * tarifaPorEstado)}",
            power = "1.600W",
            efficiency = "Classe B"
        ),
        EnergyItem(
            type = "Micro-ondas",
            details = "Cozinha - 30L",
            icon = Icons.Default.Microwave,
            color = EcoError,
            consumption = "${String.format("%.1f", 0.8 * multiplicador)} kWh",
            duration = "${(1 * multiplicador).toInt()} horas",
            cost = "R$ ${String.format("%.2f", 0.8 * multiplicador * multiplicadorTipo * tarifaPorEstado)}",
            power = "800W",
            efficiency = "Classe A"
        ),
        EnergyItem(
            type = "Computador",
            details = "Escritório - Desktop",
            icon = Icons.Default.Computer,
            color = EcoGreen,
            consumption = "${String.format("%.1f", 1.0 * multiplicador)} kWh",
            duration = "${(4 * multiplicador).toInt()} horas",
            cost = "R$ ${String.format("%.2f", 1.0 * multiplicador * multiplicadorTipo * tarifaPorEstado)}",
            power = "250W",
            efficiency = "Classe A"
        ),
        EnergyItem(
            type = "Chuveiro Elétrico",
            details = "Banheiro - 5.500W",
            icon = Icons.Default.Shower,
            color = EcoGreenLight,
            consumption = "${String.format("%.1f", 1.1 * multiplicador)} kWh",
            duration = "${(1 * multiplicador).toInt()} horas",
            cost = "R$ ${String.format("%.2f", 1.1 * multiplicador * multiplicadorTipo * tarifaPorEstado)}",
            power = "5.500W",
            efficiency = "Classe C"
        ),
        EnergyItem(
            type = "Forno Elétrico",
            details = "Cozinha - 60L",
            icon = Icons.Default.LocalPizza,
            color = EcoGreenAccent,
            consumption = "${String.format("%.1f", 1.5 * multiplicador)} kWh",
            duration = "${(1 * multiplicador).toInt()} horas",
            cost = "R$ ${String.format("%.2f", 1.5 * multiplicador * multiplicadorTipo * tarifaPorEstado)}",
            power = "1.500W",
            efficiency = "Classe A"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun EnergyScreenPreview() {
    EcoTrackTheme {
        EnergyScreen()
    }
}