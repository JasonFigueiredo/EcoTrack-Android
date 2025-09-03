package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.R
import br.com.fiap.ecotrack.ui.theme.EcoTrackTheme
import br.com.fiap.ecotrack.ui.theme.EcoGreen
import br.com.fiap.ecotrack.ui.theme.EcoDark
import br.com.fiap.ecotrack.ui.theme.EcoTextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    onGetStartedClick: () -> Unit = {},
    onGoogleLogin: () -> Unit = {},
    onFacebookLogin: () -> Unit = {},
    onAppleLogin: () -> Unit = {},
    onHelpClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
            .padding(24.dp),
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
                onClick = onHelpClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Help,
                    contentDescription = "Ajuda",
                    tint = EcoDark,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(80.dp))
        
        // Título principal
        Text(
            text = "Welcome to EcoTrack",
            color = EcoTextPrimary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Descrição
        Text(
            text = "Track your daily choices and reduce your CO₂ footprint.",
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
                text = "Get Started",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Botões de login social
        SocialLoginButton(
            text = "Continue with Google",
            onClick = onGoogleLogin,
            icon = "G"
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        SocialLoginButton(
            text = "Continue with Facebook",
            onClick = onFacebookLogin,
            icon = "f"
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        SocialLoginButton(
            text = "Continue with Apple",
            onClick = onAppleLogin,
            icon = "🍎"
        )
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    onClick: () -> Unit,
    icon: String
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
            Text(
                text = icon,
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 12.dp)
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
