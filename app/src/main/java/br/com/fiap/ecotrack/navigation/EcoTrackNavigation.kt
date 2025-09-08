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
import br.com.fiap.ecotrack.model.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

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
                },
                onEmissionCalculator = {
                    navController.navigate("emission_calculator")
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

        composable("emission_calculator") {
            EmissionCalculatorScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onShowResults = { emissionComparison ->
                    // Navegar para a tela de resultados com os dados
                    navController.navigate("emission_results") {
                        // Passar os dados via argumentos ou estado
                    }
                }
            )
        }

        composable("emission_results") {
            // Usar dados reais do estado global
            val emissionComparison = br.com.fiap.ecotrack.model.EmissionState.currentEmissionComparison
                ?: createMockEmissionComparison() // Fallback para dados mockados se não houver dados reais
            
            EmissionResultsScreen(
                emissionComparison = emissionComparison,
                onBackClick = {
                    navController.popBackStack()
                },
                onCalculateNew = {
                    // Limpar estado anterior
                    br.com.fiap.ecotrack.model.EmissionState.clearEmissionComparison()
                    navController.navigate("emission_calculator") {
                        popUpTo("emission_calculator") { inclusive = true }
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

private fun createMockEmissionComparison(): EmissionComparison {
    return EmissionComparison(
        selectedTransport = TransportEmissionResult(
            transportType = TransportType(
                id = "car_gasoline",
                name = "Carro (Gasolina)",
                icon = Icons.Default.DirectionsCar,
                color = br.com.fiap.ecotrack.ui.theme.EcoGreen,
                co2PerKm = 0.192,
                climatiqActivityId = "passenger_vehicle-vehicle_type_car-fuel_source_gasoline",
                description = "Veículo particular movido a gasolina"
            ),
            distance = 20.0,
            co2Emissions = 3.84,
            co2Unit = "kg",
            emissionFactor = EmissionFactorResponse(
                activity_id = "passenger_vehicle-vehicle_type_car-fuel_source_gasoline",
                activity_name = "Carro (Gasolina)",
                category = "Transport",
                source = "Climatiq",
                year = "2024",
                region = "BR",
                unit = "kg",
                unit_type = "mass"
            )
        ),
        alternatives = emptyList(),
        savings = listOf(
            EmissionSaving(
                transportType = TransportType(
                    id = "bicycle",
                    name = "Bicicleta",
                    icon = Icons.Default.PedalBike,
                    color = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
                    co2PerKm = 0.0,
                    climatiqActivityId = "passenger_vehicle-vehicle_type_bicycle",
                    description = "Transporte sustentável e saudável"
                ),
                co2Saved = 3.84,
                percentageSaved = 100.0,
                benefits = listOf("Zero emissões", "Exercício físico", "Economia"),
                drawbacks = listOf("Depende do clima", "Pode ser mais lento")
            )
        )
    )
}
