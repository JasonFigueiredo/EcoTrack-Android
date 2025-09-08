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
import br.com.fiap.ecotrack.model.getAvailableTransportTypes

enum class PeriodoTransporteFiltro {
    DIA, SEMANA, MES, ANO
}

enum class TipoVeiculoFiltro {
    CARRO, MOTO, ONIBUS, METRO, BICICLETA, CAMINHAO
}

enum class TipoUsoFiltro {
    URBANO, RODOVIARIO, MISTO
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransportScreen(
    onBackClick: () -> Unit = {},
    onAddTransport: () -> Unit = {},
    onEmissionCalculator: () -> Unit = {}
) {
    var periodoSelecionado by remember { mutableStateOf(PeriodoTransporteFiltro.DIA) }
    var tipoVeiculoSelecionado by remember { mutableStateOf(TipoVeiculoFiltro.CARRO) }
    var tipoUsoSelecionado by remember { mutableStateOf(TipoUsoFiltro.URBANO) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Transporte",
                    color = EcoTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = EcoTextPrimary
                    )
                }
            },
            actions = {
                IconButton(onClick = onEmissionCalculator) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = "Calculadora de Emissões",
                        tint = EcoGreen
                    )
                }
                IconButton(onClick = onAddTransport) {
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
                PeriodoTransporteFiltroCard(
                    periodoSelecionado = periodoSelecionado,
                    onPeriodoChange = { periodoSelecionado = it }
                )
            }
            
            // Filtros de tipo de veículo e uso
            item {
                TipoVeiculoUsoFiltroCard(
                    tipoVeiculoSelecionado = tipoVeiculoSelecionado,
                    tipoUsoSelecionado = tipoUsoSelecionado,
                    onTipoVeiculoChange = { tipoVeiculoSelecionado = it },
                    onTipoUsoChange = { tipoUsoSelecionado = it }
                )
            }
            
            // Resumo principal com gráfico
            item {
                TransportSummaryCard(
                    periodo = periodoSelecionado,
                    tipoVeiculo = tipoVeiculoSelecionado,
                    tipoUso = tipoUsoSelecionado
                )
            }
            
            // Gráfico de emissões por veículo
            item {
                EmissoesVeiculosChart(
                    periodo = periodoSelecionado,
                    tipoVeiculo = tipoVeiculoSelecionado,
                    tipoUso = tipoUsoSelecionado
                )
            }
            
            // Insights importantes
            item {
                TransportInsightsCard(
                    periodo = periodoSelecionado,
                    tipoVeiculo = tipoVeiculoSelecionado,
                    tipoUso = tipoUsoSelecionado
                )
            }
            
            // Lista de veículos com dados detalhados
            item {
                Text(
                    text = "Veículos e Transportes",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(getTransportItems(periodoSelecionado, tipoVeiculoSelecionado, tipoUsoSelecionado)) { transport ->
                TransportCard(transport = transport)
            }
        }
    }
}

@Composable
fun PeriodoTransporteFiltroCard(
    periodoSelecionado: PeriodoTransporteFiltro,
    onPeriodoChange: (PeriodoTransporteFiltro) -> Unit
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
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            PeriodoTransporteFiltro.values().forEach { periodo ->
               FilterChip(
                    onClick = { onPeriodoChange(periodo) },
                    label = {
                        Text(
                            text = when (periodo) {
                                PeriodoTransporteFiltro.DIA -> "Dia"
                                PeriodoTransporteFiltro.SEMANA -> "Semana"
                                PeriodoTransporteFiltro.MES -> "Mês"
                                PeriodoTransporteFiltro.ANO -> "Ano"
                            },
                            color = if (periodoSelecionado == periodo) EcoTextOnGreen else EcoTextPrimary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .wrapContentHeight(Alignment.CenterVertically)
                                .padding(vertical = 5.dp, horizontal = 1.dp) // espaçamento interno maior
                        )
                    },
                    selected = periodoSelecionado == periodo,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EcoGreen,
                        containerColor = EcoDarkSurface
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp) // altura mínima maior para o botão
                )
            }
        }
    }
}

