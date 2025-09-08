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
        startDestination = "intro"
    ) {
        composable("Intro") {
            IntroScreen(
                onContinue = {
                    navController.navigate("welcome")
                }
            )
        }

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
                onSaveTransport = {
                    // Apenas navega de volta para a tela de transporte sem parâmetros
                    navController.navigate("transport") {
                        popUpTo("transport") { inclusive = true }
                    }
                }
            )
        }

        composable("energy") {
            EnergyScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onAddEnergy = {
                    navController.navigate("add_energy")
                }
            )
        }

        composable("add_energy") {
            AddEnergyScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("food") {
            FoodScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onAddFood = {
                    navController.navigate("add_food")
                }
            )
        }

        composable("add_food") {
            AddFoodScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveFood = {
                    // Apenas navega de volta para a tela de alimentação sem parâmetros
                    navController.navigate("food") {
                        popUpTo("food") { inclusive = true }
                    }
                }
            )
        }
        
        composable("profile") {
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onOpenGoals = {
                    navController.navigate("add_meta")
                },
                onOpenConquistas = {
                    navController.navigate("add_conquista")
                },
                onOpenAjuda = {
                    navController.navigate("add_ajuda")
                },
                onOpenSobre = {
                    navController.navigate("add_sobre")
                },
            ) {
                navController.navigate("add_historico")
            }
        }

        composable("add_meta") {
            AddMetaScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("add_conquista") {
            AddConquistaScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("add_ajuda") {
            AddAjudaScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("add_sobre") {
            AddSobreScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("add_historico") {
            AddHistoricoScreen(
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
