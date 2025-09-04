package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHistoricoScreen(
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
            .padding(bottom = 30.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Histórico de Conquistas",
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
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = EcoDark
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Resumo do Histórico
            item {
                HistorySummaryCard()
            }
            
            // Filtros por Categoria
            item {
                CategoryFilterSection()
            }
            
            // Lista de Histórico
            items(getHistoryItems()) { historyItem ->
                HistoryItemCard(historyItem)
            }
        }
    }
}

@Composable
fun HistorySummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoGreen.copy(alpha = 0.1f)
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
                    imageVector = Icons.Default.History,
                    contentDescription = "Histórico",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Resumo do Histórico",
                    color = EcoGreen,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryStat(
                    value = "156",
                    label = "Conquistas",
                    color = EcoGreen
                )
                SummaryStat(
                    value = "2.8",
                    label = "Ton CO₂",
                    color = EcoGreenLight
                )
                SummaryStat(
                    value = "45",
                    label = "Dias Ativo",
                    color = EcoGreenAccent
                )
            }
        }
    }
}

@Composable
fun SummaryStat(
    value: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = color,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            color = EcoTextSecondary,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CategoryFilterSection() {
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
            Text(
                text = "Filtrar por Categoria",
                color = EcoTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CategoryChip("Todas", EcoGreen, true)
                CategoryChip("Transporte", EcoGreenLight, false)
                CategoryChip("Energia", EcoGreenAccent, false)
                CategoryChip("Alimentação", EcoGreen, false)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CategoryChip("Resíduos", EcoGreenLight, false)
                CategoryChip("Água", EcoGreenAccent, false)
                CategoryChip("Metas", EcoGreen, false)
                CategoryChip("Especiais", EcoGreenLight, false)
            }
        }
    }
}

@Composable
fun CategoryChip(
    text: String,
    color: Color,
    isSelected: Boolean
) {
    Card(
        modifier = Modifier.padding(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) color else EcoDarkSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else EcoTextSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun HistoryItemCard(historyItem: HistoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
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
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = historyItem.description,
                    color = EcoTextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 16.sp
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Data",
                        tint = EcoTextSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = historyItem.date,
                        color = EcoTextSecondary,
                        fontSize = 11.sp
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Horário",
                        tint = EcoTextSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = historyItem.time,
                        color = EcoTextSecondary,
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
                    color = EcoTextSecondary,
                    fontSize = 10.sp
                )
                
                if (historyItem.isMilestone) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Marco",
                        tint = EcoGreen,
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
