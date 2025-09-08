package br.com.fiap.ecotrack.service

import br.com.fiap.ecotrack.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson


class FoodClimatiqRepository {
    
    private val gson = Gson()
    private val apiKey = "YOUR_CLIMATIQ_API_KEY" // Substitua pela sua chave da API
    
    suspend fun calculateFoodEmissions(
        foodType: FoodType,
        weight: Double,
        period: ConsumptionPeriod
    ): Result<FoodEmissionResult> = withContext(Dispatchers.IO) {
        try {
            // Calcular peso total baseado no período
            val totalWeight = weight * period.multiplier
            
            // Tentar usar a API Climatiq primeiro
            val apiResult = try {
                calculateWithClimatiqAPI(foodType, totalWeight)
            } catch (e: Exception) {
                null
            }
            
            // Se a API falhar, usar cálculo local
            val result = apiResult ?: calculateLocally(foodType, totalWeight, period)
            
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private suspend fun calculateWithClimatiqAPI(
        foodType: FoodType,
        weight: Double
    ): FoodEmissionResult? {
        val url = URL("https://beta3.api.climatiq.io/estimate")
        val connection = url.openConnection() as HttpURLConnection
        
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Authorization", "Bearer $apiKey")
        connection.doOutput = true
        
        val request = FoodEmissionRequest(
            emission_factor = FoodEmissionFactor(
                activity_id = foodType.climatiqActivityId
            ),
            parameters = FoodEmissionParameters(
                weight = weight
            )
        )
        
        val jsonInput = gson.toJson(request)
        connection.outputStream.use { it.write(jsonInput.toByteArray()) }
        
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val emissionResponse = gson.fromJson(response, FoodEmissionResponse::class.java)
            
            return FoodEmissionResult(
                foodType = foodType,
                weight = weight,
                period = ConsumptionPeriod.DAILY, // Será ajustado pelo chamador
                co2Emissions = emissionResponse.co2e,
                co2Unit = emissionResponse.co2e_unit,
                emissionFactor = emissionResponse.emission_factor
            )
        }
        
        return null
    }
    
    private fun calculateLocally(
        foodType: FoodType,
        weight: Double,
        period: ConsumptionPeriod
    ): FoodEmissionResult {
        val co2Emissions = foodType.co2PerKg * weight
        
        return FoodEmissionResult(
            foodType = foodType,
            weight = weight,
            period = period,
            co2Emissions = co2Emissions,
            co2Unit = "kg",
            emissionFactor = EmissionFactorResponse(
                activity_id = foodType.climatiqActivityId,
                activity_name = foodType.name,
                category = "Food",
                source = "Local",
                year = "2024",
                region = "BR",
                unit = "kg",
                unit_type = "mass"
            )
        )
    }
    
    fun createFoodEmissionComparison(
        selectedFood: FoodEmissionResult,
        alternativeFoods: List<FoodEmissionResult>
    ): FoodEmissionComparison {
        val savings = alternativeFoods.map { alternative ->
            val co2Saved = selectedFood.co2Emissions - alternative.co2Emissions
            val percentageSaved = if (selectedFood.co2Emissions > 0) {
                (co2Saved / selectedFood.co2Emissions) * 100
            } else 0.0
            
            FoodEmissionSaving(
                foodType = alternative.foodType,
                co2Saved = co2Saved,
                percentageSaved = percentageSaved,
                benefits = getFoodBenefits(alternative.foodType),
                drawbacks = getFoodDrawbacks(alternative.foodType)
            )
        }.filter { it.co2Saved > 0 } // Apenas alternativas que economizam CO2
        
        return FoodEmissionComparison(
            selectedFood = selectedFood,
            alternatives = alternativeFoods,
            savings = savings
        )
    }
    
    private fun getFoodBenefits(foodType: FoodType): List<String> {
        return when (foodType.category) {
            FoodCategory.VEGETABLES -> listOf(
                "Baixo impacto ambiental",
                "Rico em vitaminas e minerais",
                "Fonte de fibras"
            )
            FoodCategory.FRUITS -> when (foodType.id) {
                "grapes" -> listOf(
                    "Baixo impacto ambiental",
                    "Antioxidantes",
                    "Rica em resveratrol",
                    "Versátil na culinária"
                )
                "oranges" -> listOf(
                    "Baixo impacto ambiental",
                    "Rica em vitamina C",
                    "Antioxidantes",
                    "Refrescante"
                )
                else -> listOf(
                    "Baixo impacto ambiental",
                    "Rico em vitaminas",
                    "Antioxidantes naturais"
                )
            }
            FoodCategory.GRAINS -> listOf(
                "Baixo impacto ambiental",
                "Fonte de energia",
                "Rico em fibras"
            )
            FoodCategory.MEAT -> when (foodType.id) {
                "fish" -> listOf(
                    "Menor impacto que outras carnes",
                    "Rico em ômega-3",
                    "Proteína de alta qualidade"
                )
                "chicken" -> listOf(
                    "Menor impacto que carne bovina",
                    "Proteína magra",
                    "Versátil na culinária"
                )
                else -> listOf("Proteína de alta qualidade")
            }
            FoodCategory.DAIRY -> listOf(
                "Fonte de cálcio",
                "Proteína completa",
                "Versátil na alimentação"
            )
            FoodCategory.BEVERAGES -> when (foodType.id) {
                "tea" -> listOf(
                    "Baixo impacto ambiental",
                    "Antioxidantes",
                    "Hidratação"
                )
                else -> listOf("Hidratação")
            }
            FoodCategory.SNACKS -> when (foodType.id) {
                "nuts" -> listOf(
                    "Baixo impacto ambiental",
                    "Gorduras saudáveis",
                    "Proteína vegetal"
                )
                else -> listOf("Satisfaz o paladar")
            }
        }
    }
    
    private fun getFoodDrawbacks(foodType: FoodType): List<String> {
        return when (foodType.category) {
            FoodCategory.VEGETABLES -> listOf(
                "Pode ser mais caro",
                "Estacionalidade"
            )
            FoodCategory.FRUITS -> when (foodType.id) {
                "grapes" -> listOf(
                    "Pode ser mais caro",
                    "Estacionalidade",
                    "Perecível"
                )
                "oranges" -> listOf(
                    "Pode ser mais caro",
                    "Estacionalidade",
                    "Pode ser ácida"
                )
                else -> listOf(
                    "Pode ser mais caro",
                    "Estacionalidade",
                    "Perecível"
                )
            }
            FoodCategory.GRAINS -> listOf(
                "Pode causar alergias",
                "Necessita preparo"
            )
            FoodCategory.MEAT -> when (foodType.id) {
                "fish" -> listOf(
                    "Pode ser mais caro",
                        "Risco de contaminação",
                        "Estacionalidade"
                )
                "chicken" -> listOf(
                    "Ainda tem impacto ambiental",
                    "Necessita refrigeração"
                )
                else -> listOf("Alto impacto ambiental")
            }
            FoodCategory.DAIRY -> listOf(
                "Pode causar intolerância",
                "Necessita refrigeração"
            )
            FoodCategory.BEVERAGES -> listOf(
                "Pode conter cafeína",
                "Pode ser diurético"
            )
            FoodCategory.SNACKS -> listOf(
                "Pode ser calórico",
                "Pode conter açúcar"
            )
        }
    }
}
