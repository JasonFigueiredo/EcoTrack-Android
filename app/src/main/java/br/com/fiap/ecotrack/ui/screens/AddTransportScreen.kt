package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransportScreen(
    onBackClick: () -> Unit = {},
    onSaveTransport: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
            .padding(bottom = 30.dp)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Consumo por Transporte",
                    color = EcoTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = EcoTextPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = EcoDark
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Título da seção
            Text(
                text = "Tipos de Transporte",
                color = EcoTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(getTransportTypes()) { transportType ->
                    TransportTypeCard(transportType = transportType)
                }
            }

        }
    }
}

@Composable
fun TransportTypeCard(
    transportType: TransportType
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = transportType.icon,
                contentDescription = transportType.name,
                tint = transportType.color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transportType.name,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${transportType.co2PerKm} kg CO₂/km",
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

// Data class
data class TransportType(
    val name: String,
    val icon: ImageVector,
    val color: Color,
    val co2PerKm: Double // kg CO2 por km
)

fun getTransportTypes(): List<TransportType> {
    return listOf(
        TransportType(
            name = "Carro (Gasolina)",
            icon = Icons.Default.DirectionsCar,
            color = EcoGreen,
            co2PerKm = 0.192 // kg CO2 por km
        ),
        TransportType(
            name = "Carro (Diesel)",
            icon = Icons.Default.DirectionsCar,
            color = EcoGreenLight,
            co2PerKm = 0.171 // kg CO2 por km
        ),
        TransportType(
            name = "Carro (Elétrico)",
            icon = Icons.Default.ElectricCar,
            color = EcoGreenAccent,
            co2PerKm = 0.053 // kg CO2 por km
        ),
        TransportType(
            name = "Ônibus",
            icon = Icons.Default.DirectionsBus,
            color = EcoGreen,
            co2PerKm = 0.089 // kg CO2 por km
        ),
        TransportType(
            name = "Metrô/Trem",
            icon = Icons.Default.Train,
            color = EcoGreenLight,
            co2PerKm = 0.041 // kg CO2 por km
        ),
        TransportType(
            name = "Bicicleta",
            icon = Icons.Default.PedalBike,
            color = EcoGreenAccent,
            co2PerKm = 0.0 // kg CO2 por km (zero emissões)
        ),
        TransportType(
            name = "Caminhada",
            color = EcoGreen,
            co2PerKm = 0.0, // kg CO2 por km (zero emissões)
            icon = Icons.AutoMirrored.Filled.DirectionsWalk
        ),
        TransportType(
            name = "Avião",
            icon = Icons.Default.Flight,
            color = EcoGreenLight,
            co2PerKm = 0.285 // kg CO2 por km
        ),
        TransportType(
            name = "Moto",
            icon = Icons.Default.TwoWheeler,
            color = EcoGreenAccent,
            co2PerKm = 0.113 // kg CO2 por km
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AddTransportScreenPreview() {
    EcoTrackTheme {
        AddTransportScreen()
    }
}
