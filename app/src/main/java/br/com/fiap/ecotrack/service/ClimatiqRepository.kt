package br.com.fiap.ecotrack.service

import br.com.fiap.ecotrack.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClimatiqRepository {
    private val apiKey = "EWN9THXE5X3TNBG9ZD5KJ0VM0R"
    private val baseUrl = "https://beta3.api.climatiq.io/"
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private val apiService = retrofit.create(ClimatiqApiService::class.java)

    suspend fun calculateEmissions(
        transportType: TransportType,
        distance: Double
    ): Result<TransportEmissionResult> = withContext(Dispatchers.IO) {
        try {
            val request = EmissionRequest(
                emission_factor = EmissionFactor(
                    activity_id = transportType.climatiqActivityId
                ),
                parameters = EmissionParameters(distance = distance)
            )

            val response = apiService.estimateEmissions(
                authHeader = "Bearer $apiKey",
                request = request
            )

            if (response.isSuccessful) {
                val emissionResponse = response.body()!!
                val result = TransportEmissionResult(
                    transportType = transportType,
                    distance = distance,
                    co2Emissions = emissionResponse.co2e,
                    co2Unit = emissionResponse.co2e_unit,
                    emissionFactor = emissionResponse.emission_factor
                )
                Result.success(result)
            } else {
                Result.failure(Exception("Erro na API: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun createEmissionComparison(
        selectedResult: TransportEmissionResult,
        alternativeResults: List<TransportEmissionResult>
    ): EmissionComparison {
        val savings = alternativeResults.map { alternative ->
            val co2Saved = selectedResult.co2Emissions - alternative.co2Emissions
            val percentageSaved = if (selectedResult.co2Emissions > 0) {
                (co2Saved / selectedResult.co2Emissions) * 100
            } else 0.0
            
            EmissionSaving(
                transportType = alternative.transportType,
                co2Saved = co2Saved,
                percentageSaved = percentageSaved,
                benefits = getTransportBenefits(alternative.transportType),
                drawbacks = getTransportDrawbacks(alternative.transportType)
            )
        }.filter { it.co2Saved > 0 }
        
        return EmissionComparison(
            selectedTransport = selectedResult,
            alternatives = alternativeResults,
            savings = savings
        )
    }
    
    private fun getTransportBenefits(transportType: TransportType): List<String> {
        return when (transportType.id) {
            "bicycle" -> listOf(
                "Zero emissões de CO₂",
                "Exercício físico",
                "Economia de combustível",
                "Não gera poluição sonora"
            )
            "train" -> listOf(
                "Baixas emissões por passageiro",
                "Transporte eficiente",
                "Menos congestionamento",
                "Confortável para longas distâncias"
            )
            "bus" -> listOf(
                "Emissões baixas por passageiro",
                "Transporte público eficiente",
                "Reduz congestionamento",
                "Mais acessível economicamente"
            )
            "car_electric" -> listOf(
                "Emissões muito baixas",
                "Tecnologia limpa",
                "Silencioso",
                "Menor manutenção"
            )
            else -> listOf("Transporte alternativo disponível")
        }
    }
    
    private fun getTransportDrawbacks(transportType: TransportType): List<String> {
        return when (transportType.id) {
            "bicycle" -> listOf(
                "Depende do clima",
                "Pode ser mais lento",
                "Requer esforço físico",
                "Limitado por distância"
            )
            "train" -> listOf(
                "Horários fixos",
                "Pode não ter cobertura total",
                "Depende de infraestrutura",
                "Pode ser mais lento para curtas distâncias"
            )
            "bus" -> listOf(
                "Horários fixos",
                "Pode ser mais lento",
                "Depende de rotas disponíveis",
                "Menos conforto"
            )
            "car_electric" -> listOf(
                "Custo inicial alto",
                "Depende de pontos de recarga",
                "Autonomia limitada",
                "Tecnologia ainda em desenvolvimento"
            )
            else -> listOf("Algumas limitações podem existir")
        }
    }
}
