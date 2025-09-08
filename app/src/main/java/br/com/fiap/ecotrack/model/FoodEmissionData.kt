package br.com.fiap.ecotrack.model

data class FoodEmissionRequest(
    val emission_factor: FoodEmissionFactor,
    val parameters: FoodEmissionParameters
)

data class FoodEmissionFactor(
    val activity_id: String,
    val data_version: String = "0.1.0"
)

data class FoodEmissionParameters(
    val weight: Double,
    val weight_unit: String = "kg"
)

data class FoodEmissionResponse(
    val co2e: Double,
    val co2e_unit: String,
    val emission_factor: EmissionFactorResponse
)

data class FoodEmissionResult(
    val foodType: FoodType,
    val weight: Double,
    val period: ConsumptionPeriod,
    val co2Emissions: Double,
    val co2Unit: String,
    val emissionFactor: EmissionFactorResponse
)

data class FoodEmissionComparison(
    val selectedFood: FoodEmissionResult,
    val alternatives: List<FoodEmissionResult>,
    val savings: List<FoodEmissionSaving>
)

data class FoodEmissionSaving(
    val foodType: FoodType,
    val co2Saved: Double,
    val percentageSaved: Double,
    val benefits: List<String>,
    val drawbacks: List<String>
)

// Estado global para comparações de alimentos
object FoodEmissionState {
    private var _foodEmissionComparison: FoodEmissionComparison? = null
    
    fun setFoodEmissionComparison(comparison: FoodEmissionComparison) {
        _foodEmissionComparison = comparison
    }
    
    fun getFoodEmissionComparison(): FoodEmissionComparison? {
        return _foodEmissionComparison
    }
    
    fun clearFoodEmissionComparison() {
        _foodEmissionComparison = null
    }
}
