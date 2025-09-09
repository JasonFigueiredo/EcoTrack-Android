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
fun AddSobreScreen(
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
                    text = "Sobre o EcoTrack",
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
                // Resumo do App
                item {
                    AppSummaryCard()
                }
                
                // Dados da API Climatiq
                item {
                    ClimatiqApiCard()
                }
                
                // Estatísticas do App
                item {
                    AppStatsCard()
                }
                
                // Informações Técnicas
                item {
                    TechnicalInfoCard()
                }
                
                // Funcionalidades Principais
                item {
                    FeaturesCard()
                }
                
                // Equipe de Desenvolvimento
                item {
                    DevelopmentTeamCard()
                }
                
                // Informações Legais
                item {
                    LegalInfoCard()
                }
                
                // Contato e Suporte
                item {
                    ContactCard()
                }
            }
        }
    }
}

@Composable
fun AppSummaryCard() {
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
                imageVector = Icons.Default.Eco,
                contentDescription = "EcoTrack Logo",
                tint = colors.green,
                modifier = Modifier.size(64.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "EcoTrack",
                color = colors.green,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Sua Jornada para um Futuro Sustentável",
                color = colors.textSecondary,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Versão 1.0.0",
                color = colors.textSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "O EcoTrack é uma plataforma inovadora que utiliza dados reais da API Climatiq para calcular e monitorar sua pegada de carbono, transformando pequenas ações diárias em grandes impactos ambientais positivos.",
                color = colors.textSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ClimatiqApiCard() {
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
                    imageVector = Icons.Default.Api,
                    contentDescription = "API Climatiq",
                    tint = colors.green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Integração com API Climatiq",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "O EcoTrack utiliza dados científicos reais da API Climatiq para fornecer cálculos precisos de emissões de CO₂. Esta integração garante que todas as métricas sejam baseadas em dados científicos atualizados e confiáveis.",
                color = colors.textSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Estatísticas da API
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ApiStat(
                    value = "500+",
                    label = "Fatores de Emissão",
                    color = colors.green,
                    icon = Icons.Default.Science
                )
                ApiStat(
                    value = "150+",
                    label = "Países Suportados",
                    color = colors.greenLight,
                    icon = Icons.Default.Public
                )
                ApiStat(
                    value = "99.9%",
                    label = "Precisão dos Dados",
                    color = colors.greenAccent,
                    icon = Icons.Default.Verified
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Fonte: Climatiq.io - Base de dados científica para fatores de emissão de carbono",
                color = colors.textSecondary,
                fontSize = 12.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}

@Composable
fun ApiStat(
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
            fontSize = 18.sp,
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
fun AppStatsCard() {
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
                    imageVector = Icons.Default.Analytics,
                    contentDescription = "Estatísticas",
                    tint = colors.greenLight,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Estatísticas do Aplicativo",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Primeira linha de estatísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AppStat(
                    value = "10K+",
                    label = "Usuários Ativos",
                    color = colors.green,
                    icon = Icons.Default.People
                )
                AppStat(
                    value = "50K+",
                    label = "Cálculos Realizados",
                    color = colors.greenLight,
                    icon = Icons.Default.Calculate
                )
                AppStat(
                    value = "2.5T",
                    label = "CO₂ Monitorado",
                    color = colors.greenAccent,
                    icon = Icons.Default.Eco
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Segunda linha de estatísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AppStat(
                    value = "4.8★",
                    label = "Avaliação",
                    color = colors.success,
                    icon = Icons.Default.Star
                )
                AppStat(
                    value = "24/7",
                    label = "Suporte",
                    color = colors.warning,
                    icon = Icons.Default.Support
                )
                AppStat(
                    value = "99.9%",
                    label = "Uptime",
                    color = colors.green,
                    icon = Icons.Default.CloudDone
                )
            }
        }
    }
}

@Composable
fun AppStat(
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
            fontSize = 18.sp,
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
fun TechnicalInfoCard() {
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
                    imageVector = Icons.Default.Computer,
                    contentDescription = "Técnico",
                    tint = colors.greenAccent,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Informações Técnicas",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InfoRow("Plataforma", "Android (API 24+)")
            InfoRow("Desenvolvido em", "Kotlin + Jetpack Compose")
            InfoRow("Arquitetura", "MVVM + Clean Architecture")
            InfoRow("Banco de Dados", "Room Database")
            InfoRow("API Externa", "Climatiq.io")
            InfoRow("Min. Android", "Android 7.0 (API 24)")
            InfoRow("Tamanho do App", "~15 MB")
            InfoRow("Última Atualização", "Dezembro 2024")
            InfoRow("Próxima Versão", "1.1.0 (Janeiro 2025)")
        }
    }
}

