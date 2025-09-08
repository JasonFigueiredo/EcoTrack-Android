package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import br.com.fiap.ecotrack.model.FoodEmissionComparison
import br.com.fiap.ecotrack.model.FoodEmissionSaving
import br.com.fiap.ecotrack.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodEmissionResultsScreen(
    foodEmissionComparison: FoodEmissionComparison,
    onBackClick: () -> Unit = {},
    onCalculateNew: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Resultados da Alimentação",
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
        
        // Conteúdo scrollável
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 100.dp)
                .padding(bottom = 45.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Resultado principal
            MainFoodEmissionResultCard(foodEmissionComparison.selectedFood)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Comparações e sugestões
            if (foodEmissionComparison.savings.isNotEmpty()) {
                Text(
                    text = "Alternativas Mais Sustentáveis",
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                foodEmissionComparison.savings.forEach { saving ->
                    AlternativeFoodCard(saving)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            
            // Dicas de sustentabilidade alimentar
            FoodSustainabilityTipsCard()
        }
    }
}

@Composable
fun MainFoodEmissionResultCard(selectedFood: br.com.fiap.ecotrack.model.FoodEmissionResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ícone do alimento
            Icon(
                imageVector = selectedFood.foodType.icon,
                contentDescription = selectedFood.foodType.name,
                tint = selectedFood.foodType.color,
                modifier = Modifier.size(48.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = selectedFood.foodType.name,
                color = EcoTextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Peso: ${String.format("%.2f", selectedFood.weight)} kg (${selectedFood.period.displayName})",
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
                text = "${String.format("%.2f", selectedFood.co2Emissions)} ${selectedFood.co2Unit}",
                color = EcoGreen,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Barra de impacto
            val impactLevel = when {
                selectedFood.co2Emissions < 1.0 -> "Baixo"
                selectedFood.co2Emissions < 5.0 -> "Médio"
                selectedFood.co2Emissions < 10.0 -> "Alto"
                else -> "Muito Alto"
            }
            
            val impactColor = when (impactLevel) {
                "Baixo" -> EcoGreen
                "Médio" -> EcoGreenLight
                "Alto" -> EcoWarning
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
                            "Médio" -> Icons.Default.Info
                            "Alto" -> Icons.Default.Warning
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
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Informações Nutricionais
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = EcoGreenAccent.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Nutrição",
                            tint = EcoGreenAccent,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Informações Nutricionais",
                            color = EcoGreenAccent,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Calorias
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Calorias:",
                            color = EcoTextSecondary,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "${String.format("%.0f", selectedFood.foodType.caloriesPerKg * selectedFood.weight)} kcal",
                            color = EcoTextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    // Proteína
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Proteína:",
                            color = EcoTextSecondary,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "${String.format("%.1f", selectedFood.foodType.proteinPerKg * selectedFood.weight)} g",
                            color = EcoTextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    // Carboidratos
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Carboidratos:",
                            color = EcoTextSecondary,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "${String.format("%.1f", selectedFood.foodType.carbsPerKg * selectedFood.weight)} g",
                            color = EcoTextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    // Gordura
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Gordura:",
                            color = EcoTextSecondary,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "${String.format("%.1f", selectedFood.foodType.fatPerKg * selectedFood.weight)} g",
                            color = EcoTextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Categoria do alimento
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = selectedFood.foodType.color.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when (selectedFood.foodType.category) {
                            br.com.fiap.ecotrack.model.FoodCategory.MEAT -> Icons.Default.Restaurant
                            br.com.fiap.ecotrack.model.FoodCategory.DAIRY -> Icons.Default.LocalDrink
                            br.com.fiap.ecotrack.model.FoodCategory.GRAINS -> Icons.Default.Grain
                            br.com.fiap.ecotrack.model.FoodCategory.VEGETABLES -> Icons.Default.Grass
                            br.com.fiap.ecotrack.model.FoodCategory.FRUITS -> Icons.Default.Circle
                            br.com.fiap.ecotrack.model.FoodCategory.BEVERAGES -> Icons.Default.LocalCafe
                            br.com.fiap.ecotrack.model.FoodCategory.SNACKS -> Icons.Default.Cake
                        },
                        contentDescription = "Categoria",
                        tint = selectedFood.foodType.color,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Categoria: ${selectedFood.foodType.category.displayName}",
                        color = selectedFood.foodType.color,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun AlternativeFoodCard(saving: FoodEmissionSaving) {
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
                    imageVector = saving.foodType.icon,
                    contentDescription = saving.foodType.name,
                    tint = saving.foodType.color,
                    modifier = Modifier.size(32.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = saving.foodType.name,
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
fun FoodSustainabilityTipsCard() {
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
                    text = "Dicas de Alimentação Sustentável",
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val tips = listOf(
                "Reduza o consumo de carne vermelha - opte por frango ou peixe",
                "Inclua mais vegetais e frutas na sua dieta diária",
                "Prefira alimentos locais e sazonais quando possível",
                "Evite desperdício de alimentos - planeje suas refeições",
                "Considere opções vegetarianas algumas vezes por semana",
                "Escolha grãos integrais em vez de processados"
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
fun FoodEmissionResultsScreenPreview() {
    EcoTrackTheme {
        // Preview com dados mockados
        val mockComparison = createMockFoodEmissionComparison()
        FoodEmissionResultsScreen(foodEmissionComparison = mockComparison)
    }
}

private fun createMockFoodEmissionComparison(): FoodEmissionComparison {
    // Esta função será removida quando a integração real estiver pronta
    return FoodEmissionComparison(
        selectedFood = br.com.fiap.ecotrack.model.FoodEmissionResult(
            foodType = br.com.fiap.ecotrack.model.FoodType(
                id = "beef",
                name = "Carne Bovina",
                icon = Icons.Default.Restaurant,
                color = EcoError,
                co2PerKg = 27.0,
                caloriesPerKg = 2500.0,
                proteinPerKg = 260.0,
                carbsPerKg = 0.0,
                fatPerKg = 150.0,
                climatiqActivityId = "food-beef",
                description = "Carne bovina - alta pegada de carbono",
                category = br.com.fiap.ecotrack.model.FoodCategory.MEAT
            ),
            weight = 0.5,
            period = br.com.fiap.ecotrack.model.ConsumptionPeriod.DAILY,
            co2Emissions = 13.5,
            co2Unit = "kg",
            emissionFactor = br.com.fiap.ecotrack.model.EmissionFactorResponse(
                activity_id = "food-beef",
                activity_name = "Carne Bovina",
                category = "Food",
                source = "Local",
                year = "2024",
                region = "BR",
                unit = "kg",
                unit_type = "mass"
            )
        ),
        alternatives = emptyList(),
        savings = listOf(
            FoodEmissionSaving(
                foodType = br.com.fiap.ecotrack.model.FoodType(
                    id = "chicken",
                    name = "Frango",
                    icon = Icons.Default.Restaurant,
                    color = EcoGreenLight,
                    co2PerKg = 6.9,
                    caloriesPerKg = 1650.0,
                    proteinPerKg = 310.0,
                    carbsPerKg = 0.0,
                    fatPerKg = 35.0,
                    climatiqActivityId = "food-chicken",
                    description = "Frango - opção mais sustentável entre carnes",
                    category = br.com.fiap.ecotrack.model.FoodCategory.MEAT
                ),
                co2Saved = 10.05,
                percentageSaved = 74.4,
                benefits = listOf("Menor impacto que carne bovina", "Proteína magra", "Versátil na culinária"),
                drawbacks = listOf("Ainda tem impacto ambiental", "Necessita refrigeração")
            )
        )
    )
}
