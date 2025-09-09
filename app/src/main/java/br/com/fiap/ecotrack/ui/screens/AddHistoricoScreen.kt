package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*
import br.com.fiap.ecotrack.ui.theme.LocalDynamicColors
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHistoricoScreen(
    onBackClick: () -> Unit = {}
) {
    val colors = LocalDynamicColors.current
    var selectedCategory by remember { mutableStateOf("Todas") }
    var isLoading by remember { mutableStateOf(true) }
    
    // Simular carregamento de dados da API
    LaunchedEffect(Unit) {
        delay(1000) // Simular delay da API
        isLoading = false
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Histórico de Conquistas",
                    color = colors.textPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = colors.textPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colors.background
            )
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colors.green,
                    modifier = Modifier.size(48.dp)
                )
            }
        } else {
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 100.dp)
                    .padding(bottom = 45.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Resumo Avançado do Histórico
                item {
                    AdvancedHistorySummaryCard()
                }
                
                // Filtros por Categoria
                item {
                    CategoryFilterSection(
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selectedCategory = it }
                    )
                }
                
                // Lista de Histórico Filtrada
                items(getFilteredHistoryItems(selectedCategory)) { historyItem ->
                    HistoryItemCard(historyItem)
                }
            }
        }
    }
}

@Composable
fun AdvancedHistorySummaryCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.green.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Analytics,
                    contentDescription = "Resumo",
                    tint = colors.green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Resumo de Impacto Ambiental",
                    color = colors.green,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Primeira linha de estatísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryStat(
                    value = "247",
                    label = "Conquistas",
                    color = colors.green,
                    icon = Icons.Default.EmojiEvents
                )
                SummaryStat(
                    value = "3.2",
                    label = "Ton CO₂ Reduzido",
                    color = colors.greenLight,
                    icon = Icons.Default.TrendingDown
                )
                SummaryStat(
                    value = "89",
                    label = "Dias Ativo",
                    color = colors.greenAccent,
                    icon = Icons.Default.CalendarToday
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Segunda linha de estatísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryStat(
                    value = "12",
                    label = "Árvores Salvas",
                    color = colors.green,
                    icon = Icons.Default.Park
                )
                SummaryStat(
                    value = "1.8",
                    label = "Ton CO₂ Produzido",
                    color = colors.warning,
                    icon = Icons.Default.TrendingUp
                )
                SummaryStat(
                    value = "3",
                    label = "Vidas Salvas",
                    color = colors.success,
                    icon = Icons.Default.Favorite
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Barra de progresso do impacto
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Impacto Positivo",
                        color = colors.textPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "64%",
                        color = colors.green,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = 0.64f,
                    modifier = Modifier.fillMaxWidth(),
                    color = colors.green,
                    trackColor = colors.surfaceVariant
                )
            }
        }
    }
}

