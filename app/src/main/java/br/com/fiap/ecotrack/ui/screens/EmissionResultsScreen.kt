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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.model.EmissionComparison
import br.com.fiap.ecotrack.model.EmissionSaving
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmissionResultsScreen(
    emissionComparison: EmissionComparison,
    onBackClick: () -> Unit = {},
    onCalculateNew: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Resultados das Emissões",
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
            actions = {
                IconButton(onClick = onCalculateNew) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Novo Cálculo",
                        tint = EcoGreen
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = EcoDark
            )
        )
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Resultado principal
            item {
                MainEmissionResultCard(emissionComparison.selectedTransport)
            }
            
            // Comparações e sugestões
            if (emissionComparison.savings.isNotEmpty()) {
                item {
                    Text(
                        text = "Alternativas Mais Sustentáveis",
                        color = EcoTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                items(emissionComparison.savings) { saving ->
                    AlternativeTransportCard(saving)
                }
            }
            
            // Dicas de sustentabilidade
            item {
                SustainabilityTipsCard()
            }
        }
    }
}

@Composable
fun MainEmissionResultCard(selectedTransport: br.com.fiap.ecotrack.model.TransportEmissionResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ícone do transporte
            Icon(
                imageVector = selectedTransport.transportType.icon,
                contentDescription = selectedTransport.transportType.name,
                tint = selectedTransport.transportType.color,
                modifier = Modifier.size(48.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = selectedTransport.transportType.name,
                color = EcoTextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Distância: ${selectedTransport.distance} km",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Emissões de CO2
            Text(
                text = "Emissões de CO₂",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${String.format("%.2f", selectedTransport.co2Emissions)} ${selectedTransport.co2Unit}",
                color = EcoGreen,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Barra de impacto
            val impactLevel = when {
                selectedTransport.co2Emissions < 1.0 -> "Baixo"
                selectedTransport.co2Emissions < 5.0 -> "Médio"
                else -> "Alto"
            }
            
            val impactColor = when (impactLevel) {
                "Baixo" -> EcoGreen
                "Médio" -> EcoWarning
                else -> EcoError
            }
            
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = impactColor.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when (impactLevel) {
                            "Baixo" -> Icons.Default.CheckCircle
                            "Médio" -> Icons.Default.Warning
                            else -> Icons.Default.Error
                        },
                        contentDescription = "Impacto",
                        tint = impactColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Impacto: $impactLevel",
                        color = impactColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun AlternativeTransportCard(saving: EmissionSaving) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = saving.transportType.icon,
                    contentDescription = saving.transportType.name,
                    tint = saving.transportType.color,
                    modifier = Modifier.size(32.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = saving.transportType.name,
                        color = EcoTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Economia: ${String.format("%.2f", saving.co2Saved)} kg CO₂ (${String.format("%.0f", saving.percentageSaved)}%)",
                        color = EcoGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Benefícios
            Text(
                text = "Benefícios:",
                color = EcoTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            saving.benefits.forEach { benefit ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Benefício",
                        tint = EcoGreen,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = benefit,
                        color = EcoTextSecondary,
                        fontSize = 11.sp
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
            }
            
            // Desvantagens (se houver)
            if (saving.drawbacks.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Considerações:",
                    color = EcoTextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                saving.drawbacks.take(2).forEach { drawback ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Consideração",
                            tint = EcoWarning,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = drawback,
                            color = EcoTextSecondary,
                            fontSize = 11.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

@Composable
fun SustainabilityTipsCard() {
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = "Dicas",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Dicas de Sustentabilidade",
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val tips = listOf(
                "Combine diferentes meios de transporte para otimizar suas viagens",
                "Prefira transporte público para trajetos longos",
                "Use bicicleta ou caminhada para distâncias curtas",
                "Considere carona compartilhada para reduzir emissões",
                "Planeje suas rotas para evitar congestionamentos"
            )
            
            tips.forEach { tip ->
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = "Dica",
                        tint = EcoGreenAccent,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = tip,
                        color = EcoTextSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmissionResultsScreenPreview() {
    EcoTrackTheme {
        // Preview com dados mockados
        val mockComparison = createMockEmissionComparison()
        EmissionResultsScreen(emissionComparison = mockComparison)
    }
}

private fun createMockEmissionComparison(): EmissionComparison {
    // Esta função será removida quando a integração real estiver pronta
    return EmissionComparison(
        selectedTransport = br.com.fiap.ecotrack.model.TransportEmissionResult(
            transportType = br.com.fiap.ecotrack.model.TransportType(
                id = "car_gasoline",
                name = "Carro (Gasolina)",
                icon = Icons.Default.DirectionsCar,
                color = EcoGreen,
                co2PerKm = 0.192,
                climatiqActivityId = "passenger_vehicle-vehicle_type_car-fuel_source_gasoline",
                description = "Veículo particular movido a gasolina"
            ),
            distance = 20.0,
            co2Emissions = 3.84,
            co2Unit = "kg",
            emissionFactor = br.com.fiap.ecotrack.model.EmissionFactorResponse(
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
                transportType = br.com.fiap.ecotrack.model.TransportType(
                    id = "bicycle",
                    name = "Bicicleta",
                    icon = Icons.Default.PedalBike,
                    color = EcoGreenAccent,
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
