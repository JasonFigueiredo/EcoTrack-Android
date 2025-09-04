package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransportScreen(
    onBackClick: () -> Unit = {},
    onSaveTransport: (TransportData) -> Unit = {}
) {
    var selectedTransport by remember { mutableStateOf<TransportType?>(null) }
    var distance by remember { mutableStateOf("") }
    var calculatedCO2 by remember { mutableStateOf(0.0) }
    
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
                    text = "Adicionar Transporte",
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
            // Seleção do tipo de transporte
            Text(
                text = "Selecione o tipo de transporte:",
                color = EcoTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getTransportTypes()) { transportType ->
                    TransportTypeCard(
                        transportType = transportType,
                        isSelected = selectedTransport == transportType,
                        onClick = {
                            selectedTransport = transportType
                            // Recalcular CO2 quando mudar o tipo
                            if (distance.isNotEmpty()) {
                                calculatedCO2 = calculateCO2(transportType, distance.toDoubleOrNull() ?: 0.0)
                            }
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Campo de distância
            if (selectedTransport != null) {
                Text(
                    text = "Distância (km):",
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = distance,
                    onValueChange = { newDistance ->
                        distance = newDistance
                        calculatedCO2 = calculateCO2(selectedTransport!!, newDistance.toDoubleOrNull() ?: 0.0)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Digite a distância em km",
                            color = EcoTextSecondary
                        )
                    },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = EcoTextPrimary,
                        unfocusedTextColor = EcoTextPrimary,
                        focusedBorderColor = EcoGreen,
                        unfocusedBorderColor = EcoTextSecondary,
                        cursorColor = EcoGreen
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Resultado do cálculo
                if (distance.isNotEmpty() && calculatedCO2 > 0) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = EcoDarkSurface
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Emissão de CO₂ calculada:",
                                color = EcoTextSecondary,
                                fontSize = 14.sp
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "${String.format("%.2f", calculatedCO2)} kg CO₂",
                                color = EcoGreen,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Text(
                                text = "Para ${distance} km de ${selectedTransport!!.name}",
                                color = EcoTextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Botão salvar
                    Button(
                        onClick = {
                            val transportData = TransportData(
                                type = selectedTransport!!,
                                distance = distance.toDoubleOrNull() ?: 0.0,
                                co2Emission = calculatedCO2
                            )
                            onSaveTransport(transportData)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EcoGreen,
                            contentColor = EcoTextOnGreen
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Salvar Transporte",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TransportTypeCard(
    transportType: TransportType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) EcoGreen.copy(alpha = 0.2f) else EcoDarkSurface
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
                tint = if (isSelected) EcoGreen else transportType.color,
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
            
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selecionado",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

// Função para calcular CO2 baseado no tipo de transporte e distância
fun calculateCO2(transportType: TransportType, distance: Double): Double {
    return transportType.co2PerKm * distance
}

// Data classes
data class TransportType(
    val name: String,
    val icon: ImageVector,
    val color: Color,
    val co2PerKm: Double // kg CO2 por km
)

data class TransportData(
    val type: TransportType,
    val distance: Double,
    val co2Emission: Double
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
            icon = Icons.Default.DirectionsWalk,
            color = EcoGreen,
            co2PerKm = 0.0 // kg CO2 por km (zero emissões)
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
