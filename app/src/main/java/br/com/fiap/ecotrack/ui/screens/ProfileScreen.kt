package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*
import br.com.fiap.ecotrack.ui.theme.LocalDynamicColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = {},
    onOpenGoals: () -> Unit = {},
    onOpenConquistas: () -> Unit = {},
    onOpenAjuda: () -> Unit = {},
    onOpenSobre: () -> Unit = {},
    onOpenHistorico: () -> Unit = {},
    onOpenConfiguracoes: () -> Unit = {}
) {
    val colors = LocalDynamicColors.current
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Perfil",
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
            // Perfil do usuário
            item {
                UserProfileCard()
            }
            
            // Gráfico de progresso semanal
            item {
                WeeklyProgressChart()
            }
            
            // Estatísticas detalhadas
            item {
                DetailedStatsCard()
            }
            
            // Seção de configurações e funcionalidades
            item {
                Text(
                    text = "Configurações e Funcionalidades",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(getProfileItems()) { item ->
                val onClick: () -> Unit = when (item.title) {
                    "Configurações" -> onOpenConfiguracoes
                    "Metas" -> onOpenGoals
                    "Conquistas" -> onOpenConquistas
                    "Histórico" -> onOpenHistorico
                    "Ajuda" -> onOpenAjuda
                    "Sobre" -> onOpenSobre
                    else -> ({})
                }
                ModernProfileItemCard(item = item, onClick = onClick)
            }
        }
    }
}

@Composable
fun UserProfileCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar com gradiente
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        color = EcoGreen,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar",
                    tint = EcoTextOnGreen,
                    modifier = Modifier.size(50.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "EcoUser",
                color = EcoTextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Membro desde Janeiro 2024",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Estatísticas rápidas melhoradas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ModernStatisticItem("12.5 kg", "CO₂ Hoje", EcoGreen)
                ModernStatisticItem("45 dias", "Streak", EcoGreenLight)
                ModernStatisticItem("8.2 kg", "Meta Diária", EcoGreenAccent)
            }
        }
    }
}

@Composable
fun ModernStatisticItem(
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
            fontSize = 18.sp,
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
fun WeeklyProgressChart() {
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
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = "Progresso",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Progresso Semanal",
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Gráfico de barras semanal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                ProgressBar("Seg", 0.8f, EcoGreen, "10.2 kg")
                ProgressBar("Ter", 0.6f, EcoGreenLight, "7.8 kg")
                ProgressBar("Qua", 0.9f, EcoGreen, "11.5 kg")
                ProgressBar("Qui", 0.7f, EcoGreenAccent, "8.9 kg")
                ProgressBar("Sex", 0.5f, EcoWarning, "6.3 kg")
                ProgressBar("Sáb", 0.3f, EcoError, "3.8 kg")
                ProgressBar("Dom", 0.4f, EcoTextSecondary, "5.1 kg")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Resumo semanal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total: 53.6 kg CO₂",
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = "Meta: 56.0 kg CO₂",
                    color = EcoGreenAccent,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun ProgressBar(
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
                .width(25.dp)
                .height((height * 60).dp)
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
            text = value,
            color = color,
            fontSize = 8.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun DetailedStatsCard() {
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
                    imageVector = Icons.Default.Analytics,
                    contentDescription = "Estatísticas",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Estatísticas Detalhadas",
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Grid de estatísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Tempo no App",
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "2h 15min",
                        color = EcoGreen,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Árvores Salvas",
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "3.2",
                        color = EcoGreenLight,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Nível",
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Eco Expert",
                        color = EcoGreenAccent,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Barra de progresso do nível
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Progresso para próximo nível",
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "75%",
                        color = EcoGreenAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = 0.75f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = EcoGreen,
                    trackColor = EcoDarkSurface
                )
            }
        }
    }
}

@Composable
fun ModernProfileItemCard(
    item: ProfileItem,
    onClick: () -> Unit = {}
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
                // Ícone com fundo colorido
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = item.color.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = item.color,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.title,
                        color = EcoTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = item.description,
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                }
                
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Ir para",
                        tint = EcoTextSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

data class ProfileItem(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val description: String
)

fun getProfileItems(): List<ProfileItem> {
    return listOf(
        ProfileItem(
            title = "Configurações",
            icon = Icons.Default.Settings,
            color = EcoTextPrimary,
            description = "Personalize suas preferências e configurações do app"
        ),
        ProfileItem(
            title = "Histórico",
            icon = Icons.Default.History,
            color = EcoGreen,
            description = "Visualize seu histórico de emissões e atividades"
        ),
        ProfileItem(
            title = "Metas",
            icon = Icons.Default.Flag,
            color = EcoGreenLight,
            description = "Defina e acompanhe suas metas de sustentabilidade"
        ),
        ProfileItem(
            title = "Conquistas",
            icon = Icons.Default.EmojiEvents,
            color = EcoGreenAccent,
            description = "Veja suas conquistas e badges desbloqueadas"
        ),
        ProfileItem(
            title = "Ajuda",
            color = EcoTextSecondary,
            icon = Icons.AutoMirrored.Filled.Help,
            description = "Tire suas dúvidas e aprenda a usar o app"
        ),
        ProfileItem(
            title = "Sobre",
            icon = Icons.Default.Info,
            color = EcoTextSecondary,
            description = "Informações sobre o app e versão"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    EcoTrackTheme {
        ProfileScreen()
    }
}
