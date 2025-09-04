package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAjudaScreen(
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
                    text = "Ajuda Rápida",
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
            // Introdução
            item {
                HelpIntroCard()
            }
            
            // Tópicos de Ajuda
            items(getHelpTopics()) { topic ->
                HelpTopicCard(topic)
            }
            
            // Contato de Suporte
            item {
                SupportContactCard()
            }
        }
    }
}

@Composable
fun HelpIntroCard() {
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
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = "Dica",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Bem-vindo ao EcoTrack!",
                    color = EcoGreen,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Este aplicativo foi desenvolvido para ajudar você a reduzir sua pegada de carbono e contribuir para um futuro mais sustentável. Aqui está um guia rápido de como usar:",
                color = EcoTextPrimary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun HelpTopicCard(topic: HelpTopic) {
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = topic.icon,
                    contentDescription = topic.title,
                    tint = topic.color,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = topic.title,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = topic.description,
                color = EcoTextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            
            if (topic.steps.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                topic.steps.forEachIndexed { index, step ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "${index + 1}.",
                            color = topic.color,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.width(24.dp)
                        )
                        Text(
                            text = step,
                            color = EcoTextSecondary,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (index < topic.steps.size - 1) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SupportContactCard() {
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Support,
                    contentDescription = "Suporte",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Precisa de mais ajuda?",
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Entre em contato conosco:",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            ContactItem(
                icon = Icons.Default.Email,
                text = "suporte@ecotrack.com",
                color = EcoGreen
            )
            
            ContactItem(
                icon = Icons.Default.Phone,
                text = "+55 (11) 99999-9999",
                color = EcoGreenLight
            )
            
            ContactItem(
                text = "Chat online disponível 24/7",
                color = EcoGreenAccent,
                icon = Icons.AutoMirrored.Filled.Chat
            )
        }
    }
}

@Composable
fun ContactItem(
    icon: ImageVector,
    text: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = EcoTextSecondary,
            fontSize = 13.sp
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
}

// Data Classes
data class HelpTopic(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val steps: List<String> = emptyList()
)

// Funções de dados
fun getHelpTopics(): List<HelpTopic> {
    return listOf(
        HelpTopic(
            title = "Como começar",
            description = "Primeiros passos para usar o EcoTrack e começar sua jornada sustentável:",
            icon = Icons.Default.PlayArrow,
            color = EcoGreen,
            steps = listOf(
                "Complete seu perfil com informações básicas",
                "Explore as diferentes categorias (Transporte, Energia, Alimentação)",
                "Configure suas metas pessoais de redução de CO₂",
                "Comece a registrar suas atividades diárias"
            )
        ),
        HelpTopic(
            title = "Monitorando Transporte",
            description = "Acompanhe e reduza suas emissões relacionadas ao transporte:",
            icon = Icons.Default.DirectionsCar,
            color = EcoGreenLight,
            steps = listOf(
                "Acesse a seção 'Transporte' no menu principal",
                "Registre suas viagens diárias (carro, ônibus, bicicleta)",
                "Veja o impacto em CO₂ de cada modalidade",
                "Acompanhe seu progresso semanal e mensal"
            )
        ),
        HelpTopic(
            title = "Economizando Energia",
            description = "Reduza o consumo de energia e suas emissões de carbono:",
            icon = Icons.Default.EnergySavingsLeaf,
            color = EcoGreenAccent,
            steps = listOf(
                "Acesse a seção 'Energia' para ver tipos de consumo",
                "Registre o uso de eletrodomésticos e iluminação",
                "Configure metas de economia de kWh",
                "Monitore sua redução de CO₂ em tempo real"
            )
        ),
        HelpTopic(
            title = "Alimentação Sustentável",
            description = "Adote hábitos alimentares mais sustentáveis:",
            icon = Icons.Default.Restaurant,
            color = EcoGreen,
            steps = listOf(
                "Explore a seção 'Alimentação' para ver impactos",
                "Registre suas refeições e escolhas alimentares",
                "Reduza o consumo de carne vermelha",
                "Prefira alimentos locais e da estação"
            )
        ),
        HelpTopic(
            title = "Acompanhando Metas",
            description = "Configure e acompanhe suas metas de redução de CO₂:",
            icon = Icons.Default.Flag,
            color = EcoGreenLight,
            steps = listOf(
                "Acesse 'Metas' no seu perfil",
                "Configure metas semanais, mensais e anuais",
                "Acompanhe seu progresso com gráficos visuais",
                "Celebre suas conquistas e selos desbloqueados"
            )
        ),
        HelpTopic(
            title = "Visualizando Conquistas",
            description = "Veja seu impacto ambiental e conquistas alcançadas:",
            icon = Icons.Default.EmojiEvents,
            color = EcoGreenAccent,
            steps = listOf(
                "Acesse 'Conquistas' no seu perfil",
                "Visualize selos desbloqueados por categoria",
                "Acompanhe estatísticas detalhadas de impacto",
                "Veja seu histórico de metas atingidas"
            )
        ),
        HelpTopic(
            title = "Dicas de Uso",
            description = "Maximize o uso do aplicativo com estas dicas:",
            icon = Icons.Default.TipsAndUpdates,
            color = EcoGreen,
            steps = listOf(
                "Use o aplicativo diariamente para manter consistência",
                "Configure lembretes para não esquecer de registrar atividades",
                "Compartilhe suas conquistas com amigos e família",
                "Participe de desafios comunitários quando disponíveis"
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AddAjudaScreenPreview() {
    EcoTrackTheme {
        AddAjudaScreen()
    }
}