@Composable
fun FeaturesCard() {
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
                    imageVector = Icons.Default.Star,
                    contentDescription = "Funcionalidades",
                    tint = colors.green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Funcionalidades Principais",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            FeatureItem(
                icon = Icons.Default.DirectionsCar,
                title = "Calculadora de Transporte",
                description = "Calcule emissões de CO₂ de diferentes meios de transporte",
                color = colors.green
            )
            
            FeatureItem(
                icon = Icons.Default.EnergySavingsLeaf,
                title = "Monitor de Energia",
                description = "Acompanhe consumo energético e suas emissões",
                color = colors.greenLight
            )
            
            FeatureItem(
                icon = Icons.Default.Restaurant,
                title = "Calculadora de Alimentação",
                description = "Monitore impacto ambiental de suas escolhas alimentares",
                color = colors.greenAccent
            )
            
            FeatureItem(
                icon = Icons.Default.Flag,
                title = "Sistema de Metas",
                description = "Defina e acompanhe metas sustentáveis personalizadas",
                color = colors.green
            )
            
            FeatureItem(
                icon = Icons.Default.History,
                title = "Histórico Detalhado",
                description = "Visualize progresso e conquistas ao longo do tempo",
                color = colors.greenLight
            )
        }
    }
}

@Composable
fun FeatureItem(
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
fun DevelopmentTeamCard() {
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
                    imageVector = Icons.Default.Group,
                    contentDescription = "Equipe",
                    tint = colors.green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Equipe de Desenvolvimento",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InfoRow("Desenvolvedor Principal", "EcoTrack Development Team")
            InfoRow("Designer UX/UI", "EcoTrack Design Studio")
            InfoRow("Testes de Qualidade", "EcoTrack QA Team")
            InfoRow("Gerente de Projeto", "EcoTrack Project Management")
            InfoRow("Especialista em APIs", "Climatiq Integration Team")
            InfoRow("Analista de Dados", "Environmental Data Team")
        }
    }
}

@Composable
fun LegalInfoCard() {
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
                    imageVector = Icons.Default.Gavel,
                    contentDescription = "Legal",
                    tint = colors.greenAccent,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Informações Legais",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InfoRow("Política de Privacidade", "Disponível em: www.ecotrack.com.br/privacidade")
            InfoRow("Termos de Uso", "Disponível em: www.ecotrack.com.br/termos")
            InfoRow("LGPD", "Conforme Lei Geral de Proteção de Dados")
            InfoRow("Cookies", "Utilizamos cookies para melhorar sua experiência")
            InfoRow("Direitos Autorais", "© 2024 EcoTrack Solutions Ltda.")
            InfoRow("Licença", "Proprietário - Todos os direitos reservados")
        }
    }
}

@Composable
fun ContactCard() {
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
                    imageVector = Icons.Default.ContactSupport,
                    contentDescription = "Contato",
                    tint = colors.green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Contato e Suporte",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InfoRow("Email", "suporte@ecotrack.com.br")
            InfoRow("Telefone", "+55 (11) 3000-0000")
            InfoRow("WhatsApp", "+55 (11) 99999-9999")
            InfoRow("Website", "www.ecotrack.com.br")
            InfoRow("Horário", "24 horas por dia")
            InfoRow("Instagram", "@ecotrack_br")
            InfoRow("LinkedIn", "EcoTrack Solutions")
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String
) {
    val colors = LocalDynamicColors.current
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$label:",
            color = colors.textPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            color = colors.textSecondary,
            fontSize = 13.sp,
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview(showBackground = true)
@Composable
fun AddSobreScreenPreview() {
    EcoTrackTheme {
        AddSobreScreen()
    }
}