package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.window.Dialog
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddConquistaScreen(
    onBackClick: () -> Unit = {}
) {
    var selectedBadge by remember { mutableStateOf<AchievementBadge?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
            .padding(bottom = 30.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Minhas Conquistas",
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
            // Dashboard Principal
            item {
                MainDashboardCard()
            }
            
            // Selos de Conquistas
            item {
                Text(
                    text = "Selos de Conquistas",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                AchievementBadgesRow(
                    onBadgeClick = { badge ->
                        selectedBadge = badge
                    }
                )
            }
            
            // Estatísticas Detalhadas
            item {
                Text(
                    text = "Estatísticas Detalhadas",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                DetailedStatsGrid()
            }
            
            // Gráficos de Progresso
            item {
                Text(
                    text = "Progresso das Metas",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                ProgressChartsSection()
            }
            
            // Histórico de Conquistas
            item {
                Text(
                    text = "Histórico de Conquistas",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(getAchievementHistory()) { achievement ->
                AchievementHistoryCard(achievement)
            }
        }
    }

    // Popup do Selo Selecionado
    selectedBadge?.let { badge ->
        BadgeDetailDialog(
            badge = badge,
            onDismiss = { selectedBadge = null }
        )
    }
}

@Composable
fun MainDashboardCard() {
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
            Text(
                text = "Dashboard de Impacto",
                color = EcoTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DashboardStat(
                    value = "2.8",
                    unit = "toneladas",
                    label = "CO₂ Reduzido",
                    color = EcoGreen
                )
                DashboardStat(
                    value = "140",
                    unit = "árvores",
                    label = "Equivalente",
                    color = EcoGreenLight
                )
                DashboardStat(
                    value = "3.2",
                    unit = "anos",
                    label = "Vida Salva",
                    color = EcoGreenAccent
                )
            }
        }
    }
}

@Composable
fun DashboardStat(
    value: String,
    unit: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = color,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = unit,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = label,
            color = EcoTextSecondary,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AchievementBadgesRow(
    onBadgeClick: (AchievementBadge) -> Unit = {}
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(getAchievementBadges()) { badge ->
            AchievementBadge(
                badge = badge,
                onClick = { onBadgeClick(badge) }
            )
        }
    }
}

@Composable
fun AchievementBadge(
    badge: AchievementBadge,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.size(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (badge.isUnlocked) badge.color else EcoDarkSurface
        ),
        shape = CircleShape,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = badge.icon,
                    contentDescription = badge.name,
                    tint = if (badge.isUnlocked) Color.White else EcoTextSecondary,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = badge.name,
                    color = if (badge.isUnlocked) Color.White else EcoTextSecondary,
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun DetailedStatsGrid() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Meta Semanal",
                value = "85%",
                subtitle = "15/18 metas atingidas",
                icon = Icons.Default.TrendingUp,
                color = EcoGreen,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Meta Mensal",
                value = "72%",
                subtitle = "65/90 metas atingidas",
                icon = Icons.Default.CalendarMonth,
                color = EcoGreenLight,
                modifier = Modifier.weight(1f)
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Meta Anual",
                value = "68%",
                subtitle = "248/365 metas atingidas",
                icon = Icons.Default.Star,
                color = EcoGreenAccent,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Streak Atual",
                value = "45",
                subtitle = "dias consecutivos",
                icon = Icons.Default.LocalFireDepartment,
                color = EcoGreen,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = value,
                color = color,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = title,
                color = EcoTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            
            Text(
                text = subtitle,
                color = EcoTextSecondary,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProgressChartsSection() {
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
                text = "Progresso das Metas",
                color = EcoTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Gráfico de barras simples
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                ProgressBar("Transporte", 0.85f, EcoGreen)
                ProgressBar("Energia", 0.78f, EcoGreenLight)
                ProgressBar("Alimentação", 0.92f, EcoGreenAccent)
                ProgressBar("Resíduos", 0.65f, EcoGreen)
            }
        }
    }
}

@Composable
fun ProgressBar(
    label: String,
    progress: Float,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(40.dp)
                .height((progress * 80).dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(4.dp)
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = EcoTextSecondary,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "${(progress * 100).toInt()}%",
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AchievementHistoryCard(achievement: AchievementHistory) {
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
            Icon(
                imageVector = achievement.icon,
                contentDescription = achievement.title,
                tint = achievement.color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = achievement.title,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = achievement.description,
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = achievement.date,
                    color = EcoTextSecondary,
                    fontSize = 10.sp
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = achievement.co2Reduced,
                    color = achievement.color,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = achievement.category,
                    color = EcoTextSecondary,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun BadgeDetailDialog(
    badge: AchievementBadge,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = EcoDarkSurface
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Header com ícone e título
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = badge.icon,
                            contentDescription = badge.name,
                            tint = badge.color,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = badge.name,
                            color = EcoTextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = EcoTextSecondary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Status do Selo
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (badge.isUnlocked) badge.color.copy(alpha = 0.2f) else EcoDarkSurface
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (badge.isUnlocked) Icons.Default.CheckCircle else Icons.Default.Lock,
                            contentDescription = if (badge.isUnlocked) "Desbloqueado" else "Bloqueado",
                            tint = if (badge.isUnlocked) badge.color else EcoTextSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (badge.isUnlocked) "Selo Desbloqueado!" else "Selo Bloqueado",
                            color = if (badge.isUnlocked) badge.color else EcoTextSecondary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Progresso
                if (badge.progressRequired > 0) {
                    Text(
                        text = "Progresso: ${badge.progressCurrent}/${badge.progressRequired}",
                        color = EcoTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = badge.progressCurrent.toFloat() / badge.progressRequired.toFloat(),
                        modifier = Modifier.fillMaxWidth(),
                        color = badge.color,
                        trackColor = EcoTextSecondary.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Dias necessários
                if (badge.daysRequired > 0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Dias Consecutivos:",
                            color = EcoTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${badge.daysCompleted}/${badge.daysRequired}",
                            color = badge.color,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // CO₂ Reduzido
                if (badge.co2Reduced.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = badge.color.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Eco,
                                contentDescription = "CO₂ Reduzido",
                                tint = badge.color,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "CO₂ Reduzido: ${badge.co2Reduced}",
                                color = badge.color,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Benefícios
                if (badge.benefits.isNotEmpty()) {
                    Text(
                        text = "Benefícios Alcançados:",
                        color = EcoTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    badge.benefits.forEach { benefit ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Benefício",
                                tint = badge.color,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = benefit,
                                color = EcoTextSecondary,
                                fontSize = 12.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

// Data Classes
data class AchievementBadge(
    val name: String,
    val icon: ImageVector,
    val color: Color,
    val isUnlocked: Boolean,
    val category: String = "",
    val progressRequired: Int = 0,
    val progressCurrent: Int = 0,
    val co2Reduced: String = "",
    val benefits: List<String> = emptyList(),
    val daysRequired: Int = 0,
    val daysCompleted: Int = 0
)

data class AchievementHistory(
    val title: String,
    val description: String,
    val date: String,
    val icon: ImageVector,
    val color: Color,
    val co2Reduced: String,
    val category: String
)

// Funções de dados
fun getAchievementBadges(): List<AchievementBadge> {
    return listOf(
        AchievementBadge(
            name = "Semanal",
            icon = Icons.Default.Star,
            color = EcoGreen,
            isUnlocked = true,
            category = "Tempo",
            progressRequired = 7,
            progressCurrent = 7,
            co2Reduced = "45 kg",
            benefits = listOf(
                "7 dias consecutivos de metas atingidas",
                "Redução de 45 kg de CO₂",
                "Equivalente a 2.25 árvores plantadas",
                "Acesso a selos especiais semanais"
            ),
            daysRequired = 7,
            daysCompleted = 7
        ),
        AchievementBadge(
            name = "Mensal",
            icon = Icons.Default.Star,
            color = EcoGreenLight,
            isUnlocked = true,
            category = "Tempo",
            progressRequired = 30,
            progressCurrent = 30,
            co2Reduced = "180 kg",
            benefits = listOf(
                "30 dias consecutivos de metas atingidas",
                "Redução de 180 kg de CO₂",
                "Equivalente a 9 árvores plantadas",
                "Desbloqueio de funcionalidades avançadas"
            ),
            daysRequired = 30,
            daysCompleted = 30
        ),
        AchievementBadge(
            name = "Anual",
            icon = Icons.Default.Star,
            color = EcoGreenAccent,
            isUnlocked = false,
            category = "Tempo",
            progressRequired = 365,
            progressCurrent = 248,
            co2Reduced = "2.8 toneladas",
            benefits = listOf(
                "365 dias consecutivos de metas atingidas",
                "Redução de 2.8 toneladas de CO₂",
                "Equivalente a 140 árvores plantadas",
                "Status de Mestre Ambiental"
            ),
            daysRequired = 365,
            daysCompleted = 248
        ),
        AchievementBadge(
            name = "Transporte",
            icon = Icons.Default.DirectionsCar,
            color = EcoGreen,
            isUnlocked = true,
            category = "Categoria",
            progressRequired = 100,
            progressCurrent = 100,
            co2Reduced = "320 kg",
            benefits = listOf(
                "100 km evitados de carro",
                "Redução de 320 kg de CO₂",
                "Uso de transporte público/ativo",
                "Melhoria da saúde cardiovascular"
            ),
            daysRequired = 45,
            daysCompleted = 45
        ),
        AchievementBadge(
            name = "Energia",
            icon = Icons.Default.EnergySavingsLeaf,
            color = EcoGreenLight,
            isUnlocked = true,
            category = "Categoria",
            progressRequired = 50,
            progressCurrent = 50,
            co2Reduced = "150 kg",
            benefits = listOf(
                "50 kWh economizados",
                "Redução de 150 kg de CO₂",
                "Uso de lâmpadas LED",
                "Desligamento consciente de eletrônicos"
            ),
            daysRequired = 30,
            daysCompleted = 30
        ),
        AchievementBadge(
            name = "Alimentação",
            icon = Icons.Default.Restaurant,
            color = EcoGreenAccent,
            isUnlocked = true,
            category = "Categoria",
            progressRequired = 80,
            progressCurrent = 80,
            co2Reduced = "200 kg",
            benefits = listOf(
                "80 refeições sem carne vermelha",
                "Redução de 200 kg de CO₂",
                "Consumo de alimentos locais",
                "Redução do desperdício alimentar"
            ),
            daysRequired = 60,
            daysCompleted = 60
        ),
        AchievementBadge(
            name = "Resíduos",
            icon = Icons.Default.Recycling,
            color = EcoGreen,
            isUnlocked = false,
            category = "Categoria",
            progressRequired = 100,
            progressCurrent = 65,
            co2Reduced = "120 kg",
            benefits = listOf(
                "100% de resíduos reciclados",
                "Redução de 120 kg de CO₂",
                "Compostagem de orgânicos",
                "Redução do uso de plásticos"
            ),
            daysRequired = 90,
            daysCompleted = 65
        ),
        AchievementBadge(
            name = "Água",
            icon = Icons.Default.WaterDrop,
            color = EcoGreenLight,
            isUnlocked = true,
            category = "Categoria",
            progressRequired = 60,
            progressCurrent = 60,
            co2Reduced = "80 kg",
            benefits = listOf(
                "60 litros de água economizados/dia",
                "Redução de 80 kg de CO₂",
                "Uso de dispositivos economizadores",
                "Reutilização de água da chuva"
            ),
            daysRequired = 40,
            daysCompleted = 40
        )
    )
}

fun getAchievementHistory(): List<AchievementHistory> {
    return listOf(
        AchievementHistory(
            title = "Meta Semanal Atingida",
            description = "Reduziu 15 kg de CO₂ em transporte",
            date = "Esta semana",
            icon = Icons.Default.DirectionsCar,
            color = EcoGreen,
            co2Reduced = "15 kg",
            category = "Transporte"
        ),
        AchievementHistory(
            title = "Meta Mensal Atingida",
            description = "Economizou 45 kWh de energia",
            date = "Este mês",
            icon = Icons.Default.EnergySavingsLeaf,
            color = EcoGreenLight,
            co2Reduced = "32 kg",
            category = "Energia"
        ),
        AchievementHistory(
            title = "Meta Semanal Atingida",
            description = "Reduziu consumo de carne vermelha",
            date = "Esta semana",
            icon = Icons.Default.Restaurant,
            color = EcoGreenAccent,
            co2Reduced = "12 kg",
            category = "Alimentação"
        ),
        AchievementHistory(
            title = "Meta Mensal Atingida",
            description = "Reciclou 80% dos resíduos",
            date = "Este mês",
            icon = Icons.Default.Recycling,
            color = EcoGreen,
            co2Reduced = "18 kg",
            category = "Resíduos"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AddConquistaScreenPreview() {
    EcoTrackTheme {
        AddConquistaScreen()
    }
}
