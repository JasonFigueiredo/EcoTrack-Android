package br.com.fiap.ecotrack.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.ecotrack.ui.screens.*

@Composable
fun EcoTrackNavigation(navController: NavHostController) {
    val context = LocalContext.current
    
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(
                onGetStartedClick = {
                    navController.navigate("home")
                },
                onGoogleLogin = {
                    openSocialLogin(context, "https://accounts.google.com/signin")
                },
                onFacebookLogin = {
                    openSocialLogin(context, "https://www.facebook.com/login")
                },
                onAppleLogin = {
                    openSocialLogin(context, "https://appleid.apple.com/sign-in")
                },
                onHelpClick = {
                    // Implementar tela de ajuda
                }
            )
        }
        
        composable("home") {
            HomeScreen(
                onNavigateToTransport = {
                    navController.navigate("transport")
                },
                onNavigateToEnergy = {
                    navController.navigate("energy")
                },
                onNavigateToFood = {
                    navController.navigate("food")
                },
                onNavigateToProfile = {
                    navController.navigate("profile")
                }
            )
        }
        
        composable("transport") {
            TransportScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onAddTransport = {
                    navController.navigate("add_transport")
                }
            )
        }
        
        composable("add_transport") {
            AddTransportScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveTransport = { transportData ->
                    // Aqui você pode salvar os dados do transporte
                    // Por enquanto, apenas volta para a tela anterior
                    navController.popBackStack()
                }
            )
        }
        
        composable("energy") {
            EnergyScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("food") {
            FoodScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("profile") {
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

private fun openSocialLogin(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: Exception) {
        // Se não conseguir abrir o link, pode implementar um fallback
        // Por exemplo, mostrar uma mensagem ou abrir no navegador padrão
        val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        fallbackIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(fallbackIntent)
    }
}
