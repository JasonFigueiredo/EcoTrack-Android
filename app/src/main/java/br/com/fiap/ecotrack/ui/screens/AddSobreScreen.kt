package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSobreScreen(
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
                    text = "Sobre o EcoTrack",
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
            // Logo e Nome do App
            item {
                AppHeaderCard()
            }
            
            // Informações da Empresa
            item {
                CompanyInfoCard()
            }
            
            // Descrição do Aplicativo
            item {
                AppDescriptionCard()
            }
            
            // Informações Técnicas
            item {
                TechnicalInfoCard()
            }
            
            // Equipe de Desenvolvimento
            item {
                DevelopmentTeamCard()
            }
            
            // Direitos Autorais e Licenças
            item {
                CopyrightCard()
            }
            
            // Política de Privacidade e Termos
            item {
                LegalInfoCard()
            }
            
            // Redes Sociais e Contato
            item {
                SocialMediaCard()
            }
        }
    }
}

@Composable
fun AppHeaderCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoGreen.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = "EcoTrack Logo",
                tint = EcoGreen,
                modifier = Modifier.size(64.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "EcoTrack",
                color = EcoGreen,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Sua Jornada para um Futuro Sustentável",
                color = EcoTextSecondary,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Versão 1.0.0",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun CompanyInfoCard() {
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
                    imageVector = Icons.Default.Business,
                    contentDescription = "Empresa",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Informações da Empresa",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InfoRow("Nome", "EcoTrack Solutions Ltda.")
            InfoRow("CNPJ", "12.345.678/0001-90")
            InfoRow("Endereço", "Av. Paulista, 1000 - São Paulo/SP")
            InfoRow("CEP", "01310-100")
            InfoRow("Telefone", "+55 (11) 3000-0000")
            InfoRow("Email", "contato@ecotrack.com")
            InfoRow("Website", "www.ecotrack.com.br")
        }
    }
}

@Composable
fun AppDescriptionCard() {
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
                    imageVector = Icons.Default.Info,
                    contentDescription = "Informações",
                    tint = EcoGreenLight,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Sobre o Aplicativo",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "O EcoTrack é uma plataforma inovadora desenvolvida para conscientizar e capacitar usuários a reduzir sua pegada de carbono através de monitoramento inteligente de atividades diárias.",
                color = EcoTextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Nossa missão é democratizar o acesso a informações sobre sustentabilidade, transformando pequenas ações diárias em grandes impactos ambientais positivos.",
                color = EcoTextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun TechnicalInfoCard() {
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
                    imageVector = Icons.Default.Computer,
                    contentDescription = "Técnico",
                    tint = EcoGreenAccent,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Informações Técnicas",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InfoRow("Plataforma", "Android (API 24+)")
            InfoRow("Desenvolvido em", "Kotlin + Jetpack Compose")
            InfoRow("Arquitetura", "MVVM + Clean Architecture")
            InfoRow("Banco de Dados", "Room Database")
            InfoRow("Min. Android", "Android 7.0 (API 24)")
            InfoRow("Tamanho do App", "~15 MB")
            InfoRow("Última Atualização", "Dezembro 2024")
        }
    }
}

@Composable
fun DevelopmentTeamCard() {
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
                    imageVector = Icons.Default.Group,
                    contentDescription = "Equipe",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Equipe de Desenvolvimento",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InfoRow("Desenvolvedor Principal", "EcoTrack Development Team")
            InfoRow("Designer UX/UI", "EcoTrack Design Studio")
            InfoRow("Testes de Qualidade", "EcoTrack QA Team")
            InfoRow("Gerente de Projeto", "EcoTrack Project Management")
        }
    }
}

@Composable
fun CopyrightCard() {
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
                    imageVector = Icons.Default.Copyright,
                    contentDescription = "Direitos Autorais",
                    tint = EcoGreenLight,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Direitos Autorais e Licenças",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "© 2024 EcoTrack Solutions Ltda. Todos os direitos reservados.",
                color = EcoTextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Este aplicativo e todo seu conteúdo são protegidos por leis de direitos autorais brasileiras e internacionais.",
                color = EcoTextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "É proibida a reprodução, distribuição ou modificação sem autorização expressa da empresa.",
                color = EcoTextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun LegalInfoCard() {
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
                    imageVector = Icons.Default.Gavel,
                    contentDescription = "Legal",
                    tint = EcoGreenAccent,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Informações Legais",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InfoRow("Política de Privacidade", "Disponível em: www.ecotrack.com.br/privacidade")
            InfoRow("Termos de Uso", "Disponível em: www.ecotrack.com.br/termos")
            InfoRow("LGPD", "Conforme Lei Geral de Proteção de Dados")
            InfoRow("Cookies", "Utilizamos cookies para melhorar sua experiência")
        }
    }
}

@Composable
fun SocialMediaCard() {
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
                    imageVector = Icons.Default.Share,
                    contentDescription = "Redes Sociais",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Redes Sociais e Contato",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InfoRow("Instagram", "@ecotrack_br")
            InfoRow("Facebook", "EcoTrack Brasil")
            InfoRow("LinkedIn", "EcoTrack Solutions")
            InfoRow("YouTube", "EcoTrack Oficial")
            InfoRow("Twitter", "@ecotrack_br")
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$label:",
            color = EcoTextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            color = EcoTextSecondary,
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
