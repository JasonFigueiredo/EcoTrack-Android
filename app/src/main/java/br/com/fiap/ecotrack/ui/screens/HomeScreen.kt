package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import br.com.fiap.ecotrack.ui.theme.LocalDynamicColors
import br.com.fiap.ecotrack.model.getAvailableTransportTypes
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToTransport: () -> Unit = {},
    onNavigateToEnergy: () -> Unit = {},
    onNavigateToFood: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val colors = LocalDynamicColors.current
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
                    .padding(top = 50.dp)
                    .padding(bottom = 45.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header de Boas-vindas
                item {
                    WelcomeHeaderCard(onNavigateToProfile = onNavigateToProfile)
                }
                
                // Resumo de Impacto Ambiental
                item {
                    EnvironmentalImpactCard()
                }
                
                // Estatísticas Rápidas
                item {
                    QuickStatsCard()
                }
                
                // Calculadoras Principais
                item {
                    MainCalculatorsCard(
                        onNavigateToTransport = onNavigateToTransport,
                        onNavigateToEnergy = onNavigateToEnergy,
                        onNavigateToFood = onNavigateToFood
                    )
                }
                
                // Metas e Conquistas
                item {
                    HomeGoalsAndAchievementsCard()
                }
                
                // Insights e Dicas
                item {
                    InsightsCard()
                }
                
                // Resumo de Impacto Total
                item {
                    ImpactSummaryCard()
                }
            }
        }
    }
}

@Composable
fun WelcomeHeaderCard(
    onNavigateToProfile: () -> Unit
) {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.green.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Olá, EcoWarrior! 🌱",
                    color = colors.green,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Sua jornada sustentável continua",
                    color = colors.textSecondary,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Hoje você pode fazer a diferença!",
                    color = colors.textPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            IconButton(
                onClick = onNavigateToProfile,
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = colors.green,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = colors.textOnGreen,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun EnvironmentalImpactCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = "Impacto Ambiental",
                    tint = colors.green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Seu Impacto Ambiental Hoje",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // CO₂ Principal
            Text(
                text = "12.1 kg CO₂",
                color = colors.green,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Meta: 8.0 kg CO₂",
                color = colors.textSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Barra de progresso
            LinearProgressIndicator(
                progress = { 0.66f }, // 12.1 / 8.0 = 1.51, mas limitado a 1.0
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = colors.green,
                trackColor = colors.surfaceVariant,
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ImpactStat(
                    value = "4.1 kg",
                    label = "Acima da Meta",
                    color = colors.warning,
                    icon = Icons.Default.TrendingUp
                )
                ImpactStat(
                    value = "2.3 árvores",
                    label = "Equivalente",
                    color = colors.green,
                    icon = Icons.Default.Park
                )
                ImpactStat(
                    value = "1.2 vidas",
                    label = "Impactadas",
                    color = colors.greenLight,
                    icon = Icons.Default.Favorite
                )
            }
        }
    }
}

@Composable
fun ImpactStat(
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
            fontSize = 16.sp,
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
fun QuickStatsCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Analytics,
                    contentDescription = "Estatísticas",
                    tint = colors.greenLight,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Estatísticas Rápidas",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickStat(
                    value = "7 dias",
                    label = "Dias Ativo",
                    color = colors.green,
                    icon = Icons.Default.CalendarToday
                )
                QuickStat(
                    value = "3 metas",
                    label = "Em Progresso",
                    color = colors.greenLight,
                    icon = Icons.Default.Flag
                )
                QuickStat(
                    value = "5 selos",
                    label = "Conquistados",
                    color = colors.greenAccent,
                    icon = Icons.Default.EmojiEvents
                )
            }
        }
    }
}

@Composable
fun QuickStat(
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
            fontSize = 16.sp,
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
fun MainCalculatorsCard(
    onNavigateToTransport: () -> Unit,
    onNavigateToEnergy: () -> Unit,
    onNavigateToFood: () -> Unit
) {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = "Calculadoras",
                    tint = colors.greenAccent,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Calculadoras Principais",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Use dados científicos da API Climatiq para cálculos precisos:",
                color = colors.textSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Calculadoras
            CalculatorItem(
                icon = Icons.Default.DirectionsCar,
                title = "Transporte",
                description = "Calcule emissões de viagens",
                co2Amount = "7.8 kg",
                color = colors.green,
                onClick = onNavigateToTransport
            )
            
            CalculatorItem(
                icon = Icons.Default.EnergySavingsLeaf,
                title = "Energia",
                description = "Monitore consumo energético",
                co2Amount = "2.1 kg",
                color = colors.greenLight,
                onClick = onNavigateToEnergy
            )
            
            CalculatorItem(
                icon = Icons.Default.Restaurant,
                title = "Alimentação",
                description = "Impacto das suas refeições",
                co2Amount = "2.2 kg",
                color = colors.greenAccent,
                onClick = onNavigateToFood
            )
        }
    }
}

