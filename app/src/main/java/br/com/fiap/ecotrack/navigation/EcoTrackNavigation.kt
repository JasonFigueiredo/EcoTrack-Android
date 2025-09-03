package br.com.fiap.ecotrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.ecotrack.ui.screens.*

@Composable
fun EcoTrackNavigation(navController: NavHostController) {
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
                    navController.navigate("home")
                },
                onFacebookLogin = {
                    navController.navigate("home")
                },
                onAppleLogin = {
                    navController.navigate("home")
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
                    // Implementar tela de adicionar transporte
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
