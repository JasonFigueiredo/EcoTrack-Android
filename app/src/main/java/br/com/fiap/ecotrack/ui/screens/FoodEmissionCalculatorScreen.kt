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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.model.*
import br.com.fiap.ecotrack.service.FoodClimatiqRepository
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodEmissionCalculatorScreen(
    onBackClick: () -> Unit = {},
    onShowResults: (FoodEmissionComparison) -> Unit = {}
) {
    var selectedFood by remember { mutableStateOf<FoodType?>(null) }
    var weight by remember { mutableStateOf("") }
    var selectedPeriod by remember { mutableStateOf(ConsumptionPeriod.DAILY) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var shouldCalculate by remember { mutableStateOf(false) }
    
    val foodRepository = remember { FoodClimatiqRepository() }
    val availableFoods = remember { getAvailableFoodTypes() }
    
    // LaunchedEffect para calcular emissões quando shouldCalculate for true
    LaunchedEffect(shouldCalculate) {
        if (shouldCalculate && selectedFood != null && weight.isNotBlank()) {
            val weightValue = weight.toDoubleOrNull()
            if (weightValue != null && weightValue > 0) {
                try {
                    // Primeiro, calcular emissões do alimento selecionado
                    val selectedResult = foodRepository.calculateFoodEmissions(
                        selectedFood!!,
                        weightValue,
                        selectedPeriod
                    ).getOrElse { 
                        // Se falhar, usar cálculo local
                        FoodEmissionResult(
                            foodType = selectedFood!!,
                            weight = weightValue * selectedPeriod.multiplier,
                            period = selectedPeriod,
                            co2Emissions = selectedFood!!.co2PerKg * weightValue * selectedPeriod.multiplier,
                            co2Unit = "kg",
                            emissionFactor = EmissionFactorResponse(
                                activity_id = selectedFood!!.climatiqActivityId,
                                activity_name = selectedFood!!.name,
                                category = "Food",
                                source = "Local",
                                year = "2024",
                                region = "BR",
                                unit = "kg",
                                unit_type = "mass"
                            )
                        )
                    }
                    
                    // Calcular emissões das alternativas (alimentos da mesma categoria com menor impacto)
                    val alternativeResults = availableFoods
                        .filter { it.category == selectedFood!!.category && it.id != selectedFood!!.id }
                        .filter { it.co2PerKg < selectedFood!!.co2PerKg }
                        .take(3) // Limitar a 3 alternativas
                        .map { food ->
                            foodRepository.calculateFoodEmissions(food, weightValue, selectedPeriod)
                                .getOrElse {
                                    FoodEmissionResult(
                                        foodType = food,
                                        weight = weightValue * selectedPeriod.multiplier,
                                        period = selectedPeriod,
                                        co2Emissions = food.co2PerKg * weightValue * selectedPeriod.multiplier,
                                        co2Unit = "kg",
                                        emissionFactor = EmissionFactorResponse(
                                            activity_id = food.climatiqActivityId,
                                            activity_name = food.name,
                                            category = "Food",
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
                    val comparison = foodRepository.createFoodEmissionComparison(
                        selectedResult,
                        alternativeResults
                    )
                    
                    // Salvar no estado global
                    FoodEmissionState.setFoodEmissionComparison(comparison)
                    
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
                errorMessage = "Por favor, insira um peso válido"
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
                    text = "Calculadora de Alimentação",
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
                text = "Selecione o alimento e a quantidade consumida",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Campo de peso
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Peso (kg)", color = EcoTextSecondary) },
                placeholder = { Text("Ex: 0.5", color = EcoTextSecondary) },
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
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                )
            )
            
            // Debug: mostrar peso digitado
            if (weight.isNotBlank()) {
                val weightValue = weight.toDoubleOrNull()
                if (weightValue != null && weightValue > 0) {
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
                                imageVector = Icons.Default.Scale,
                                contentDescription = "Peso",
                                tint = EcoGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Peso: $weightValue kg",
                                color = EcoGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Seleção de período
            Text(
                text = "Período de Consumo",
                color = EcoTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ConsumptionPeriod.values().forEach { period ->
                    FilterChip(
                        onClick = { selectedPeriod = period },
                        label = { 
                            Text(
                                text = period.displayName,
                                fontSize = 12.sp
                            ) 
                        },
                        selected = selectedPeriod == period,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EcoGreen,
                            selectedLabelColor = EcoTextOnGreen,
                            containerColor = EcoDarkSurface,
                            labelColor = EcoTextSecondary
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Seleção de alimento
            Text(
                text = "Tipo de Alimento",
                color = EcoTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            
            // Debug: mostrar seleção atual
            selectedFood?.let { food ->
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
                            text = "Selecionado: ${food.name}",
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
                // Agrupar alimentos por categoria
                val foodsByCategory = availableFoods.groupBy { it.category }
                
                foodsByCategory.forEach { (category, foods) ->
                    item {
                        Text(
                            text = category.displayName,
                            color = EcoTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    
                    items(foods) { food ->
                        FoodSelectionCard(
                            food = food,
                            isSelected = selectedFood?.id == food.id,
                            onClick = { selectedFood = food }
                        )
                    }
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
            if (selectedFood != null && weight.isNotBlank()) {
                val weightValue = weight.toDoubleOrNull()
                if (weightValue != null && weightValue > 0) {
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
                                text = "Pronto para calcular! ${selectedFood!!.name} - ${weightValue}kg/${selectedPeriod.displayName}",
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
                    if (selectedFood != null && weight.isNotBlank()) {
                        val weightValue = weight.toDoubleOrNull()
                        if (weightValue != null && weightValue > 0) {
                            isLoading = true
                            errorMessage = null
                            shouldCalculate = true
                        } else {
                            errorMessage = "Por favor, insira um peso válido"
                        }
                    } else {
                        errorMessage = "Por favor, selecione um alimento e insira o peso"
                    }
                },
                enabled = !isLoading && selectedFood != null && weight.isNotBlank(),
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
fun FoodSelectionCard(
    food: FoodType,
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
                imageVector = food.icon,
                contentDescription = food.name,
                tint = if (isSelected) EcoGreen else food.color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = food.name,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = food.description,
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = "${String.format("%.1f", food.co2PerKg)} kg CO₂/kg",
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
fun FoodEmissionCalculatorScreenPreview() {
    EcoTrackTheme {
        FoodEmissionCalculatorScreen()
    }
}
