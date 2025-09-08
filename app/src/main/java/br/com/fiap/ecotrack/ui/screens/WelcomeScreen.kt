package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import br.com.fiap.ecotrack.R
import br.com.fiap.ecotrack.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    onGetStartedClick: () -> Unit = {},
    onGoogleLogin: () -> Unit = {},
    onFacebookLogin: () -> Unit = {},
    onAppleLogin: () -> Unit = {},
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
            .padding(start = 16.dp, end = 16.dp, top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            iconUrl = "https://img.icons8.com/?size=512&id=17949&format=png",
            iconSize = 40.dp
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        SocialLoginButton(
            text = "Logar com Facebook",
            onClick = onFacebookLogin,
            iconUrl = "https://img.icons8.com/?size=48&id=13912&format=png",
            iconSize = 45.dp
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        SocialLoginButton(
            text = "Logar com Apple",
            onClick = onAppleLogin,
            iconUrl = "https://img.icons8.com/?size=100&id=85906&format=png&color=FFFFFF",
            iconSize = 45.dp
        )
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    onClick: () -> Unit,
    iconRes: Int? = null,
    iconUrl: String? = null,
    iconSize: Dp = 35.dp
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
            when {
                iconUrl != null -> {
                    AsyncImage(
                        model = iconUrl,
                        contentDescription = text,
                        modifier = Modifier
                            .size(iconSize)
                            .padding(end = 12.dp)
                    )
                }
                iconRes != null -> {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = text,
                        modifier = Modifier
                            .size(iconSize)
                            .padding(end = 12.dp)
                    )
                }
            }
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
