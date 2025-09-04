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
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroScreen(
    onContinue: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { 5 })
    var currentPage by remember { mutableStateOf(0) }
    
    // Auto-scroll effect
    LaunchedEffect(pagerState) {
        while (true) {
            delay(4000) // 4 seconds per banner
            val nextPage = (currentPage + 1) % 5
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
            .background(EcoDark)
    ) {
        // Background pattern
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            EcoDark,
                            EcoDarkSurface,
                            EcoDark
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
                color = EcoTextPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Tecnologia ESG para um Futuro Sustentável",
                color = EcoTextSecondary,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Banner Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) { page ->
                BannerCard(
                    banner = getBannerData()[page],
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Page indicators
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (currentPage == index) 12.dp else 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (currentPage == index) EcoGreen else EcoTextSecondary.copy(alpha = 0.3f)
                            )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Continue button
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EcoGreen,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Começar Jornada",
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

fun getBannerData(): List<BannerData> {
    return listOf(
        BannerData(
            title = "Tecnologia ESG",
            description = "Utilizamos tecnologia avançada para monitorar e reduzir sua pegada de carbono de forma inteligente e eficiente.",
            icon = Icons.Default.Science,
            iconColor = EcoGreen,
            textColor = Color.White,
            backgroundColor = EcoGreen.copy(alpha = 0.8f),
            benefits = listOf(
                "Monitoramento em tempo real",
                "Análise de dados precisos",
                "Relatórios detalhados",
                "Metas personalizadas"
            )
        ),
        BannerData(
            title = "Redução de CO₂",
            description = "Acompanhe sua jornada sustentável e veja o impacto real das suas escolhas no meio ambiente.",
            icon = Icons.Default.Eco,
            iconColor = EcoGreenLight,
            textColor = Color.White,
            backgroundColor = EcoGreenLight.copy(alpha = 0.8f),
            benefits = listOf(
                "Até 1000kg CO₂ reduzidos/ano",
                "Equivalente a plantar 50 árvores",
                "Economia de energia significativa",
                "Contribuição para o planeta"
            )
        ),
        BannerData(
            title = "Vantagens do App",
            description = "Interface intuitiva e funcionalidades que tornam a sustentabilidade acessível e motivadora.",
            icon = Icons.Default.Star,
            iconColor = EcoGreenAccent,
            textColor = Color.White,
            backgroundColor = EcoGreenAccent.copy(alpha = 0.8f),
            benefits = listOf(
                "Fácil de usar",
                "Gamificação sustentável",
                "Conquistas e selos",
                "Comunidade engajada"
            )
        ),
        BannerData(
            title = "Comprometimento",
            description = "Junte-se a milhares de usuários comprometidos com um futuro mais sustentável e responsável.",
            icon = Icons.Default.Handshake,
            iconColor = EcoGreen,
            textColor = Color.White,
            backgroundColor = EcoGreen.copy(alpha = 0.7f),
            benefits = listOf(
                "Movimento global",
                "Impacto coletivo",
                "Futuro sustentável",
                "Responsabilidade social"
            )
        ),
        BannerData(
            title = "Inovação Sustentável",
            description = "Combinação perfeita entre tecnologia de ponta e consciência ambiental para um mundo melhor.",
            icon = Icons.Default.Lightbulb,
            iconColor = EcoGreenLight,
            textColor = Color.White,
            backgroundColor = EcoGreenLight.copy(alpha = 0.7f),
            benefits = listOf(
                "Tecnologia verde",
                "Inovação responsável",
                "Soluções inteligentes",
                "Futuro promissor"
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
