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
import br.com.fiap.ecotrack.model.*
import br.com.fiap.ecotrack.service.ClimatiqRepository
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmissionCalculatorScreen(
    onBackClick: () -> Unit = {},
    onShowResults: (EmissionComparison) -> Unit = {}
) {
    var selectedTransport by remember { mutableStateOf<TransportType?>(null) }
    var distance by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var shouldCalculate by remember { mutableStateOf(false) }
    
    val climatiqRepository = remember { ClimatiqRepository() }
    val availableTransports = remember { getAvailableTransportTypes() }
    
    // LaunchedEffect para calcular emissões quando shouldCalculate for true
    LaunchedEffect(shouldCalculate) {
        if (shouldCalculate && selectedTransport != null && distance.isNotBlank()) {
            val distanceValue = distance.toDoubleOrNull()
            if (distanceValue != null && distanceValue > 0) {
                try {
                    // Primeiro, calcular emissões do transporte selecionado
                    val selectedResult = climatiqRepository.calculateEmissions(
                        selectedTransport!!,
                        distanceValue
                    ).getOrElse { 
                        // Se falhar, usar cálculo local
                        TransportEmissionResult(
                            transportType = selectedTransport!!,
                            distance = distanceValue,
                            co2Emissions = selectedTransport!!.co2PerKm * distanceValue,
                            co2Unit = "kg",
                            emissionFactor = EmissionFactorResponse(
                                activity_id = selectedTransport!!.climatiqActivityId,
                                activity_name = selectedTransport!!.name,
                                category = "Transport",
                                source = "Local",
                                year = "2024",
                                region = "BR",
                                unit = "kg",
                                unit_type = "mass"
                            )
                        )
                    }
                    
                    // Calcular emissões das alternativas
                    val alternativeResults = availableTransports
                        .filter { it.id != selectedTransport!!.id }
                        .map { transport ->
                            climatiqRepository.calculateEmissions(transport, distanceValue)
                                .getOrElse {
                                    TransportEmissionResult(
                                        transportType = transport,
                                        distance = distanceValue,
                                        co2Emissions = transport.co2PerKm * distanceValue,
                                        co2Unit = "kg",
                                        emissionFactor = EmissionFactorResponse(
                                            activity_id = transport.climatiqActivityId,
                                            activity_name = transport.name,
                                            category = "Transport",
                                            source = "Local",
                                            year = "2024",
                                            region = "BR",
                                            unit = "kg",
                                            unit_type = "mass"
                                        )
                                    )
                                }
                        }
                    
                    // Criar comparação
                    val comparison = climatiqRepository.createEmissionComparison(
                        selectedResult,
                        alternativeResults
                    )
                    
                    // Salvar no estado global
                    EmissionState.setEmissionComparison(comparison)
                    
                    isLoading = false
                    shouldCalculate = false
                    onShowResults(comparison)
                } catch (e: Exception) {
                    isLoading = false
                    shouldCalculate = false
                    errorMessage = "Erro ao calcular emissões: ${e.message}"
                }
            } else {
                isLoading = false
                shouldCalculate = false
                errorMessage = "Por favor, insira uma distância válida"
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Calculadora de Emissões",
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
            // Título
            Text(
                text = "Calcule suas emissões de CO₂",
                color = EcoTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Selecione o tipo de transporte e a distância percorrida",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Campo de distância
            OutlinedTextField(
                value = distance,
                onValueChange = { distance = it },
                label = { Text("Distância (km)", color = EcoTextSecondary) },
                placeholder = { Text("Ex: 20", color = EcoTextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = EcoTextPrimary,
                    unfocusedTextColor = EcoTextPrimary,
                    focusedBorderColor = EcoGreen,
                    unfocusedBorderColor = EcoTextSecondary,
                    focusedLabelColor = EcoGreen,
                    unfocusedLabelColor = EcoTextSecondary
                ),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                )
            )
            
            // Debug: mostrar distância digitada
            if (distance.isNotBlank()) {
                val distanceValue = distance.toDoubleOrNull()
                if (distanceValue != null && distanceValue > 0) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = EcoGreen.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Route,
                                contentDescription = "Distância",
                                tint = EcoGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Distância: $distanceValue km",
                                color = EcoGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Seleção de transporte
            Text(
                text = "Tipo de Transporte",
                color = EcoTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            
            // Debug: mostrar seleção atual
            selectedTransport?.let { transport ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = EcoGreen.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Selecionado",
                            tint = EcoGreen,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Selecionado: ${transport.name}",
                            color = EcoGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(availableTransports) { transport ->
                    TransportSelectionCard(
                        transport = transport,
                        isSelected = selectedTransport?.id == transport.id,
                        onClick = { selectedTransport = transport }
                    )
                }
            }
            
            // Mensagem de erro
            errorMessage?.let { error ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = EcoError.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Erro",
                            tint = EcoError,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = error,
                            color = EcoError,
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Indicador de status
            if (selectedTransport != null && distance.isNotBlank()) {
                val distanceValue = distance.toDoubleOrNull()
                if (distanceValue != null && distanceValue > 0) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = EcoGreen.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Pronto",
                                tint = EcoGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Pronto para calcular! ${selectedTransport!!.name} - ${distanceValue}km",
                                color = EcoGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            
            // Botão de calcular
            Button(
                onClick = {
                    if (selectedTransport != null && distance.isNotBlank()) {
                        val distanceValue = distance.toDoubleOrNull()
                        if (distanceValue != null && distanceValue > 0) {
                            isLoading = true
                            errorMessage = null
                            shouldCalculate = true
                        } else {
                            errorMessage = "Por favor, insira uma distância válida"
                        }
                    } else {
                        errorMessage = "Por favor, selecione um transporte e insira a distância"
                    }
                },
                enabled = !isLoading && selectedTransport != null && distance.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
                .padding(bottom = 25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EcoGreen,
                    disabledContainerColor = EcoTextSecondary
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = EcoTextOnGreen,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = if (isLoading) "Calculando..." else "Calcular Emissões",
                    color = if (isLoading) EcoTextSecondary else EcoTextOnGreen,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun TransportSelectionCard(
    transport: TransportType,
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
                imageVector = transport.icon,
                contentDescription = transport.name,
                tint = if (isSelected) EcoGreen else transport.color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transport.name,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = transport.description,
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = "${String.format("%.3f", transport.co2PerKm)} kg CO₂/km",
                    color = if (isSelected) EcoGreen else EcoTextSecondary,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
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


@Preview(showBackground = true)
@Composable
fun EmissionCalculatorScreenPreview() {
    EcoTrackTheme {
        EmissionCalculatorScreen()
    }
}
