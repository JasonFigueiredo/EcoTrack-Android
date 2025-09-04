package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = {},
    onOpenGoals: () -> Unit = {},
    onOpenConquistas: () -> Unit = {},
    onOpenAjuda: () -> Unit = {},
    onOpenSobre: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
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
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = EcoTextPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = EcoDark
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Perfil do usuário
            UserProfileCard()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Estatísticas
            Text(
                text = "Suas Estatísticas",
                color = EcoTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getProfileItems()) { item ->
                    val onClick: () -> Unit = when (item.title) {
                        "Metas" -> onOpenGoals
                        "Conquistas" -> onOpenConquistas
                        "Ajuda" -> onOpenAjuda
                        "Sobre" -> onOpenSobre
                        else -> ({})
                    }
                    ProfileItemCard(item = item, onClick = onClick)
                }
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
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(80.dp)
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
                    modifier = Modifier.size(40.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "EcoUser",
                color = EcoTextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Membro desde Janeiro 2024",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Estatísticas rápidas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatisticItem("12.5 kg", "CO₂ Hoje")
                StatisticItem("45 dias", "Streak")
                StatisticItem("8.2 kg", "Meta Diária")
            }
        }
    }
}

@Composable
fun StatisticItem(
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = EcoGreen,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            color = EcoTextSecondary,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ProfileItemCard(
    item: ProfileItem,
    onClick: () -> Unit = {}
) {
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
                imageVector = item.icon,
                contentDescription = item.title,
                tint = item.color,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = item.title,
                color = EcoTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .weight(1f)
                    .let { it }
            )
            
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Ir para",
                    tint = EcoTextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

data class ProfileItem(
    val title: String,
    val icon: ImageVector,
    val color: Color
)

fun getProfileItems(): List<ProfileItem> {
    return listOf(
        ProfileItem(
            title = "Configurações",
            icon = Icons.Default.Settings,
            color = EcoTextPrimary
        ),
        ProfileItem(
            title = "Histórico",
            icon = Icons.Default.History,
            color = EcoGreen
        ),
        ProfileItem(
            title = "Metas",
            icon = Icons.Default.Flag,
            color = EcoGreenLight
        ),
        ProfileItem(
            title = "Conquistas",
            icon = Icons.Default.EmojiEvents,
            color = EcoGreenAccent
        ),
        ProfileItem(
            title = "Ajuda",
            icon = Icons.Default.Help,
            color = EcoTextSecondary
        ),
        ProfileItem(
            title = "Sobre",
            icon = Icons.Default.Info,
            color = EcoTextSecondary
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