@Composable
fun TipoVeiculoUsoFiltroCard(
    tipoVeiculoSelecionado: TipoVeiculoFiltro,
    tipoUsoSelecionado: TipoUsoFiltro,
    onTipoVeiculoChange: (TipoVeiculoFiltro) -> Unit,
    onTipoUsoChange: (TipoUsoFiltro) -> Unit
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
            // Filtro de Tipo de Veículo
            Text(
                text = "Tipo de Veículo",
                color = EcoTextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Primeira linha de veículos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TipoVeiculoFiltro.values().take(3).forEach { veiculo ->
                        FilterChip(
                            onClick = { onTipoVeiculoChange(veiculo) },
                            label = {
                               Text(
                                    text = when (veiculo) {
                                        TipoVeiculoFiltro.CARRO -> "Carro"
                                        TipoVeiculoFiltro.MOTO -> "Moto"
                                        TipoVeiculoFiltro.ONIBUS -> "Ônibus"
                                        TipoVeiculoFiltro.METRO -> "Metrô"
                                        TipoVeiculoFiltro.BICICLETA -> "Bicicleta"
                                        TipoVeiculoFiltro.CAMINHAO -> "Caminhão"
                                    },
                                    color = if (tipoVeiculoSelecionado == veiculo) EcoTextOnGreen else EcoTextPrimary,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .wrapContentHeight(Alignment.CenterVertically)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            },
                            selected = tipoVeiculoSelecionado == veiculo,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EcoGreen,
                                containerColor = EcoDarkSurface
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Segunda linha de veículos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TipoVeiculoFiltro.values().drop(3).forEach { veiculo ->
                        FilterChip(
                            onClick = { onTipoVeiculoChange(veiculo) },
                            label = {
                                Text(
                                    text = when (veiculo) {
                                        TipoVeiculoFiltro.CARRO -> "Carro"
                                        TipoVeiculoFiltro.MOTO -> "Moto"
                                        TipoVeiculoFiltro.ONIBUS -> "Ônibus"
                                        TipoVeiculoFiltro.METRO -> "Metrô"
                                        TipoVeiculoFiltro.BICICLETA -> "Bicicleta"
                                        TipoVeiculoFiltro.CAMINHAO -> "Caminhão"
                                    },
                                    color = if (tipoVeiculoSelecionado == veiculo) EcoTextOnGreen else EcoTextPrimary,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .wrapContentHeight(Alignment.CenterVertically)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            },
                            selected = tipoVeiculoSelecionado == veiculo,
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
            
            // Filtro de Tipo de Uso
            Text(
                text = "Tipo de Uso",
                color = EcoTextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TipoUsoFiltro.values().forEach { uso ->
                    FilterChip(
                        onClick = { onTipoUsoChange(uso) },
                        label = {
                           Text(
                                text = when (uso) {
                                    TipoUsoFiltro.URBANO -> "Urbano"
                                    TipoUsoFiltro.RODOVIARIO -> "Rodoviário"
                                    TipoUsoFiltro.MISTO -> "Misto"
                                },
                                color = if (tipoUsoSelecionado == uso) EcoTextOnGreen else EcoTextPrimary,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .wrapContentHeight(Alignment.CenterVertically)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        },
                        selected = tipoUsoSelecionado == uso,
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
fun TransportSummaryCard(
    periodo: PeriodoTransporteFiltro,
    tipoVeiculo: TipoVeiculoFiltro,
    tipoUso: TipoUsoFiltro
) {
    // Dados baseados no tipo de veículo e uso
    val co2PorKm = when (tipoVeiculo) {
        TipoVeiculoFiltro.CARRO -> 0.192
        TipoVeiculoFiltro.MOTO -> 0.103
        TipoVeiculoFiltro.ONIBUS -> 0.089
        TipoVeiculoFiltro.METRO -> 0.041
        TipoVeiculoFiltro.BICICLETA -> 0.0
        TipoVeiculoFiltro.CAMINHAO -> 0.822
    }
    
    val multiplicadorUso = when (tipoUso) {
        TipoUsoFiltro.URBANO -> 1.0
        TipoUsoFiltro.RODOVIARIO -> 1.3
        TipoUsoFiltro.MISTO -> 1.15
    }
    
    val (distancia, co2, unidade) = when (periodo) {
        PeriodoTransporteFiltro.DIA -> Triple(
            "${String.format("%.1f", 45.2 * multiplicadorUso)}",
            "${String.format("%.1f", 45.2 * multiplicadorUso * co2PorKm)}",
            "km/dia"
        )
        PeriodoTransporteFiltro.SEMANA -> Triple(
            "${String.format("%.1f", 316.4 * multiplicadorUso)}",
            "${String.format("%.1f", 316.4 * multiplicadorUso * co2PorKm)}",
            "km/semana"
        )
        PeriodoTransporteFiltro.MES -> Triple(
            "${String.format("%.1f", 1356.0 * multiplicadorUso)}",
            "${String.format("%.1f", 1356.0 * multiplicadorUso * co2PorKm)}",
            "km/mês"
        )
        PeriodoTransporteFiltro.ANO -> Triple(
            "${String.format("%.1f", 16498.0 * multiplicadorUso)}",
            "${String.format("%.1f", 16498.0 * multiplicadorUso * co2PorKm)}",
            "km/ano"
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
                        text = "CO₂ do Transporte",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "$co2 kg $unidade",
                        color = EcoGreen,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Distância Total",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "$distancia km",
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
                        text = "Emissão por km",
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "${String.format("%.3f", co2PorKm)} kg/km",
                        color = EcoGreenAccent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Tipo: ${when (tipoVeiculo) {
                            TipoVeiculoFiltro.CARRO -> "Carro"
                            TipoVeiculoFiltro.MOTO -> "Moto"
                            TipoVeiculoFiltro.ONIBUS -> "Ônibus"
                            TipoVeiculoFiltro.METRO -> "Metrô"
                            TipoVeiculoFiltro.BICICLETA -> "Bicicleta"
                            TipoVeiculoFiltro.CAMINHAO -> "Caminhão"
                        }}",
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = when (tipoUso) {
                            TipoUsoFiltro.URBANO -> "Urbano"
                            TipoUsoFiltro.RODOVIARIO -> "Rodoviário"
                            TipoUsoFiltro.MISTO -> "Misto"
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
fun EmissoesVeiculosChart(
    periodo: PeriodoTransporteFiltro,
    tipoVeiculo: TipoVeiculoFiltro,
    tipoUso: TipoUsoFiltro
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
                    text = "Emissões por Veículo",
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
                TransportBar("Carro", 0.8f, EcoGreen, "8.7 kg")
                TransportBar("Moto", 0.4f, EcoGreenLight, "4.7 kg")
                TransportBar("Ônibus", 0.3f, EcoGreenAccent, "4.0 kg")
                TransportBar("Metrô", 0.2f, EcoWarning, "1.8 kg")
                TransportBar("Bicicleta", 0.0f, EcoError, "0.0 kg")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Legenda com dados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total: ${when (periodo) {
                        PeriodoTransporteFiltro.DIA -> "19.2 kg CO₂"
                        PeriodoTransporteFiltro.SEMANA -> "134.4 kg CO₂"
                        PeriodoTransporteFiltro.MES -> "576.0 kg CO₂"
                        PeriodoTransporteFiltro.ANO -> "7008.0 kg CO₂"
                    }}",
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = "Média Brasil: ${when (periodo) {
                        PeriodoTransporteFiltro.DIA -> "22.5 kg CO₂"
                        PeriodoTransporteFiltro.SEMANA -> "157.5 kg CO₂"
                        PeriodoTransporteFiltro.MES -> "675.0 kg CO₂"
                        PeriodoTransporteFiltro.ANO -> "8212.5 kg CO₂"
                    }}",
                    color = EcoGreenAccent,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun TransportInsightsCard(
    periodo: PeriodoTransporteFiltro,
    tipoVeiculo: TipoVeiculoFiltro,
    tipoUso: TipoUsoFiltro
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
                    text = "Insights de Mobilidade",
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val insights = when (periodo) {
                PeriodoTransporteFiltro.DIA -> listOf(
                    "Seu transporte está 15% abaixo da média nacional",
                    "Carro representa 45% das suas emissões diárias",
                    "Economia potencial: 2.3 kg CO₂/dia usando transporte público"
                )
                PeriodoTransporteFiltro.SEMANA -> listOf(
                    "Consumo semanal 15% menor que a média brasileira",
                    "Moto pode ser mais eficiente para trajetos curtos",
                    "Economia potencial: 16.1 kg CO₂/semana com carona compartilhada"
                )
                PeriodoTransporteFiltro.MES -> listOf(
                    "Consumo mensal 15% abaixo da média nacional",
                    "Bicicleta para trajetos < 5km pode economizar 99 kg CO₂/mês",
                    "Sua pegada de carbono é 0.7 toneladas CO₂/mês"
                )
                PeriodoTransporteFiltro.ANO -> listOf(
                    "Consumo anual 15% menor que a média brasileira",
                    "Substituir carro por transporte público pode economizar 1.2 toneladas CO₂/ano",
                    "Sua pegada de carbono é 8.4 toneladas CO₂/ano"
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
fun TransportBar(
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
fun TransportCard(
    transport: TransportItem
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
                    imageVector = transport.icon,
                    contentDescription = transport.type,
                    tint = transport.color,
                    modifier = Modifier.size(32.dp)
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = transport.type,
                        color = EcoTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = transport.details,
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = transport.co2Amount,
                        color = transport.color,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = transport.distance,
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
                        text = "Eficiência",
                        color = EcoTextSecondary,
                        fontSize = 10.sp
                    )
                    Text(
                        text = transport.efficiency,
                        color = EcoTextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Velocidade Média",
                        color = EcoTextSecondary,
                        fontSize = 10.sp
                    )
                    Text(
                        text = transport.avgSpeed,
                        color = EcoGreenAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Custo/km",
                        color = EcoTextSecondary,
                        fontSize = 10.sp
                    )
                    Text(
                        text = transport.costPerKm,
                        color = EcoGreenAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

data class TransportItem(
    val type: String,
    val details: String,
    val icon: ImageVector,
    val color: Color,
    val co2Amount: String,
    val distance: String,
    val efficiency: String,
    val avgSpeed: String,
    val costPerKm: String
)

fun getTransportItems(
    periodo: PeriodoTransporteFiltro,
    tipoVeiculo: TipoVeiculoFiltro,
    tipoUso: TipoUsoFiltro
): List<TransportItem> {
    val multiplicador = when (periodo) {
        PeriodoTransporteFiltro.DIA -> 1.0
        PeriodoTransporteFiltro.SEMANA -> 7.0
        PeriodoTransporteFiltro.MES -> 30.0
        PeriodoTransporteFiltro.ANO -> 365.0
    }
    
    val multiplicadorUso = when (tipoUso) {
        TipoUsoFiltro.URBANO -> 1.0
        TipoUsoFiltro.RODOVIARIO -> 1.3
        TipoUsoFiltro.MISTO -> 1.15
    }
    
    return listOf(
        TransportItem(
            type = "Carro (Gasolina)",
            details = "Casa → Trabalho - 1.6L",
            icon = Icons.Default.DirectionsCar,
            color = EcoGreen,
            co2Amount = "${String.format("%.1f", 25.0 * multiplicador * multiplicadorUso * 0.192)} kg",
            distance = "${String.format("%.1f", 25.0 * multiplicador * multiplicadorUso)} km",
            efficiency = "12 km/L",
            avgSpeed = "35 km/h",
            costPerKm = "R$ 0.45"
        ),
        TransportItem(
            type = "Moto (150cc)",
            details = "Trabalho → Shopping - 150cc",
            icon = Icons.Default.Motorcycle,
            color = EcoGreenLight,
            co2Amount = "${String.format("%.1f", 15.0 * multiplicador * multiplicadorUso * 0.103)} kg",
            distance = "${String.format("%.1f", 15.0 * multiplicador * multiplicadorUso)} km",
            efficiency = "35 km/L",
            avgSpeed = "45 km/h",
            costPerKm = "R$ 0.18"
        ),
        TransportItem(
            type = "Ônibus Urbano",
            details = "Shopping → Casa - Linha 123",
            icon = Icons.Default.DirectionsBus,
            color = EcoGreenAccent,
            co2Amount = "${String.format("%.1f", 12.0 * multiplicador * multiplicadorUso * 0.089)} kg",
            distance = "${String.format("%.1f", 12.0 * multiplicador * multiplicadorUso)} km",
            efficiency = "8 km/L",
            avgSpeed = "25 km/h",
            costPerKm = "R$ 0.12"
        ),
        TransportItem(
            type = "Metrô",
            details = "Centro → Zona Sul - Linha 1",
            icon = Icons.Default.Train,
            color = EcoWarning,
            co2Amount = "${String.format("%.1f", 8.0 * multiplicador * multiplicadorUso * 0.041)} kg",
            distance = "${String.format("%.1f", 8.0 * multiplicador * multiplicadorUso)} km",
            efficiency = "Eletricidade",
            avgSpeed = "40 km/h",
            costPerKm = "R$ 0.08"
        ),
        TransportItem(
            type = "Bicicleta",
            details = "Casa → Parque - Elétrica",
            icon = Icons.Default.PedalBike,
            color = EcoError,
            co2Amount = "${String.format("%.1f", 5.2 * multiplicador * multiplicadorUso * 0.0)} kg",
            distance = "${String.format("%.1f", 5.2 * multiplicador * multiplicadorUso)} km",
            efficiency = "Zero emissões",
            avgSpeed = "15 km/h",
            costPerKm = "R$ 0.02"
        ),
        TransportItem(
            type = "Caminhão (Carga)",
            details = "Centro de Distribuição - 3.5t",
            icon = Icons.Default.LocalShipping,
            color = EcoGreen,
            co2Amount = "${String.format("%.1f", 50.0 * multiplicador * multiplicadorUso * 0.822)} kg",
            distance = "${String.format("%.1f", 50.0 * multiplicador * multiplicadorUso)} km",
            efficiency = "6 km/L",
            avgSpeed = "60 km/h",
            costPerKm = "R$ 1.20"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TransportScreenPreview() {
    EcoTrackTheme {
        TransportScreen()
    }
}
