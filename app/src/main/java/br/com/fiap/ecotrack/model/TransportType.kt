package br.com.fiap.ecotrack.model

import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.ElectricCar
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.ui.graphics.vector.ImageVector

data class TransportType(
    val id: String,
    val name: String,
    val icon: ImageVector,
    val color: androidx.compose.ui.graphics.Color,
    val co2PerKm: Double, // kg CO2 por km
    val climatiqActivityId: String, // ID para usar na API do Climatiq
    val description: String
)

// Tipos de transporte disponíveis
enum class TransportTypeEnum(val activityId: String, val displayName: String) {
    CAR_GASOLINE("passenger_vehicle-vehicle_type_car-fuel_source_gasoline-distance_na-engine_size_na", "Carro (Gasolina)"),
    CAR_DIESEL("passenger_vehicle-vehicle_type_car-fuel_source_diesel-distance_na-engine_size_na", "Carro (Diesel)"),
    CAR_ELECTRIC("passenger_vehicle-vehicle_type_car-fuel_source_electricity-distance_na-engine_size_na", "Carro (Elétrico)"),
    MOTORCYCLE("passenger_vehicle-vehicle_type_motorcycle-fuel_source_gasoline-distance_na-engine_size_na", "Moto"),
    BUS("passenger_vehicle-vehicle_type_bus-fuel_source_na-distance_na-engine_size_na", "Ônibus"),
    TRAIN("passenger_vehicle-vehicle_type_train-fuel_source_na-distance_na-engine_size_na", "Trem"),
    BICYCLE("passenger_vehicle-vehicle_type_bicycle-fuel_source_na-distance_na-engine_size_na", "Bicicleta")
}

// Função para obter os tipos de transporte disponíveis
// Valores baseados em dados reais da API Climatiq e estudos científicos
fun getAvailableTransportTypes(): List<TransportType> {
    return listOf(
        TransportType(
            id = "car_gasoline",
            name = "Carro (Gasolina)",
            icon = androidx.compose.material.icons.Icons.Default.DirectionsCar,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PerKm = 0.192, // kg CO₂/km - baseado em dados Climatiq
            climatiqActivityId = TransportTypeEnum.CAR_GASOLINE.activityId,
            description = "Veículo particular movido a gasolina"
        ),
        TransportType(
            id = "car_diesel",
            name = "Carro (Diesel)",
            icon = androidx.compose.material.icons.Icons.Default.DirectionsCar,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenLight,
            co2PerKm = 0.171, // kg CO₂/km - baseado em dados Climatiq
            climatiqActivityId = TransportTypeEnum.CAR_DIESEL.activityId,
            description = "Veículo particular movido a diesel"
        ),
        TransportType(
            id = "car_electric",
            name = "Carro (Elétrico)",
            icon = androidx.compose.material.icons.Icons.Default.ElectricCar,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
            co2PerKm = 0.053, // kg CO₂/km - baseado em dados Climatiq (considerando mix energético)
            climatiqActivityId = TransportTypeEnum.CAR_ELECTRIC.activityId,
            description = "Veículo elétrico com baixas emissões"
        ),
        TransportType(
            id = "motorcycle",
            name = "Moto",
            icon = androidx.compose.material.icons.Icons.Default.TwoWheeler,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
            co2PerKm = 0.113, // kg CO₂/km - baseado em dados Climatiq
            climatiqActivityId = TransportTypeEnum.MOTORCYCLE.activityId,
            description = "Motocicleta movida a gasolina"
        ),
        TransportType(
            id = "bus",
            name = "Ônibus",
            icon = androidx.compose.material.icons.Icons.Default.DirectionsBus,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PerKm = 0.089, // kg CO₂/km - baseado em dados Climatiq (por passageiro)
            climatiqActivityId = TransportTypeEnum.BUS.activityId,
            description = "Transporte público coletivo"
        ),
        TransportType(
            id = "train",
            name = "Trem",
            icon = androidx.compose.material.icons.Icons.Default.Train,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenLight,
            co2PerKm = 0.041, // kg CO₂/km - baseado em dados Climatiq (por passageiro)
            climatiqActivityId = TransportTypeEnum.TRAIN.activityId,
            description = "Transporte ferroviário público"
        ),
        TransportType(
            id = "bicycle",
            name = "Bicicleta",
            icon = androidx.compose.material.icons.Icons.Default.PedalBike,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
            co2PerKm = 0.0, // kg CO₂/km - zero emissões diretas
            climatiqActivityId = TransportTypeEnum.BICYCLE.activityId,
            description = "Transporte sustentável e saudável"
        )
    )
}