@Composable
fun CalculatorItem(
    icon: ImageVector,
    title: String,
    description: String,
    co2Amount: String,
    color: Color,
    onClick: () -> Unit
) {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = colors.textPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = description,
                    color = colors.textSecondary,
                    fontSize = 12.sp
                )
            }
            
            Text(
                text = co2Amount,
                color = color,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Ir para",
                tint = colors.textSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun HomeGoalsAndAchievementsCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "Metas e Conquistas",
                    tint = colors.green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Metas e Conquistas",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GoalStat(
                    value = "3",
                    label = "Metas Ativas",
                    color = colors.green,
                    icon = Icons.Default.Flag
                )
                GoalStat(
                    value = "2",
                    label = "Concluídas",
                    color = colors.success,
                    icon = Icons.Default.CheckCircle
                )
                GoalStat(
                    value = "5",
                    label = "Selos Ganhos",
                    color = colors.greenAccent,
                    icon = Icons.Default.EmojiEvents
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "🎯 Meta da Semana: Reduzir 2kg de CO₂",
                color = colors.green,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = { 0.6f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = colors.green,
                trackColor = colors.surfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "60% concluído - 1.2kg restantes",
                color = colors.textSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun GoalStat(
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
            fontSize = 16.sp,
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
fun InsightsCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = "Insights",
                    tint = colors.greenLight,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Insights e Dicas",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InsightItem(
                icon = Icons.Default.DirectionsCar,
                title = "Dica de Transporte",
                description = "Use transporte público 2x por semana e reduza 1.5kg CO₂",
                color = colors.green
            )
            
            InsightItem(
                icon = Icons.Default.EnergySavingsLeaf,
                title = "Dica de Energia",
                description = "Desligue aparelhos em standby e economize 0.8kg CO₂",
                color = colors.greenLight
            )
            
            InsightItem(
                icon = Icons.Default.Restaurant,
                title = "Dica de Alimentação",
                description = "Reduza carne vermelha 1x por semana e economize 2.1kg CO₂",
                color = colors.greenAccent
            )
        }
    }
}

@Composable
fun InsightItem(
    icon: ImageVector,
    title: String,
    description: String,
    color: Color
) {
    val colors = LocalDynamicColors.current
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column {
            Text(
                text = title,
                color = colors.textPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                color = colors.textSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun ImpactSummaryCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = "Resumo de Impacto",
                    tint = colors.green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Resumo de Impacto Total",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Dados baseados em cálculos científicos da API Climatiq:",
                color = colors.textSecondary,
                fontSize = 12.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Tabela de impacto
            ImpactTable()
        }
    }
}

@Composable
fun ImpactTable() {
    val colors = LocalDynamicColors.current
    
    Column {
        // Header da tabela
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Ação",
                color = colors.textPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = "1 Semana",
                color = colors.textPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Text(
                text = "1 Mês",
                color = colors.textPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Text(
                text = "1 Ano",
                color = colors.textPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Linha separadora
        HorizontalDivider(
            color = colors.textSecondary.copy(alpha = 0.3f),
            thickness = 1.dp
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Dados da tabela
        getImpactData().forEach { impactItem ->
            ImpactTableRow(impactItem = impactItem)
            if (impactItem != getImpactData().last()) {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun ImpactTableRow(impactItem: ImpactItem) {
    val colors = LocalDynamicColors.current
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícone e nome da ação
        Row(
            modifier = Modifier.weight(2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = impactItem.icon,
                contentDescription = impactItem.action,
                tint = impactItem.color,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = impactItem.action,
                color = colors.textPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        // 1 Semana
        Text(
            text = impactItem.oneWeek,
            color = colors.textSecondary,
            fontSize = 9.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        
        // 1 Mês
        Text(
            text = impactItem.oneMonth,
            color = colors.textSecondary,
            fontSize = 9.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        
        // 1 Ano
        Text(
            text = impactItem.oneYear,
            color = colors.textSecondary,
            fontSize = 9.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

data class ImpactItem(
    val action: String,
    val icon: ImageVector,
    val color: Color,
    val oneWeek: String,
    val oneMonth: String,
    val oneYear: String
)

fun getImpactData(): List<ImpactItem> {
    val transportTypes = getAvailableTransportTypes()
    return listOf(
        ImpactItem(
            action = "Transporte sustentável",
            icon = transportTypes[0].icon, // Carro
            color = EcoGreen,
            oneWeek = "~15 kg",
            oneMonth = "~60 kg",
            oneYear = "~700 kg"
        ),
        ImpactItem(
            action = "Caminhar/pedalar",
            icon = transportTypes[6].icon, // Bicicleta
            color = EcoGreenLight,
            oneWeek = "~2 kg",
            oneMonth = "~10 kg",
            oneYear = "~120 kg"
        ),
        ImpactItem(
            action = "Transporte público",
            icon = transportTypes[4].icon, // Ônibus
            color = EcoGreenAccent,
            oneWeek = "~20 kg",
            oneMonth = "~80 kg",
            oneYear = "~900 kg"
        ),
        ImpactItem(
            action = "Reduzir carne vermelha",
            icon = Icons.Default.Restaurant,
            color = EcoGreen,
            oneWeek = "~10 kg",
            oneMonth = "~90 kg",
            oneYear = "~1000 kg"
        ),
        ImpactItem(
            action = "Economizar energia",
            icon = Icons.Default.ElectricalServices,
            color = EcoGreenLight,
            oneWeek = "~2-5 kg",
            oneMonth = "~30 kg",
            oneYear = "~600 kg"
        ),
        ImpactItem(
            action = "Reciclagem",
            icon = Icons.Default.Recycling,
            color = EcoGreenAccent,
            oneWeek = "~3 kg",
            oneMonth = "~15 kg",
            oneYear = "~180 kg"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    EcoTrackTheme {
        HomeScreen()
    }
}