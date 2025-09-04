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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.R
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AddFoodScreen(
    onBackClick: () -> Unit = {},
    onSaveFood: () -> Unit = {},
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
                    text = "Alimentos e Emissões",
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
                text = "Tipos de Alimentos",
                color = EcoTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(getFoodTypes()) { foodType ->
                    FoodTypeCard(foodType = foodType)
                }
            }
        }
    }
}

@Composable
fun FoodTypeCard(
    foodType: FoodType
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
            if (foodType.isCustomIcon) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = foodType.customIconResId!!),
                    contentDescription = foodType.name,
                    tint = foodType.color,
                    modifier = Modifier.size(32.dp)
                )
            } else {
                Icon(
                    imageVector = foodType.icon,
                    contentDescription = foodType.name,
                    tint = foodType.color,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = foodType.name,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${foodType.co2PerKg} kg CO₂/kg",
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

// Data class
data class FoodType(
    val name: String,
    val icon: ImageVector,
    val color: Color,
    val co2PerKg: Double, // kg CO2 por kg de alimento
    val isCustomIcon: Boolean = false,
    val customIconResId: Int? = null
)

fun getFoodTypes(): List<FoodType> {
    return listOf(
        // Carnes (Alto impacto)
        FoodType(
            name = "Carne Bovina",
            icon = Icons.Default.Restaurant,
            color = EcoGreen,
            co2PerKg = 27.0 // kg CO2 por kg
        ),
        FoodType(
            name = "Carne de Porco",
            icon = Icons.Default.Restaurant,
            color = EcoGreenLight,
            co2PerKg = 12.1 // kg CO2 por kg
        ),
        FoodType(
            name = "Frango",
            icon = Icons.Default.Restaurant,
            color = EcoGreenAccent,
            co2PerKg = 6.9 // kg CO2 por kg
        ),
        FoodType(
            name = "Peixe",
            icon = Icons.Default.SetMeal,
            color = EcoGreen,
            co2PerKg = 5.1 // kg CO2 por kg
        ),
        
        // Laticínios
        FoodType(
            name = "Queijo",
            icon = Icons.Default.Kitchen,
            color = EcoGreenLight,
            co2PerKg = 13.5 // kg CO2 por kg
        ),
        FoodType(
            name = "Leite",
            icon = Icons.Default.LocalDrink,
            color = EcoGreenAccent,
            co2PerKg = 3.2 // kg CO2 por kg
        ),
        FoodType(
            name = "Ovos",
            icon = Icons.Default.Egg,
            color = EcoGreen,
            co2PerKg = 4.2 // kg CO2 por kg
        ),
        
        // Grãos e Cereais
        FoodType(
            name = "Arroz",
            icon = Icons.Default.Grain,
            color = EcoGreenLight,
            co2PerKg = 4.0 // kg CO2 por kg
        ),
        FoodType(
            name = "Trigo/Pão",
            icon = Icons.Default.BakeryDining,
            color = EcoGreenAccent,
            co2PerKg = 2.5 // kg CO2 por kg
        ),
        FoodType(
            name = "Milho",
            icon = Icons.Default.Grain,
            color = EcoGreen,
            co2PerKg = 1.7 // kg CO2 por kg
        ),
        
        // Frutas e Vegetais
        FoodType(
            name = "Banana",
            icon = Icons.Default.Cake, // ícone padrão como fallback
            color = EcoGreenLight,
            co2PerKg = 0.7, // kg CO2 por kg
            isCustomIcon = true,
            customIconResId = R.drawable.banana_icon
        ),
        FoodType(
            name = "Maçã",
            icon = Icons.Default.AcUnit, // ícone padrão como fallback
            color = EcoGreenAccent,
            co2PerKg = 0.4, // kg CO2 por kg
            isCustomIcon = true,
            customIconResId = R.drawable.apple_icon
        ),
        FoodType(
            name = "Tomate",
            icon = Icons.Default.LocalPizza,
            color = EcoGreen,
            co2PerKg = 2.0 // kg CO2 por kg
        ),
        FoodType(
            name = "Batata",
            icon = Icons.Default.LocalPizza,
            color = EcoGreenLight,
            co2PerKg = 0.2 // kg CO2 por kg
        ),
        FoodType(
            name = "Cenoura",
            icon = Icons.Default.LocalPizza,
            color = EcoGreenAccent,
            co2PerKg = 0.4 // kg CO2 por kg
        ),
        
        // Leguminosas
        FoodType(
            name = "Feijão",
            icon = Icons.Default.Grain,
            color = EcoGreen,
            co2PerKg = 2.0 // kg CO2 por kg
        ),
        FoodType(
            name = "Lentilha",
            icon = Icons.Default.Grain,
            color = EcoGreenLight,
            co2PerKg = 0.9 // kg CO2 por kg
        ),
        FoodType(
            name = "Soja",
            icon = Icons.Default.Grain,
            color = EcoGreenAccent,
            co2PerKg = 2.0 // kg CO2 por kg
        ),
        
        // Nozes e Sementes
        FoodType(
            name = "Amêndoas",
            icon = Icons.Default.Grain,
            color = EcoGreen,
            co2PerKg = 2.3 // kg CO2 por kg
        ),
        FoodType(
            name = "Nozes",
            icon = Icons.Default.Grain,
            color = EcoGreenLight,
            co2PerKg = 2.3 // kg CO2 por kg
        ),
        
        // Bebidas
        FoodType(
            name = "Café",
            icon = Icons.Default.FreeBreakfast,
            color = EcoGreenAccent,
            co2PerKg = 16.5 // kg CO2 por kg
        ),
        FoodType(
            name = "Chá",
            icon = Icons.Default.LocalDrink,
            color = EcoGreen,
            co2PerKg = 7.0 // kg CO2 por kg
        ),
        FoodType(
            name = "Açúcar",
            icon = Icons.Default.Cake,
            color = EcoGreenLight,
            co2PerKg = 3.2 // kg CO2 por kg
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AddFoodScreenPreview() {
    EcoTrackTheme {
        AddFoodScreen()
    }
}