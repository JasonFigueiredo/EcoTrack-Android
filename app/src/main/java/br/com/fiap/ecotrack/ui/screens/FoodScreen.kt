package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun FoodScreen(
    onBackClick: () -> Unit = {},
    onAddFood: () -> Unit = {},
    onFoodEmissionCalculator: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Alimentação",
                    color = EcoTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        contentDescription = "Voltar",
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = EcoTextPrimary
                    )
                }
            },
            actions = {
                IconButton(onClick = onFoodEmissionCalculator) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = "Calculadora de Emissões",
                        tint = EcoGreen
                    )
                }
                IconButton(onClick = onAddFood) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Help,
                        contentDescription = "Adicionar",
                        tint = EcoGreen
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
            FoodSummaryCard()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Refeições de Hoje",
                color = EcoTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getFoodItems()) { food ->
                    FoodCard(food = food)
                }
            }
        }
    }
}

@Composable
fun FoodSummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "CO₂ da Alimentação",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "2.2 kg",
                        color = EcoGreenAccent,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Calorias",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "2,150 kcal",
                        color = EcoTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun FoodCard(
    food: FoodItem
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
                imageVector = food.icon,
                contentDescription = food.meal,
                tint = food.color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = food.meal,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = food.details,
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = food.co2Amount,
                    color = food.color,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = food.calories,
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

data class FoodItem(
    val meal: String,
    val details: String,
    val icon: ImageVector,
    val color: Color,
    val co2Amount: String,
    val calories: String
)

fun getFoodItems(): List<FoodItem> {
    return listOf(
        FoodItem(
            meal = "Café da Manhã",
            details = "Pão, leite, frutas",
            icon = Icons.Default.FreeBreakfast,
            color = EcoGreenAccent,
            co2Amount = "0.8 kg",
            calories = "450 kcal"
        ),
        FoodItem(
            meal = "Almoço",
            details = "Arroz, feijão, carne",
            icon = Icons.Default.Restaurant,
            color = EcoGreen,
            co2Amount = "1.2 kg",
            calories = "850 kcal"
        ),
        FoodItem(
            meal = "Jantar",
            details = "Salada, peixe",
            icon = Icons.Default.DinnerDining,
            color = EcoGreenLight,
            co2Amount = "0.2 kg",
            calories = "650 kcal"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun FoodScreenPreview() {
    EcoTrackTheme {
        FoodScreen()
    }
}
