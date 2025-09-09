package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAjudaScreen(
    onBackClick: () -> Unit = {}
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
        TopAppBar(
            title = {
                Text(
                    text = "Ajuda e Dicas",
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
                // Introdução e Bem-vindo
                item {
                    WelcomeCard()
                }
                
                // Guia de Início Rápido
                item {
                    QuickStartCard()
                }
                
                // Calculadoras e Ferramentas
                item {
                    CalculatorsCard()
                }
                
                // Sistema de Metas e Conquistas
                item {
                    GoalsAndAchievementsCard()
                }
                
                // Dicas Avançadas
                item {
                    AdvancedTipsCard()
                }
                
                // FAQ - Perguntas Frequentes
                item {
                    FAQCard()
                }
                
                // Suporte e Contato
                item {
                    SupportCard()
                }
            }
        }
    }
}

@Composable
fun WelcomeCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.green.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = "Dicas",
                tint = colors.green,
                modifier = Modifier.size(64.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Bem-vindo ao EcoTrack!",
                color = colors.green,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Seu Guia Completo para Sustentabilidade",
                color = colors.textSecondary,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Descubra como maximizar seu impacto ambiental positivo e tirar o máximo proveito de todas as funcionalidades do EcoTrack. Com dados científicos da API Climatiq, você terá informações precisas para tomar decisões mais sustentáveis.",
                color = colors.textSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun QuickStartCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
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
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Início Rápido",
                    tint = colors.green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Guia de Início Rápido",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            QuickStartStep(
                step = "1",
                title = "Complete seu Perfil",
                description = "Adicione informações básicas para personalizar sua experiência e cálculos mais precisos",
                icon = Icons.Default.Person,
                color = colors.green
            )
            
            QuickStartStep(
                step = "2",
                title = "Explore as Calculadoras",
                description = "Teste as calculadoras de Transporte, Energia e Alimentação para entender seu impacto atual",
                icon = Icons.Default.Calculate,
                color = colors.greenLight
            )
            
            QuickStartStep(
                step = "3",
                title = "Configure suas Metas",
                description = "Defina metas realistas de redução de CO₂ baseadas em seus hábitos atuais",
                icon = Icons.Default.Flag,
                color = colors.greenAccent
            )
            
            QuickStartStep(
                step = "4",
                title = "Monitore seu Progresso",
                description = "Acompanhe diariamente suas atividades e veja seu impacto ambiental melhorar",
                icon = Icons.Default.TrendingUp,
                color = colors.green
            )
        }
    }
}

@Composable
fun QuickStartStep(
    step: String,
    title: String,
    description: String,
    icon: ImageVector,
    color: Color
) {
    val colors = LocalDynamicColors.current
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Número do passo
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = color.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = step,
                color = color,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Ícone
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Conteúdo
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
fun CalculatorsCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
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
                    imageVector = Icons.Default.Calculate,
                    contentDescription = "Calculadoras",
                    tint = colors.greenLight,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Calculadoras e Ferramentas",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            CalculatorFeature(
                icon = Icons.Default.DirectionsCar,
                title = "Calculadora de Transporte",
                description = "Calcule emissões de CO₂ de diferentes meios de transporte usando dados reais da API Climatiq",
                tips = listOf(
                    "Registre todas as suas viagens diárias",
                    "Compare diferentes modalidades de transporte",
                    "Use os filtros por período para análises detalhadas",
                    "Acompanhe sua evolução semanal e mensal"
                ),
                color = colors.green
            )
            
            CalculatorFeature(
                icon = Icons.Default.EnergySavingsLeaf,
                title = "Monitor de Energia",
                description = "Monitore consumo energético e calcule emissões baseadas em dados regionais",
                tips = listOf(
                    "Configure filtros por estado e tipo de consumidor",
                    "Registre uso de eletrodomésticos específicos",
                    "Acompanhe médias de consumo por período",
                    "Use insights para otimizar seu consumo"
                ),
                color = colors.greenLight
            )
            
            CalculatorFeature(
                icon = Icons.Default.Restaurant,
                title = "Calculadora de Alimentação",
                description = "Monitore impacto ambiental de suas escolhas alimentares com dados nutricionais",
                tips = listOf(
                    "Registre refeições com peso e tipo de alimento",
                    "Acompanhe dados nutricionais (calorias, proteínas, etc.)",
                    "Compare impactos de diferentes alimentos",
                    "Use filtros por tipo de refeição e período"
                ),
                color = colors.greenAccent
            )
        }
    }
}

@Composable
fun CalculatorFeature(
    icon: ImageVector,
    title: String,
    description: String,
    tips: List<String>,
    color: Color
) {
    val colors = LocalDynamicColors.current
    
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Row(
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
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "💡 Dicas para maximizar o uso:",
                    color = color,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                
                tips.forEach { tip ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "• ",
                            color = color,
                            fontSize = 12.sp
                        )
                        Text(
                            text = tip,
                            color = colors.textSecondary,
                            fontSize = 11.sp,
                            lineHeight = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GoalsAndAchievementsCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
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
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "Metas e Conquistas",
                    tint = colors.greenAccent,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Sistema de Metas e Conquistas",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "O sistema de metas e conquistas do EcoTrack foi projetado para motivar e acompanhar seu progresso sustentável:",
                color = colors.textSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            AchievementTip(
                icon = Icons.Default.Flag,
                title = "Configurando Metas Inteligentes",
                description = "Defina metas realistas baseadas em seus hábitos atuais. O app sugere metas personalizadas usando dados da API Climatiq.",
                color = colors.green
            )
            
            AchievementTip(
                icon = Icons.Default.TrendingUp,
                title = "Acompanhando Progresso",
                description = "Visualize seu progresso com gráficos detalhados, estatísticas de impacto e comparações temporais.",
                color = colors.greenLight
            )
            
            AchievementTip(
                icon = Icons.Default.EmojiEvents,
                title = "Desbloqueando Conquistas",
                description = "Ganhe selos e conquistas por atingir marcos importantes em sua jornada sustentável.",
                color = colors.greenAccent
            )
            
            AchievementTip(
                icon = Icons.Default.History,
                title = "Histórico Detalhado",
                description = "Acesse seu histórico completo de atividades, metas atingidas e impacto ambiental acumulado.",
                color = colors.green
            )
        }
    }
}