@Composable
fun SummaryStat(
    value: String,
    label: String,
    color: Color,
    icon: ImageVector
) {
    val colors = LocalDynamicColors.current
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = color,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            color = colors.textSecondary,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CategoryFilterSection(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val colors = LocalDynamicColors.current
    val categories = listOf(
        "Todas" to colors.green,
        "Transporte" to colors.greenLight,
        "Energia" to colors.greenAccent,
        "Alimentação" to colors.green,
        "Resíduos" to colors.greenLight,
        "Água" to colors.greenAccent,
        "Metas" to colors.green,
        "Especiais" to colors.greenLight
    )
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Filtrar por Categoria",
                color = colors.textPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Primeira linha de filtros
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                categories.take(4).forEach { (category, color) ->
                    CategoryChip(
                        text = category,
                        color = color,
                        isSelected = selectedCategory == category,
                        onClick = { onCategorySelected(category) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Segunda linha de filtros
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                categories.drop(4).forEach { (category, color) ->
                    CategoryChip(
                        text = category,
                        color = color,
                        isSelected = selectedCategory == category,
                        onClick = { onCategorySelected(category) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryChip(
    text: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier
            .padding(2.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) color else colors.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else colors.textSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun HistoryItemCard(historyItem: HistoryItem) {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone da Categoria
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = historyItem.categoryColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = historyItem.icon,
                    contentDescription = historyItem.category,
                    tint = historyItem.categoryColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Informações do Histórico
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = historyItem.title,
                    color = colors.textPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = historyItem.description,
                    color = colors.textSecondary,
                    fontSize = 13.sp,
                    lineHeight = 16.sp
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Data",
                        tint = colors.textSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = historyItem.date,
                        color = colors.textSecondary,
                        fontSize = 11.sp
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Horário",
                        tint = colors.textSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = historyItem.time,
                        color = colors.textSecondary,
                        fontSize = 11.sp
                    )
                }
            }
            
            // CO₂ Reduzido
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = historyItem.co2Reduced,
                    color = historyItem.categoryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "CO₂",
                    color = colors.textSecondary,
                    fontSize = 10.sp
                )
                
                if (historyItem.isMilestone) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Marco",
                        tint = colors.green,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

// Data Classes
data class HistoryItem(
    val title: String,
    val description: String,
    val category: String,
    val categoryColor: Color,
    val icon: ImageVector,
    val date: String,
    val time: String,
    val co2Reduced: String,
    val isMilestone: Boolean = false
)

// Função de filtro
fun getFilteredHistoryItems(selectedCategory: String): List<HistoryItem> {
    val allItems = getHistoryItems()
    return if (selectedCategory == "Todas") {
        allItems
    } else {
        allItems.filter { it.category == selectedCategory }
    }
}

// Funções de dados
fun getHistoryItems(): List<HistoryItem> {
    return listOf(
        // TRANSPORTE
        HistoryItem(
            title = "Meta Semanal de Transporte Atingida",
            description = "Evitou usar carro por 5 dias consecutivos, optando por bicicleta e transporte público",
            category = "Transporte",
            categoryColor = EcoGreen,
            icon = Icons.Default.DirectionsCar,
            date = "15/12/2024",
            time = "18:30",
            co2Reduced = "25 kg",
            isMilestone = true
        ),
        HistoryItem(
            title = "Viagem de Bicicleta",
            description = "Substituiu carro por bicicleta para ir ao trabalho (8 km)",
            category = "Transporte",
            categoryColor = EcoGreen,
            date = "14/12/2024",
            time = "07:15",
            co2Reduced = "2.4 kg",
            icon = Icons.AutoMirrored.Filled.DirectionsBike
        ),
        HistoryItem(
            title = "Uso de Transporte Público",
            description = "Utilizou metrô em vez de carro para compromissos do dia",
            category = "Transporte",
            categoryColor = EcoGreen,
            icon = Icons.Default.Train,
            date = "13/12/2024",
            time = "09:45",
            co2Reduced = "3.8 kg"
        ),
        
        // ENERGIA
        HistoryItem(
            title = "Meta Mensal de Energia Atingida",
            description = "Reduziu consumo de energia em 30% comparado ao mês anterior",
            category = "Energia",
            categoryColor = EcoGreenLight,
            icon = Icons.Default.EnergySavingsLeaf,
            date = "12/12/2024",
            time = "22:00",
            co2Reduced = "45 kg",
            isMilestone = true
        ),
        HistoryItem(
            title = "Troca para Lâmpadas LED",
            description = "Substituiu 10 lâmpadas incandescentes por LED",
            category = "Energia",
            categoryColor = EcoGreenLight,
            icon = Icons.Default.Lightbulb,
            date = "11/12/2024",
            time = "16:20",
            co2Reduced = "8.5 kg"
        ),
        HistoryItem(
            title = "Desligamento Consciente",
            description = "Desligou todos os eletrônicos ao sair de casa",
            category = "Energia",
            categoryColor = EcoGreenLight,
            icon = Icons.Default.PowerSettingsNew,
            date = "10/12/2024",
            time = "08:00",
            co2Reduced = "1.2 kg"
        ),
        
        // ALIMENTAÇÃO
        HistoryItem(
            title = "Meta Semanal de Alimentação Atingida",
            description = "Reduziu consumo de carne vermelha em 80% da semana",
            category = "Alimentação",
            categoryColor = EcoGreenAccent,
            icon = Icons.Default.Restaurant,
            date = "09/12/2024",
            time = "19:30",
            co2Reduced = "18 kg",
            isMilestone = true
        ),
        HistoryItem(
            title = "Compra de Produtos Locais",
            description = "Adquiriu frutas e verduras de produtores locais",
            category = "Alimentação",
            categoryColor = EcoGreenAccent,
            icon = Icons.Default.LocalGroceryStore,
            date = "08/12/2024",
            time = "14:15",
            co2Reduced = "2.1 kg"
        ),
        HistoryItem(
            title = "Redução de Desperdício",
            description = "Planejou refeições para evitar sobras e desperdício",
            category = "Alimentação",
            categoryColor = EcoGreenAccent,
            icon = Icons.Default.NoFood,
            date = "07/12/2024",
            time = "12:00",
            co2Reduced = "1.8 kg"
        ),
        
        // RESÍDUOS
        HistoryItem(
            title = "Meta Mensal de Reciclagem Atingida",
            description = "Reciclou 95% dos resíduos domésticos do mês",
            category = "Resíduos",
            categoryColor = EcoGreen,
            icon = Icons.Default.Recycling,
            date = "06/12/2024",
            time = "20:45",
            co2Reduced = "32 kg",
            isMilestone = true
        ),
        HistoryItem(
            title = "Compostagem de Orgânicos",
            description = "Iniciou processo de compostagem para resíduos orgânicos",
            category = "Resíduos",
            categoryColor = EcoGreen,
            icon = Icons.Default.Grass,
            date = "05/12/2024",
            time = "10:30",
            co2Reduced = "5.5 kg"
        ),
        HistoryItem(
            title = "Redução de Plásticos",
            description = "Substituiu produtos com embalagens plásticas por alternativas sustentáveis",
            category = "Resíduos",
            categoryColor = EcoGreen,
            icon = Icons.Default.DeleteOutline,
            date = "04/12/2024",
            time = "15:20",
            co2Reduced = "3.2 kg"
        ),
        
        // ÁGUA
        HistoryItem(
            title = "Meta Semanal de Água Atingida",
            description = "Reduziu consumo de água em 25% comparado à semana anterior",
            category = "Água",
            categoryColor = EcoGreenLight,
            icon = Icons.Default.WaterDrop,
            date = "03/12/2024",
            time = "21:15",
            co2Reduced = "12 kg",
            isMilestone = true
        ),
        HistoryItem(
            title = "Instalação de Economizadores",
            description = "Instalou dispositivos economizadores de água nas torneiras",
            category = "Água",
            categoryColor = EcoGreenLight,
            icon = Icons.Default.Plumbing,
            date = "02/12/2024",
            time = "11:00",
            co2Reduced = "8.7 kg"
        ),
        HistoryItem(
            title = "Reutilização de Água",
            description = "Reutilizou água da chuva para regar plantas",
            category = "Água",
            categoryColor = EcoGreenLight,
            icon = Icons.Default.Water,
            date = "01/12/2024",
            time = "16:45",
            co2Reduced = "2.3 kg"
        ),
        
        // METAS ESPECIAIS
        HistoryItem(
            title = "Desafio 30 Dias Sustentáveis",
            description = "Completou 30 dias consecutivos de práticas sustentáveis",
            category = "Metas",
            categoryColor = EcoGreenAccent,
            icon = Icons.Default.EmojiEvents,
            date = "30/11/2024",
            time = "23:59",
            co2Reduced = "150 kg",
            isMilestone = true
        ),
        HistoryItem(
            title = "Primeira Conquista Anual",
            description = "Atingiu primeira meta anual de redução de CO₂",
            category = "Metas",
            categoryColor = EcoGreenAccent,
            icon = Icons.Default.Star,
            date = "25/11/2024",
            time = "18:00",
            co2Reduced = "500 kg",
            isMilestone = true
        ),
        HistoryItem(
            title = "Comunidade Sustentável",
            description = "Inspirou 5 amigos a adotarem práticas sustentáveis",
            category = "Metas",
            categoryColor = EcoGreenAccent,
            icon = Icons.Default.Group,
            date = "20/11/2024",
            time = "14:30",
            co2Reduced = "75 kg"
        ),
        
        // CONQUISTAS ESPECIAIS
        HistoryItem(
            title = "Primeiro Milhão de CO₂",
            description = "Reduziu 1 milhão de gramas de CO₂ em um ano",
            category = "Especiais",
            categoryColor = EcoGreenLight,
            icon = Icons.Default.Diamond,
            date = "15/11/2024",
            time = "12:00",
            co2Reduced = "1000 kg",
            isMilestone = true
        ),
        HistoryItem(
            title = "Herói do Clima",
            description = "Conquistou o título de Herói do Clima por 6 meses consecutivos",
            category = "Especiais",
            categoryColor = EcoGreenLight,
            icon = Icons.Default.MilitaryTech,
            date = "10/11/2024",
            time = "09:00",
            co2Reduced = "800 kg",
            isMilestone = true
        ),
        HistoryItem(
            title = "Influenciador Verde",
            description = "Compartilhou 100 dicas sustentáveis nas redes sociais",
            category = "Especiais",
            categoryColor = EcoGreenLight,
            icon = Icons.Default.Share,
            date = "05/11/2024",
            time = "16:30",
            co2Reduced = "200 kg"
        ),
        
        // METAS ADICIONAIS DE TRANSPORTE
        HistoryItem(
            title = "Carona Solidária",
            description = "Organizou sistema de carona solidária no trabalho",
            category = "Transporte",
            categoryColor = EcoGreen,
            icon = Icons.Default.Group,
            date = "28/11/2024",
            time = "08:00",
            co2Reduced = "15 kg"
        ),
        HistoryItem(
            title = "Primeira Viagem Elétrica",
            description = "Realizou primeira viagem longa com veículo elétrico",
            category = "Transporte",
            categoryColor = EcoGreen,
            icon = Icons.Default.ElectricCar,
            date = "22/11/2024",
            time = "14:00",
            co2Reduced = "35 kg",
            isMilestone = true
        ),
        
        // METAS ADICIONAIS DE ENERGIA
        HistoryItem(
            title = "Energia Solar Doméstica",
            description = "Instalou sistema de energia solar em casa",
            category = "Energia",
            categoryColor = EcoGreenLight,
            icon = Icons.Default.SolarPower,
            date = "18/11/2024",
            time = "10:00",
            co2Reduced = "120 kg",
            isMilestone = true
        ),
        HistoryItem(
            title = "Casa Inteligente",
            description = "Implementou sistema de automação para economia de energia",
            category = "Energia",
            categoryColor = EcoGreenLight,
            icon = Icons.Default.Home,
            date = "12/11/2024",
            time = "15:30",
            co2Reduced = "45 kg"
        ),
        
        // METAS ADICIONAIS DE ALIMENTAÇÃO
        HistoryItem(
            title = "Horta Orgânica",
            description = "Criou horta orgânica em casa para consumo próprio",
            category = "Alimentação",
            categoryColor = EcoGreenAccent,
            icon = Icons.Default.Yard,
            date = "08/11/2024",
            time = "11:00",
            co2Reduced = "25 kg",
            isMilestone = true
        ),
        HistoryItem(
            title = "Veganismo Semanal",
            description = "Adotou dieta vegana por uma semana completa",
            category = "Alimentação",
            categoryColor = EcoGreenAccent,
            icon = Icons.Default.Eco,
            date = "01/11/2024",
            time = "19:00",
            co2Reduced = "18 kg"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AddHistoricoScreenPreview() {
    EcoTrackTheme {
        AddHistoricoScreen()
    }
}
