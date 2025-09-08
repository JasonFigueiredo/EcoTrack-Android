package br.com.fiap.ecotrack.model

data class EmissionRequest(
    val emission_factor: EmissionFactor,
    val parameters: EmissionParameters
)

data class EmissionFactor(
    val activity_id: String,
    val data_version: String = "0.1.0"
)

data class EmissionParameters(
    val distance: Double,
    val distance_unit: String = "km"
)

data class EmissionResponse(
    val co2e: Double,
    val co2e_unit: String,
    val emission_factor: EmissionFactorResponse
)

data class EmissionFactorResponse(
    val activity_id: String,
    val activity_name: String,
    val category: String,
    val source: String,
    val year: String,
    val region: String,
    val unit: String,
    val unit_type: String
)

data class TransportEmissionResult(
    val transportType: TransportType,
    val distance: Double,
    val co2Emissions: Double,
    val co2Unit: String,
    val emissionFactor: EmissionFactorResponse
)

data class EmissionComparison(
    val selectedTransport: TransportEmissionResult,
    val alternatives: List<TransportEmissionResult>,
    val savings: List<EmissionSaving>
)

data class EmissionSaving(
    val transportType: TransportType,
    val co2Saved: Double,
    val percentageSaved: Double,
    val benefits: List<String>,
    val drawbacks: List<String>
)
