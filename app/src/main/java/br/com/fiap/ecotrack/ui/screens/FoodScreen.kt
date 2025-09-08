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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*

enum class PeriodoAlimentacaoFiltro {
    DIA, SEMANA, MES, ANO
}

enum class TipoAlimentoFiltro {
    CARNES, LATICINIOS, GRAOS, FRUTAS, VEGETAIS, DOCES
}

enum class TipoRefeicaoFiltro {
    CAFE_MANHA, ALMOCO, LANCHE, JANTAR, CEIA
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScreen(
    onBackClick: () -> Unit = {},
    onAddFood: () -> Unit = {},
    onFoodEmissionCalculator: () -> Unit = {}
) {
    var periodoSelecionado by remember { mutableStateOf(PeriodoAlimentacaoFiltro.DIA) }
    var tipoAlimentoSelecionado by remember { mutableStateOf(TipoAlimentoFiltro.CARNES) }
    var tipoRefeicaoSelecionado by remember { mutableStateOf(TipoRefeicaoFiltro.ALMOCO) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Alimentação",
                    color = EcoTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        contentDescription = "Voltar",
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = EcoTextPrimary
                    )
                }
            },
            actions = {
                IconButton(onClick = onFoodEmissionCalculator) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = "Calculadora de Emissões",
                        tint = EcoGreen
                    )
                }
                IconButton(onClick = onAddFood) {
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
                PeriodoAlimentacaoFiltroCard(
                    periodoSelecionado = periodoSelecionado,
                    onPeriodoChange = { periodoSelecionado = it }
                )
            }
            
            // Filtros de tipo de alimento e refeição
            item {
                TipoAlimentoRefeicaoFiltroCard(
                    tipoAlimentoSelecionado = tipoAlimentoSelecionado,
                    tipoRefeicaoSelecionado = tipoRefeicaoSelecionado,
                    onTipoAlimentoChange = { tipoAlimentoSelecionado = it },
                    onTipoRefeicaoChange = { tipoRefeicaoSelecionado = it }
                )
            }
            
            // Resumo principal com gráfico
            item {
                FoodSummaryCard(
                    periodo = periodoSelecionado,
                    tipoAlimento = tipoAlimentoSelecionado,
                    tipoRefeicao = tipoRefeicaoSelecionado
                )
            }
            
            // Gráfico de emissões por alimento
            item {
                EmissoesAlimentosChart(
                    periodo = periodoSelecionado,
                    tipoAlimento = tipoAlimentoSelecionado,
                    tipoRefeicao = tipoRefeicaoSelecionado
                )
            }
            
            // Insights importantes
            item {
                FoodInsightsCard(
                    periodo = periodoSelecionado,
                    tipoAlimento = tipoAlimentoSelecionado,
                    tipoRefeicao = tipoRefeicaoSelecionado
                )
            }
            
            // Lista de alimentos com dados detalhados
            item {
                Text(
                    text = "Alimentos e Refeições",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(getFoodItems(periodoSelecionado, tipoAlimentoSelecionado, tipoRefeicaoSelecionado)) { food ->
                FoodCard(food = food)
            }
        }
    }
}

