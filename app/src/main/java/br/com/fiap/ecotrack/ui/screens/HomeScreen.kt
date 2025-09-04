package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
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
import br.com.fiap.ecotrack.ui.theme.EcoTrackTheme
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToTransport: () -> Unit = {},
    onNavigateToEnergy: () -> Unit = {},
    onNavigateToFood: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
            .padding(start = 16.dp, end = 16.dp, top = 48.dp),
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Olá, EcoUser!",
                    color = EcoTextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Vamos reduzir sua pegada de carbono",
                    color = EcoTextSecondary,
                    fontSize = 14.sp
                )
            }
            
            IconButton(
                onClick = onNavigateToProfile,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = EcoGreen,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = EcoTextOnGreen,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Card de resumo de CO2
        CO2SummaryCard()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Seção de atividades
        Text(
            text = "Suas Atividades",
            color = EcoTextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Lista de atividades
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(getActivityItems()) { activity ->
                ActivityCard(
                    activity = activity,
                    onClick = {
                        when (activity.title) {
                            "Transporte" -> onNavigateToTransport()
                            "Energia" -> onNavigateToEnergy()
                            "Alimentação" -> onNavigateToFood()
                        }
                    }
                )
            }
            
            // Resumo de Impacto Total
            item {
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Resumo Rápido de Impacto Total (Aproximado)",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                ImpactSummaryTable()
            }
        }
    }
}

@Composable
fun CO2SummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pegada de CO₂ Hoje",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "12.5 kg",
                color = EcoGreen,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Meta: 8.0 kg",
                color = EcoTextSecondary,
                fontSize = 12.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Barra de progresso
            LinearProgressIndicator(
            progress = { 0.6f },
            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
            color = EcoGreen,
            trackColor = EcoDarkSurfaceVariant,
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Você está 4.5kg acima da meta",
                color = EcoWarning,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ActivityCard(
    activity: ActivityItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
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
                imageVector = activity.icon,
                contentDescription = activity.title,
                tint = activity.color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.title,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = activity.subtitle,
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
            }
            
            Text(
                text = activity.co2Amount,
                color = activity.color,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Ir para",
                tint = EcoTextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

data class ActivityItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color,
    val co2Amount: String
)

@Composable
fun ImpactSummaryTable() {
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
            // Header da tabela
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Ação",
                    color = EcoTextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(2f)
                )
                Text(
                    text = "1 Semana",
                    color = EcoTextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "1 Mês",
                    color = EcoTextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "1 Ano",
                    color = EcoTextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Linha separadora
            HorizontalDivider(
                color = EcoTextSecondary.copy(alpha = 0.3f),
                thickness = 1.dp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Dados da tabela
            getImpactData().forEach { impactItem ->
                ImpactTableRow(impactItem = impactItem)
                if (impactItem != getImpactData().last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ImpactTableRow(impactItem: ImpactItem) {
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
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = impactItem.action,
                color = EcoTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        // 1 Semana
        Text(
            text = impactItem.oneWeek,
            color = EcoTextSecondary,
            fontSize = 11.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        
        // 1 Mês
        Text(
            text = impactItem.oneMonth,
            color = EcoTextSecondary,
            fontSize = 11.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        
        // 1 Ano
        Text(
            text = impactItem.oneYear,
            color = EcoTextSecondary,
            fontSize = 11.sp,
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
    return listOf(
        ImpactItem(
            action = "Transporte sustentável",
            icon = Icons.Default.DirectionsCar,
            color = EcoGreen,
            oneWeek = "~15 kg",
            oneMonth = "~60 kg",
            oneYear = "~700 kg"
        ),
        ImpactItem(
            action = "Caminhar/pedalar",
            icon = Icons.AutoMirrored.Filled.DirectionsWalk,
            color = EcoGreenLight,
            oneWeek = "~2 kg",
            oneMonth = "~10 kg",
            oneYear = "~120 kg"
        ),
        ImpactItem(
            action = "Transporte público",
            icon = Icons.Default.DirectionsBus,
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
        ),
        ImpactItem(
            action = "Reflorestamento",
            icon = Icons.Default.Park,
            color = EcoGreen,
            oneWeek = "-",
            oneMonth = "~80 kg",
            oneYear = "~1000 kg"
        ),
        ImpactItem(
            action = "Consumo consciente",
            icon = Icons.Default.ShoppingCart,
            color = EcoGreenLight,
            oneWeek = "~5-10 kg",
            oneMonth = "~40 kg",
            oneYear = "~500 kg"
        )
    )
}

fun getActivityItems(): List<ActivityItem> {
    return listOf(
        ActivityItem(
            title = "Transporte",
            subtitle = "Carro, ônibus, avião",
            icon = Icons.Default.DirectionsCar,
            color = EcoGreen,
            co2Amount = "8.2 kg"
        ),
        ActivityItem(
            title = "Energia",
            subtitle = "Eletricidade, gás",
            icon = Icons.Default.ElectricalServices,
            color = EcoGreenLight,
            co2Amount = "2.1 kg"
        ),
        ActivityItem(
            title = "Alimentação",
            subtitle = "Refeições e bebidas",
            icon = Icons.Default.Restaurant,
            color = EcoGreenAccent,
            co2Amount = "2.2 kg"
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
