package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Login
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import br.com.fiap.ecotrack.R
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    onGetStartedClick: () -> Unit = {},
    onGoogleLogin: () -> Unit = {},
    onFacebookLogin: () -> Unit = {},
    onAppleLogin: () -> Unit = {},
    onHelpClick: () -> Unit = {}
) {
    var showHelpDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
            .padding(start = 16.dp, end = 16.dp, top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header com título e botão de ajuda
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "EcoTrack",
                color = EcoTextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { showHelpDialog = true },
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = EcoGreen,
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Help,
                    contentDescription = "Ajuda",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(30.dp))
        
        Image(
            painter = painterResource(id = R.drawable.ecotracklogo),
            contentDescription = "Logo do EcoTrack",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .padding(end = 40.dp),
        )

        Spacer(modifier = Modifier.height(0.dp))
        
        // Título principal
        Text(
            text = "Bem Vindo ao EcoTrack",
            color = EcoTextPrimary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Descrição
        Text(
            text = "Acompanhe suas escolhas diárias e reduza sua pegada de CO₂.",
            color = EcoTextPrimary,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(60.dp))
        
        // Botão Get Started
        Button(
            onClick = onGetStartedClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = EcoTextPrimary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Vamos começar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Botões de login social
        SocialLoginButton(
            text = "Logar com Google",
            onClick = onGoogleLogin,
            iconRes = R.drawable.google
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        SocialLoginButton(
            text = "Logar com Facebook",
            onClick = onFacebookLogin,
            iconRes = R.drawable.facebook
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        SocialLoginButton(
            text = "Logar com Apple",
            onClick = onAppleLogin,
            iconRes = R.drawable.apple
        )
    }
    
    // Popup de Ajuda
    if (showHelpDialog) {
        HelpDialog(
            onDismiss = { showHelpDialog = false }
        )
    }
}

@Composable
fun HelpDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            colors = CardDefaults.cardColors(
                containerColor = EcoDark
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Header do Dialog
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ajuda Rápida",
                        color = EcoTextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = EcoTextPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Conteúdo do Dialog
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Introdução
                    item {
                        HelpIntroCard()
                    }
                    
                    // Seção de Login
                    item {
                        LoginHelpSection()
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
    }
}

@Composable
fun LoginHelpSection() {
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
                    imageVector = Icons.AutoMirrored.Filled.Login,
                    contentDescription = "Login",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Opções de Acesso",
                    color = EcoGreen,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Login com Google
            LoginOptionCard(
                title = "Login com Google",
                description = "Acesse rapidamente com sua conta Google",
                icon = Icons.Default.AccountCircle,
                color = EcoGreenLight,
                steps = listOf(
                    "Toque no botão 'Logar com Google'",
                    "Selecione sua conta Google",
                    "Autorize o acesso aos dados básicos",
                    "Comece a usar o EcoTrack imediatamente"
                )
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Login com Facebook
            LoginOptionCard(
                title = "Login com Facebook",
                description = "Conecte-se usando sua conta Facebook",
                icon = Icons.Default.Person,
                color = EcoGreenAccent,
                steps = listOf(
                    "Toque no botão 'Logar com Facebook'",
                    "Faça login na sua conta Facebook",
                    "Permita o acesso aos dados necessários",
                    "Sua conta será criada automaticamente"
                )
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Login com Apple
            LoginOptionCard(
                title = "Login com Apple",
                description = "Use sua Apple ID para acessar",
                icon = Icons.Default.PhoneIphone,
                color = EcoGreen,
                steps = listOf(
                    "Toque no botão 'Logar com Apple'",
                    "Use Face ID, Touch ID ou senha",
                    "Confirme os dados de privacidade",
                    "Aproveite o acesso seguro e rápido"
                )
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Começar sem login
            LoginOptionCard(
                title = "Começar sem Login",
                description = "Use o app sem criar conta",
                icon = Icons.Default.PlayArrow,
                color = EcoGreenLight,
                steps = listOf(
                    "Toque em 'Vamos começar'",
                    "Explore todas as funcionalidades",
                    "Seus dados ficam apenas no dispositivo",
                    "Faça login depois se quiser sincronizar"
                )
            )
        }
    }
}

@Composable
fun LoginOptionCard(
    title: String,
    description: String,
    icon: ImageVector,
    color: Color,
    steps: List<String>
) {
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
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    color = EcoTextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(6.dp))
            
            Text(
                text = description,
                color = EcoTextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            steps.forEachIndexed { index, step ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "${index + 1}.",
                        color = color,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(16.dp)
                    )
                    Text(
                        text = step,
                        color = EcoTextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (index < steps.size - 1) {
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    onClick: () -> Unit,
    iconRes: Int
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = EcoTextPrimary
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier
                    .size(35.dp)
                    .padding(end = 12.dp)
            )
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    EcoTrackTheme {
        WelcomeScreen()
    }
}