@Composable
fun AchievementTip(
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
fun AdvancedTipsCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
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
                    imageVector = Icons.Default.TipsAndUpdates,
                    contentDescription = "Dicas Avançadas",
                    tint = colors.green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Dicas Avançadas para Máximo Impacto",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            AdvancedTip(
                title = "Use Filtros Estratégicos",
                description = "Aproveite os filtros por período, estado e tipo de consumidor para análises mais precisas e personalizadas.",
                icon = Icons.Default.FilterList,
                color = colors.green
            )
            
            AdvancedTip(
                title = "Integre com sua Rotina",
                description = "Configure lembretes diários para registrar atividades e manter consistência nos dados.",
                icon = Icons.Default.Schedule,
                color = colors.greenLight
            )
            
            AdvancedTip(
                title = "Analise Tendências",
                description = "Use os gráficos e estatísticas para identificar padrões e oportunidades de melhoria.",
                icon = Icons.Default.Analytics,
                color = colors.greenAccent
            )
            
            AdvancedTip(
                title = "Compartilhe Conquistas",
                description = "Compartilhe suas conquistas sustentáveis para inspirar outros e criar impacto social.",
                icon = Icons.Default.Share,
                color = colors.green
            )
            
            AdvancedTip(
                title = "Dados Científicos Reais",
                description = "Todos os cálculos são baseados em dados reais da API Climatiq, garantindo precisão científica.",
                icon = Icons.Default.Science,
                color = colors.greenLight
            )
        }
    }
}

@Composable
fun AdvancedTip(
    title: String,
    description: String,
    icon: ImageVector,
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
fun FAQCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
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
                    imageVector = Icons.Default.Help,
                    contentDescription = "FAQ",
                    tint = colors.greenAccent,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Perguntas Frequentes",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            FAQItem(
                question = "Como os cálculos de CO₂ são feitos?",
                answer = "Utilizamos dados científicos reais da API Climatiq, que fornece fatores de emissão atualizados e precisos para diferentes atividades e regiões.",
                color = colors.green
            )
            
            FAQItem(
                question = "Posso confiar na precisão dos dados?",
                answer = "Sim! Todos os dados são baseados em pesquisas científicas e atualizados regularmente. A API Climatiq é referência mundial em dados de emissão.",
                color = colors.greenLight
            )
            
            FAQItem(
                question = "Como definir metas realistas?",
                answer = "O app analisa seus hábitos atuais e sugere metas baseadas em reduções graduais e sustentáveis, usando dados regionais específicos.",
                color = colors.greenAccent
            )
            
            FAQItem(
                question = "Meus dados são seguros?",
                answer = "Sim! Seguimos rigorosamente a LGPD e utilizamos criptografia para proteger todas as suas informações pessoais.",
                color = colors.green
            )
            
            FAQItem(
                question = "Como maximizar meu impacto positivo?",
                answer = "Use o app diariamente, configure lembretes, analise tendências e compartilhe suas conquistas para inspirar outros.",
                color = colors.greenLight
            )
        }
    }
}

@Composable
fun FAQItem(
    question: String,
    answer: String,
    color: Color
) {
    val colors = LocalDynamicColors.current
    
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = "❓ $question",
            color = color,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = answer,
            color = colors.textSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    }
}

@Composable
fun SupportCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
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
                    imageVector = Icons.Default.Support,
                    contentDescription = "Suporte",
                    tint = colors.green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Suporte e Contato",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Nossa equipe está sempre pronta para ajudar você a maximizar seu impacto sustentável:",
                color = colors.textSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            SupportItem(
                icon = Icons.Default.Email,
                title = "Email de Suporte",
                description = "suporte@ecotrack.com.br",
                color = colors.green
            )
            
            SupportItem(
                icon = Icons.Default.Phone,
                title = "Telefone",
                description = "+55 (11) 3000-0000",
                color = colors.greenLight
            )
            
            SupportItem(
                icon = Icons.Default.Chat,
                title = "Chat Online",
                description = "Disponível 24 horas por dia",
                color = colors.greenAccent
            )
            
            SupportItem(
                icon = Icons.Default.Web,
                title = "Website",
                description = "www.ecotrack.com.br",
                color = colors.green
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "💡 Dica: Para respostas mais rápidas, use o chat online ou envie um email detalhando sua dúvida.",
                color = colors.green,
                fontSize = 12.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}

@Composable
fun SupportItem(
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
        verticalAlignment = Alignment.CenterVertically
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
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                color = colors.textSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddAjudaScreenPreview() {
    EcoTrackTheme {
        AddAjudaScreen()
    }
}