@Composable
fun PeriodoAlimentacaoFiltroCard(
    periodoSelecionado: PeriodoAlimentacaoFiltro,
    onPeriodoChange: (PeriodoAlimentacaoFiltro) -> Unit
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
            PeriodoAlimentacaoFiltro.values().forEach { periodo ->
                FilterChip(
                    onClick = { onPeriodoChange(periodo) },
                    label = {
                        Text(
                            text = when (periodo) {
                                PeriodoAlimentacaoFiltro.DIA -> "Dia"
                                PeriodoAlimentacaoFiltro.SEMANA -> "Semana"
                                PeriodoAlimentacaoFiltro.MES -> "Mês"
                                PeriodoAlimentacaoFiltro.ANO -> "Ano"
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
fun TipoAlimentoRefeicaoFiltroCard(
    tipoAlimentoSelecionado: TipoAlimentoFiltro,
    tipoRefeicaoSelecionado: TipoRefeicaoFiltro,
    onTipoAlimentoChange: (TipoAlimentoFiltro) -> Unit,
    onTipoRefeicaoChange: (TipoRefeicaoFiltro) -> Unit
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
            // Filtro de Tipo de Alimento
            Text(
                text = "Tipo de Alimento",
                color = EcoTextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Primeira linha de alimentos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TipoAlimentoFiltro.values().take(3).forEach { alimento ->
                        FilterChip(
                            onClick = { onTipoAlimentoChange(alimento) },
                            label = {
                                Text(
                                    text = when (alimento) {
                                        TipoAlimentoFiltro.CARNES -> "Carnes"
                                        TipoAlimentoFiltro.LATICINIOS -> "Laticínios"
                                        TipoAlimentoFiltro.GRAOS -> "Grãos"
                                        TipoAlimentoFiltro.FRUTAS -> "Frutas"
                                        TipoAlimentoFiltro.VEGETAIS -> "Vegetais"
                                        TipoAlimentoFiltro.DOCES -> "Doces"
                                    },
                                    color = if (tipoAlimentoSelecionado == alimento) EcoTextOnGreen else EcoTextPrimary,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(Alignment.CenterVertically)
                                )
                            },
                            selected = tipoAlimentoSelecionado == alimento,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EcoGreen,
                                containerColor = EcoDarkSurface
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Segunda linha de alimentos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TipoAlimentoFiltro.values().drop(3).forEach { alimento ->
                        FilterChip(
                            onClick = { onTipoAlimentoChange(alimento) },
                            label = {
                                Text(
                                    text = when (alimento) {
                                        TipoAlimentoFiltro.CARNES -> "Carnes"
                                        TipoAlimentoFiltro.LATICINIOS -> "Laticínios"
                                        TipoAlimentoFiltro.GRAOS -> "Grãos"
                                        TipoAlimentoFiltro.FRUTAS -> "Frutas"
                                        TipoAlimentoFiltro.VEGETAIS -> "Vegetais"
                                        TipoAlimentoFiltro.DOCES -> "Doces"
                                    },
                                    color = if (tipoAlimentoSelecionado == alimento) EcoTextOnGreen else EcoTextPrimary,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(Alignment.CenterVertically)
                                )
                            },
                            selected = tipoAlimentoSelecionado == alimento,
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
            
            // Filtro de Tipo de Refeição
            Text(
                text = "Tipo de Refeição",
                color = EcoTextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
           Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Primeira linha: 3 botões
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TipoRefeicaoFiltro.values().take(3).forEach { refeicao ->
                        FilterChip(
                            onClick = { onTipoRefeicaoChange(refeicao) },
                            label = {
                                Text(
                                    text = when (refeicao) {
                                        TipoRefeicaoFiltro.CAFE_MANHA -> "Café"
                                        TipoRefeicaoFiltro.ALMOCO -> "Almoço"
                                        TipoRefeicaoFiltro.LANCHE -> "Lanche"
                                        else -> ""
                                    },
                                    color = if (tipoRefeicaoSelecionado == refeicao) EcoTextOnGreen else EcoTextPrimary,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                )
                            },
                            selected = tipoRefeicaoSelecionado == refeicao,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EcoGreen,
                                containerColor = EcoDarkSurface
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(35.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                // Segunda linha: 2 botões
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TipoRefeicaoFiltro.values().drop(3).forEach { refeicao ->
                        FilterChip(
                            onClick = { onTipoRefeicaoChange(refeicao) },
                            label = {
                                Text(
                                    text = when (refeicao) {
                                        TipoRefeicaoFiltro.JANTAR -> "Jantar"
                                        TipoRefeicaoFiltro.CEIA -> "Ceia"
                                        else -> ""
                                    },
                                    color = if (tipoRefeicaoSelecionado == refeicao) EcoTextOnGreen else EcoTextPrimary,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 1.dp)
                                )
                            },
                            selected = tipoRefeicaoSelecionado == refeicao,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EcoGreen,
                                containerColor = EcoDarkSurface
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(35.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FoodSummaryCard(
    periodo: PeriodoAlimentacaoFiltro,
    tipoAlimento: TipoAlimentoFiltro,
    tipoRefeicao: TipoRefeicaoFiltro
) {
    // Dados baseados no tipo de alimento e refeição
    val co2PorKg = when (tipoAlimento) {
        TipoAlimentoFiltro.CARNES -> 27.0
        TipoAlimentoFiltro.LATICINIOS -> 3.2
        TipoAlimentoFiltro.GRAOS -> 2.0
        TipoAlimentoFiltro.FRUTAS -> 0.3
        TipoAlimentoFiltro.VEGETAIS -> 0.2
        TipoAlimentoFiltro.DOCES -> 1.5
    }
    
    val caloriasPorKg = when (tipoAlimento) {
        TipoAlimentoFiltro.CARNES -> 2500.0
        TipoAlimentoFiltro.LATICINIOS -> 4000.0
        TipoAlimentoFiltro.GRAOS -> 3500.0
        TipoAlimentoFiltro.FRUTAS -> 600.0
        TipoAlimentoFiltro.VEGETAIS -> 200.0
        TipoAlimentoFiltro.DOCES -> 4500.0
    }
    
    val multiplicadorRefeicao = when (tipoRefeicao) {
        TipoRefeicaoFiltro.CAFE_MANHA -> 0.3
        TipoRefeicaoFiltro.ALMOCO -> 0.4
        TipoRefeicaoFiltro.LANCHE -> 0.1
        TipoRefeicaoFiltro.JANTAR -> 0.3
        TipoRefeicaoFiltro.CEIA -> 0.1
    }
    
    val (peso, co2, calorias, unidade) = when (periodo) {
        PeriodoAlimentacaoFiltro.DIA -> listOf(
            "${String.format("%.1f", 1.5 * multiplicadorRefeicao)}",
            "${String.format("%.1f", 1.5 * multiplicadorRefeicao * co2PorKg)}",
            "${String.format("%.0f", 1.5 * multiplicadorRefeicao * caloriasPorKg)}",
            "kg/dia"
        )
        PeriodoAlimentacaoFiltro.SEMANA -> listOf(
            "${String.format("%.1f", 10.5 * multiplicadorRefeicao)}",
            "${String.format("%.1f", 10.5 * multiplicadorRefeicao * co2PorKg)}",
            "${String.format("%.0f", 10.5 * multiplicadorRefeicao * caloriasPorKg)}",
            "kg/semana"
        )
        PeriodoAlimentacaoFiltro.MES -> listOf(
            "${String.format("%.1f", 45.0 * multiplicadorRefeicao)}",
            "${String.format("%.1f", 45.0 * multiplicadorRefeicao * co2PorKg)}",
            "${String.format("%.0f", 45.0 * multiplicadorRefeicao * caloriasPorKg)}",
            "kg/mês"
        )
        PeriodoAlimentacaoFiltro.ANO -> listOf(
            "${String.format("%.1f", 547.5 * multiplicadorRefeicao)}",
            "${String.format("%.1f", 547.5 * multiplicadorRefeicao * co2PorKg)}",
            "${String.format("%.0f", 547.5 * multiplicadorRefeicao * caloriasPorKg)}",
            "kg/ano"
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
                        text = "CO₂ da Alimentação",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "$co2 kg $unidade",
                        color = EcoGreenAccent,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Calorias",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "$calorias kcal",
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
                        text = "Peso consumido",
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "$peso kg",
                        color = EcoGreenAccent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Emissão por kg",
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "${String.format("%.1f", co2PorKg)} kg CO₂/kg",
                        color = EcoGreenAccent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Tipo: ${when (tipoAlimento) {
                            TipoAlimentoFiltro.CARNES -> "Carnes"
                            TipoAlimentoFiltro.LATICINIOS -> "Laticínios"
                            TipoAlimentoFiltro.GRAOS -> "Grãos"
                            TipoAlimentoFiltro.FRUTAS -> "Frutas"
                            TipoAlimentoFiltro.VEGETAIS -> "Vegetais"
                            TipoAlimentoFiltro.DOCES -> "Doces"
                        }}",
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = when (tipoRefeicao) {
                            TipoRefeicaoFiltro.CAFE_MANHA -> "Café da Manhã"
                            TipoRefeicaoFiltro.ALMOCO -> "Almoço"
                            TipoRefeicaoFiltro.LANCHE -> "Lanche"
                            TipoRefeicaoFiltro.JANTAR -> "Jantar"
                            TipoRefeicaoFiltro.CEIA -> "Ceia"
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
fun EmissoesAlimentosChart(
    periodo: PeriodoAlimentacaoFiltro,
    tipoAlimento: TipoAlimentoFiltro,
    tipoRefeicao: TipoRefeicaoFiltro
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
                    text = "Emissões por Alimento",
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
                FoodBar("Carnes", 0.9f, EcoGreen, "24.3 kg")
                FoodBar("Laticínios", 0.3f, EcoGreenLight, "3.2 kg")
                FoodBar("Grãos", 0.2f, EcoGreenAccent, "2.0 kg")
                FoodBar("Frutas", 0.1f, EcoWarning, "0.3 kg")
                FoodBar("Vegetais", 0.05f, EcoError, "0.2 kg")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Legenda com dados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total: ${when (periodo) {
                        PeriodoAlimentacaoFiltro.DIA -> "30.0 kg CO₂"
                        PeriodoAlimentacaoFiltro.SEMANA -> "210.0 kg CO₂"
                        PeriodoAlimentacaoFiltro.MES -> "900.0 kg CO₂"
                        PeriodoAlimentacaoFiltro.ANO -> "10950.0 kg CO₂"
                    }}",
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = "Média Brasil: ${when (periodo) {
                        PeriodoAlimentacaoFiltro.DIA -> "35.0 kg CO₂"
                        PeriodoAlimentacaoFiltro.SEMANA -> "245.0 kg CO₂"
                        PeriodoAlimentacaoFiltro.MES -> "1050.0 kg CO₂"
                        PeriodoAlimentacaoFiltro.ANO -> "12775.0 kg CO₂"
                    }}",
                    color = EcoGreenAccent,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun FoodInsightsCard(
    periodo: PeriodoAlimentacaoFiltro,
    tipoAlimento: TipoAlimentoFiltro,
    tipoRefeicao: TipoRefeicaoFiltro
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
                    text = "Insights de Alimentação",
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val insights = when (periodo) {
                PeriodoAlimentacaoFiltro.DIA -> listOf(
                    "Sua alimentação está 14% abaixo da média nacional",
                    "Carnes representam 81% das suas emissões alimentares",
                    "Economia potencial: 5.0 kg CO₂/dia reduzindo carnes"
                )
                PeriodoAlimentacaoFiltro.SEMANA -> listOf(
                    "Consumo semanal 14% menor que a média brasileira",
                    "Substituir carne por vegetais pode economizar 35 kg CO₂/semana",
                    "Dieta vegetariana reduziria emissões em 70%"
                )
                PeriodoAlimentacaoFiltro.MES -> listOf(
                    "Consumo mensal 14% abaixo da média nacional",
                    "Alimentação local pode economizar 150 kg CO₂/mês",
                    "Sua pegada alimentar é 0.9 toneladas CO₂/mês"
                )
                PeriodoAlimentacaoFiltro.ANO -> listOf(
                    "Consumo anual 14% menor que a média brasileira",
                    "Dieta flexitariana pode economizar 1.8 toneladas CO₂/ano",
                    "Sua pegada alimentar é 11.0 toneladas CO₂/ano"
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
fun FoodBar(
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
fun FoodCard(
    food: FoodItem
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
                imageVector = food.icon,
                contentDescription = food.meal,
                tint = food.color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = food.meal,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = food.details,
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = food.co2Amount,
                    color = food.color,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = food.calories,
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
                        text = "Peso",
                        color = EcoTextSecondary,
                        fontSize = 10.sp
                    )
                    Text(
                        text = food.weight,
                        color = EcoTextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Proteína",
                        color = EcoTextSecondary,
                        fontSize = 10.sp
                    )
                    Text(
                        text = food.protein,
                        color = EcoGreenAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Carboidratos",
                        color = EcoTextSecondary,
                        fontSize = 10.sp
                    )
                    Text(
                        text = food.carbs,
                        color = EcoGreenAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

data class FoodItem(
    val meal: String,
    val details: String,
    val icon: ImageVector,
    val color: Color,
    val co2Amount: String,
    val calories: String,
    val weight: String,
    val protein: String,
    val carbs: String
)

fun getFoodItems(
    periodo: PeriodoAlimentacaoFiltro,
    tipoAlimento: TipoAlimentoFiltro,
    tipoRefeicao: TipoRefeicaoFiltro
): List<FoodItem> {
    val multiplicador = when (periodo) {
        PeriodoAlimentacaoFiltro.DIA -> 1.0
        PeriodoAlimentacaoFiltro.SEMANA -> 7.0
        PeriodoAlimentacaoFiltro.MES -> 30.0
        PeriodoAlimentacaoFiltro.ANO -> 365.0
    }
    
    val multiplicadorRefeicao = when (tipoRefeicao) {
        TipoRefeicaoFiltro.CAFE_MANHA -> 0.3
        TipoRefeicaoFiltro.ALMOCO -> 0.4
        TipoRefeicaoFiltro.LANCHE -> 0.1
        TipoRefeicaoFiltro.JANTAR -> 0.3
        TipoRefeicaoFiltro.CEIA -> 0.1
    }
    
    return listOf(
        FoodItem(
            meal = "Café da Manhã",
            details = "Pão integral, leite, banana",
            icon = Icons.Default.FreeBreakfast,
            color = EcoGreenAccent,
            co2Amount = "${String.format("%.1f", 0.8 * multiplicador * multiplicadorRefeicao)} kg",
            calories = "${String.format("%.0f", 450 * multiplicador * multiplicadorRefeicao)} kcal",
            weight = "${String.format("%.1f", 0.3 * multiplicador * multiplicadorRefeicao)} kg",
            protein = "${String.format("%.1f", 15 * multiplicador * multiplicadorRefeicao)} g",
            carbs = "${String.format("%.1f", 65 * multiplicador * multiplicadorRefeicao)} g"
        ),
        FoodItem(
            meal = "Almoço",
            details = "Arroz, feijão, carne bovina",
            icon = Icons.Default.Restaurant,
            color = EcoGreen,
            co2Amount = "${String.format("%.1f", 1.2 * multiplicador * multiplicadorRefeicao)} kg",
            calories = "${String.format("%.0f", 850 * multiplicador * multiplicadorRefeicao)} kcal",
            weight = "${String.format("%.1f", 0.4 * multiplicador * multiplicadorRefeicao)} kg",
            protein = "${String.format("%.1f", 45 * multiplicador * multiplicadorRefeicao)} g",
            carbs = "${String.format("%.1f", 80 * multiplicador * multiplicadorRefeicao)} g"
        ),
        FoodItem(
            meal = "Jantar",
            details = "Salada verde, peixe grelhado",
            icon = Icons.Default.DinnerDining,
            color = EcoGreenLight,
            co2Amount = "${String.format("%.1f", 0.2 * multiplicador * multiplicadorRefeicao)} kg",
            calories = "${String.format("%.0f", 650 * multiplicador * multiplicadorRefeicao)} kcal",
            weight = "${String.format("%.1f", 0.3 * multiplicador * multiplicadorRefeicao)} kg",
            protein = "${String.format("%.1f", 35 * multiplicador * multiplicadorRefeicao)} g",
            carbs = "${String.format("%.1f", 25 * multiplicador * multiplicadorRefeicao)} g"
        ),
        FoodItem(
            meal = "Lanche",
            details = "Iogurte, granola, frutas",
            icon = Icons.Default.Cake,
            color = EcoWarning,
            co2Amount = "${String.format("%.1f", 0.1 * multiplicador * multiplicadorRefeicao)} kg",
            calories = "${String.format("%.0f", 200 * multiplicador * multiplicadorRefeicao)} kcal",
            weight = "${String.format("%.1f", 0.2 * multiplicador * multiplicadorRefeicao)} kg",
            protein = "${String.format("%.1f", 8 * multiplicador * multiplicadorRefeicao)} g",
            carbs = "${String.format("%.1f", 30 * multiplicador * multiplicadorRefeicao)} g"
        ),
        FoodItem(
            meal = "Ceia",
            details = "Chá, biscoito integral",
            icon = Icons.Default.LocalCafe,
            color = EcoError,
            co2Amount = "${String.format("%.1f", 0.05 * multiplicador * multiplicadorRefeicao)} kg",
            calories = "${String.format("%.0f", 100 * multiplicador * multiplicadorRefeicao)} kcal",
            weight = "${String.format("%.1f", 0.1 * multiplicador * multiplicadorRefeicao)} kg",
            protein = "${String.format("%.1f", 3 * multiplicador * multiplicadorRefeicao)} g",
            carbs = "${String.format("%.1f", 15 * multiplicador * multiplicadorRefeicao)} g"
        ),
        FoodItem(
            meal = "Sobremesa",
            details = "Pudim de chocolate",
            icon = Icons.Default.Cake,
            color = EcoGreenAccent,
            co2Amount = "${String.format("%.1f", 0.3 * multiplicador * multiplicadorRefeicao)} kg",
            calories = "${String.format("%.0f", 300 * multiplicador * multiplicadorRefeicao)} kcal",
            weight = "${String.format("%.1f", 0.15 * multiplicador * multiplicadorRefeicao)} kg",
            protein = "${String.format("%.1f", 5 * multiplicador * multiplicadorRefeicao)} g",
            carbs = "${String.format("%.1f", 45 * multiplicador * multiplicadorRefeicao)} g"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun FoodScreenPreview() {
    EcoTrackTheme {
        FoodScreen()
    }
}
