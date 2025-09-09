package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.R
import br.com.fiap.ecotrack.ui.theme.*
import br.com.fiap.ecotrack.ui.theme.LocalDynamicColors
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroScreen(
    onContinue: () -> Unit = {}
) {
    val colors = LocalDynamicColors.current
    val pagerState = rememberPagerState(pageCount = { 6 })
    var currentPage by remember { mutableIntStateOf(0) }
    
    // Auto-scroll effect
    LaunchedEffect(pagerState) {
        while (true) {
            delay(5000) // 5 seconds per banner
            val nextPage = (currentPage + 1) % 6
            pagerState.animateScrollToPage(nextPage)
            currentPage = nextPage
        }
    }
    
    // Update current page when pager changes
    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        // Background pattern
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colors.background,
                            colors.surface,
                            colors.background
                        )
                    )
                )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            
            // Logo
            Image(
                painter = painterResource(id = R.drawable.ecotracklogo),
                contentDescription = "Logo do EcoTrack",
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(end = 30.dp)
            )
            
            Spacer(modifier = Modifier.height(0.dp))
            
            // Title
            Text(
                text = "EcoTrack",
                color = colors.green,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Transforme Pequenas Ações em Grandes Impactos",
                color = colors.textSecondary,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
            
            Spacer(modifier = Modifier.height(10.dp))
            
            // Banner Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
            ) { page ->
                BannerCard(
                    banner = getBannerData()[page],
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(2.dp))
            
            // Page indicators
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(6) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (currentPage == index) 12.dp else 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (currentPage == index) colors.green else colors.textSecondary.copy(alpha = 0.3f)
                            )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Continue button
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom =50.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.green,
                    contentColor = colors.textOnGreen
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Começar Minha Jornada Sustentável",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun BannerCard(
    banner: BannerData,
    modifier: Modifier = Modifier
) {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = banner.backgroundColor
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon
            Icon(
                imageVector = banner.icon,
                contentDescription = banner.title,
                tint = banner.iconColor,
                modifier = Modifier.size(64.dp)
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Title
            Text(
                text = banner.title,
                color = banner.textColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Description
            Text(
                text = banner.description,
                color = banner.textColor.copy(alpha = 0.9f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Benefits list
            banner.benefits.forEach { benefit ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Benefício",
                        tint = banner.iconColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = benefit,
                        color = banner.textColor,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

data class BannerData(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconColor: Color,
    val textColor: Color,
    val backgroundColor: Color,
    val benefits: List<String>
)

@Composable
fun getBannerData(): List<BannerData> {
    val colors = LocalDynamicColors.current
    
    return listOf(
        BannerData(
            title = "🌍 Problema: Pegada de Carbono Descontrolada",
            description = "Você sabia que cada pessoa produz em média 4.8 toneladas de CO₂ por ano? Sem monitoramento, é impossível saber o real impacto das suas ações no meio ambiente.",
            icon = Icons.Default.Warning,
            iconColor = Color(0xFFFF6B6B),
            textColor = Color.White,
            backgroundColor = Color(0xFFFF6B6B).copy(alpha = 0.8f),
            benefits = listOf(
                "4.8 toneladas CO₂/ano por pessoa",
                "Impacto invisível no dia a dia",
                "Falta de consciência ambiental",
                "Dificuldade em medir progresso"
            )
        ),
        BannerData(
            title = "🚗 Solução: Calculadora de Transporte Inteligente",
            description = "Monitore suas viagens e descubra o impacto real de cada modalidade de transporte. Dados científicos da API Climatiq para cálculos precisos.",
            icon = Icons.Default.DirectionsCar,
            iconColor = Color(0xFF4ECDC4),
            textColor = Color.White,
            backgroundColor = Color(0xFF4ECDC4).copy(alpha = 0.8f),
            benefits = listOf(
                "Cálculos baseados em dados reais",
                "Comparação entre modalidades",
                "Filtros por período e região",
                "Economia de até 2.5T CO₂/ano"
            )
        ),
        BannerData(
            title = "⚡ Solução: Monitor de Energia Avançado",
            description = "Controle seu consumo energético e reduza custos. Análise detalhada por estado, tipo de consumidor e aparelhos específicos.",
            icon = Icons.Default.EnergySavingsLeaf,
            iconColor = Color(0xFF45B7D1),
            textColor = Color.White,
            backgroundColor = Color(0xFF45B7D1).copy(alpha = 0.8f),
            benefits = listOf(
                "Dados regionais específicos",
                "Análise por tipo de consumidor",
                "Insights para economia",
                "Redução de até 40% na conta"
            )
        ),
        BannerData(
            title = "🍎 Solução: Calculadora de Alimentação Sustentável",
            description = "Descubra o impacto ambiental dos seus alimentos. Dados nutricionais completos e cálculos de emissão por tipo de alimento.",
            icon = Icons.Default.Restaurant,
            iconColor = Color(0xFF96CEB4),
            textColor = Color.White,
            backgroundColor = Color(0xFF96CEB4).copy(alpha = 0.8f),
            benefits = listOf(
                "Dados nutricionais completos",
                "Impacto por tipo de alimento",
                "Filtros por refeição e período",
                "Redução de até 1.5T CO₂/ano"
            )
        ),
        BannerData(
            title = "🎯 Solução: Sistema de Metas e Conquistas",
            description = "Transforme sustentabilidade em um jogo motivador. Metas personalizadas, conquistas e acompanhamento de progresso em tempo real.",
            icon = Icons.Default.EmojiEvents,
            iconColor = Color(0xFFFFD93D),
            textColor = Color.White,
            backgroundColor = Color(0xFFFFD93D).copy(alpha = 0.8f),
            benefits = listOf(
                "Metas personalizadas e realistas",
                "Sistema de gamificação",
                "Conquistas e selos",
                "Progresso visual em tempo real"
            )
        ),
        BannerData(
            title = "📊 Resultado: Impacto Real e Mensurável",
            description = "Veja o resultado das suas ações: árvores salvas, vidas impactadas, CO₂ reduzido. Dados científicos que comprovam seu impacto positivo.",
            icon = Icons.Default.TrendingUp,
            iconColor = Color(0xFF6BCF7F),
            textColor = Color.White,
            backgroundColor = Color(0xFF6BCF7F).copy(alpha = 0.8f),
            benefits = listOf(
                "Até 4.8T CO₂ reduzidas/ano",
                "Equivalente a 240 árvores plantadas",
                "Impacto em 12+ vidas humanas",
                "Contribuição real para o planeta"
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
    EcoTrackTheme {
        IntroScreen()
    }
}