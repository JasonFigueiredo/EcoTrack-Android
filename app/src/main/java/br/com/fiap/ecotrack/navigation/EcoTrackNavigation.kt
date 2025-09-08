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
import br.com.fiap.ecotrack.model.DadosEnergia.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import br.com.fiap.ecotrack.ui.theme.*

@Composable
fun EcoTrackNavigation(navController: NavHostController) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "intro"
    ) {
        composable("intro") {
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
                },
                onCalculadoraEnergia = {
                    navController.navigate("calculadora_energia")
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

        composable("calculadora_energia") {
            TelaCalculadoraEnergia(
                onVoltarClick = {
                    navController.popBackStack()
                },
                onMostrarResultados = { comparacaoEnergia ->
                    navController.navigate("resultados_energia")
                }
            )
        }

        composable("resultados_energia") {
            val comparacaoEnergia = br.com.fiap.ecotrack.model.EstadoEnergia.comparacaoAtual
                ?: criarComparacaoEnergiaMock() // Fallback para dados mockados
            
            TelaResultadosEnergia(
                comparacaoEnergia = comparacaoEnergia,
                onVoltarClick = {
                    navController.popBackStack()
                },
                onCalcularNovo = {
                    br.com.fiap.ecotrack.model.EstadoEnergia.limparComparacaoEnergia()
                    navController.navigate("calculadora_energia") {
                        popUpTo("calculadora_energia") { inclusive = true }
                    }
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
                },
                onFoodEmissionCalculator = {
                    navController.navigate("food_emission_calculator")
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

        composable("food_emission_calculator") {
            FoodEmissionCalculatorScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onShowResults = { foodEmissionComparison ->
                    // Navegar para a tela de resultados com os dados
                    navController.navigate("food_emission_results") {
                        // Passar os dados via argumentos ou estado
                    }
                }
            )
        }

        composable("food_emission_results") {
            // Usar dados reais do estado global
            val foodEmissionComparison = br.com.fiap.ecotrack.model.FoodEmissionState.getFoodEmissionComparison()
                ?: createMockFoodEmissionComparison() // Fallback para dados mockados se não houver dados reais
            
            FoodEmissionResultsScreen(
                foodEmissionComparison = foodEmissionComparison,
                onBackClick = {
                    navController.popBackStack()
                },
                onCalculateNew = {
                    // Limpar estado anterior
                    br.com.fiap.ecotrack.model.FoodEmissionState.clearFoodEmissionComparison()
                    navController.navigate("food_emission_calculator") {
                        popUpTo("food_emission_calculator") { inclusive = true }
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
                onOpenHistorico = {
                    navController.navigate("add_historico")
                }
            )
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

private fun criarComparacaoEnergiaMock(): ComparacaoEmissaoEnergia {
    val tipoEnergiaMock = TipoEnergia(
        id = "ar_condicionado",
        nome = "Ar Condicionado",
        icone = Icons.Default.AcUnit,
        cor = br.com.fiap.ecotrack.ui.theme.EcoGreen,
        co2PorKwh = 0.118,
        climatiqActivityId = "electricity-energy_source_grid-electricity_type_na-country_BR",
        descricao = "Sistema de climatização residencial",
        potenciaMedia = 2000.0
    )
    
    return ComparacaoEmissaoEnergia(
        energiaSelecionada = ResultadoEmissaoEnergia(
            tipoEnergia = tipoEnergiaMock,
            consumoKwh = 4.8,
            horasUso = 2.0,
            periodo = PeriodoTempo.DIA,
            co2Emissoes = 0.566,
            co2Unidade = "kg",
            fatorEmissao = FatorEmissaoEnergiaResposta(
                activity_id = "electricity-energy_source_grid-electricity_type_na-country_BR",
                activity_name = "Ar Condicionado",
                category = "electricity",
                source = "mock_data",
                year = "2024",
                region = "BR",
                unit = "kWh",
                unit_type = "energy"
            )
        ),
        alternativas = emptyList(),
        economias = listOf(
            EconomiaEnergia(
                tipoEnergia = TipoEnergia(
                    id = "energia_solar",
                    nome = "Energia Solar",
                    icone = Icons.Default.WbSunny,
                    cor = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
                    co2PorKwh = 0.0,
                    climatiqActivityId = "electricity-energy_source_solar-electricity_type_na-country_BR",
                    descricao = "Energia solar fotovoltaica",
                    potenciaMedia = 0.0
                ),
                co2Economizado = 0.566,
                percentualEconomizado = 100.0,
                beneficios = listOf("Zero emissões", "Energia renovável", "Economia na conta"),
                desvantagens = listOf("Custo inicial alto", "Depende do sol")
            )
        )
    )
}

private fun createMockFoodEmissionComparison(): FoodEmissionComparison {
    return FoodEmissionComparison(
        selectedFood = FoodEmissionResult(
            foodType = br.com.fiap.ecotrack.model.FoodType(
                id = "grapes",
                name = "Uvas",
                icon = Icons.Default.Circle,
                color = EcoGreenAccent,
                co2PerKg = 0.5,
                caloriesPerKg = 620.0,
                proteinPerKg = 6.0,
                carbsPerKg = 160.0,
                fatPerKg = 1.0,
                climatiqActivityId = "food-grapes",
                description = "Uvas - fruta versátil e nutritiva",
                category = br.com.fiap.ecotrack.model.FoodCategory.FRUITS
            ),
            weight = 0.5,
            period = br.com.fiap.ecotrack.model.ConsumptionPeriod.DAILY,
            co2Emissions = 0.25,
            co2Unit = "kg",
            emissionFactor = br.com.fiap.ecotrack.model.EmissionFactorResponse(
                activity_id = "food-grapes",
                activity_name = "Uvas",
                category = "Food",
                source = "Local",
                year = "2024",
                region = "BR",
                unit = "kg",
                unit_type = "mass"
            )
        ),
        alternatives = emptyList(),
        savings = listOf(
            br.com.fiap.ecotrack.model.FoodEmissionSaving(
                foodType = br.com.fiap.ecotrack.model.FoodType(
                    id = "oranges",
                    name = "Laranjas",
                    icon = Icons.Default.Circle,
                    color = EcoGreenAccent,
                    co2PerKg = 0.3,
                    caloriesPerKg = 470.0,
                    proteinPerKg = 9.0,
                    carbsPerKg = 118.0,
                    fatPerKg = 1.0,
                    climatiqActivityId = "food-oranges",
                    description = "Laranjas - fruta cítrica rica em vitamina C",
                    category = br.com.fiap.ecotrack.model.FoodCategory.FRUITS
                ),
                co2Saved = 0.1,
                percentageSaved = 40.0,
                benefits = listOf("Baixo impacto ambiental", "Rica em vitamina C", "Antioxidantes", "Refrescante"),
                drawbacks = listOf("Pode ser mais caro", "Estacionalidade", "Pode ser ácida")
            )
        )
    )
}
