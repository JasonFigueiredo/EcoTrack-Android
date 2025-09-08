package br.com.fiap.ecotrack.model

import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class FoodType(
    val id: String,
    val name: String,
    val icon: ImageVector,
    val color: androidx.compose.ui.graphics.Color,
    val co2PerKg: Double, // kg CO2 por kg de alimento
    val caloriesPerKg: Double, // calorias por kg de alimento
    val proteinPerKg: Double, // proteína por kg de alimento (g)
    val carbsPerKg: Double, // carboidratos por kg de alimento (g)
    val fatPerKg: Double, // gordura por kg de alimento (g)
    val climatiqActivityId: String, // ID para usar na API do Climatiq
    val description: String,
    val category: FoodCategory
)

enum class FoodCategory(val displayName: String) {
    MEAT("Carnes"),
    DAIRY("Laticínios"),
    GRAINS("Grãos"),
    VEGETABLES("Vegetais"),
    FRUITS("Frutas"),
    BEVERAGES("Bebidas"),
    SNACKS("Lanches")
}

enum class ConsumptionPeriod(val displayName: String, val multiplier: Double) {
    DAILY("Diário", 1.0),
    WEEKLY("Semanal", 7.0),
    MONTHLY("Mensal", 30.0),
    YEARLY("Anual", 365.0)
}

// Tipos de alimentos disponíveis
enum class FoodTypeEnum(val activityId: String, val displayName: String) {
    BEEF("food-beef", "Carne Bovina"),
    PORK("food-pork", "Carne Suína"),
    CHICKEN("food-chicken", "Frango"),
    FISH("food-fish", "Peixe"),
    MILK("food-milk", "Leite"),
    CHEESE("food-cheese", "Queijo"),
    EGGS("food-eggs", "Ovos"),
    RICE("food-rice", "Arroz"),
    WHEAT("food-wheat", "Trigo"),
    CORN("food-corn", "Milho"),
    POTATOES("food-potatoes", "Batatas"),
    TOMATOES("food-tomatoes", "Tomates"),
    APPLES("food-apples", "Maçãs"),
    BANANAS("food-bananas", "Bananas"),
    COFFEE("food-coffee", "Café"),
    TEA("food-tea", "Chá"),
    BEER("food-beer", "Cerveja"),
    WINE("food-wine", "Vinho"),
    CHOCOLATE("food-chocolate", "Chocolate"),
    NUTS("food-nuts", "Nozes")
}

// Função para obter os tipos de alimentos disponíveis
// Valores baseados em dados reais da API Climatiq e estudos científicos
fun getAvailableFoodTypes(): List<FoodType> {
    return listOf(
        // Carnes
        FoodType(
            id = "beef",
            name = "Carne Bovina",
            icon = androidx.compose.material.icons.Icons.Default.Restaurant,
            color = br.com.fiap.ecotrack.ui.theme.EcoError,
            co2PerKg = 27.0, // kg CO₂/kg - uma das maiores emissões
            caloriesPerKg = 2500.0, // kcal/kg
            proteinPerKg = 260.0, // g/kg
            carbsPerKg = 0.0, // g/kg
            fatPerKg = 150.0, // g/kg
            climatiqActivityId = FoodTypeEnum.BEEF.activityId,
            description = "Carne bovina - alta pegada de carbono",
            category = FoodCategory.MEAT
        ),
        FoodType(
            id = "pork",
            name = "Carne Suína",
            icon = androidx.compose.material.icons.Icons.Default.Restaurant,
            color = br.com.fiap.ecotrack.ui.theme.EcoWarning,
            co2PerKg = 12.1, // kg CO₂/kg
            caloriesPerKg = 2630.0, // kcal/kg
            proteinPerKg = 270.0, // g/kg
            carbsPerKg = 0.0, // g/kg
            fatPerKg = 160.0, // g/kg
            climatiqActivityId = FoodTypeEnum.PORK.activityId,
            description = "Carne suína - emissões moderadas",
            category = FoodCategory.MEAT
        ),
        FoodType(
            id = "chicken",
            name = "Frango",
            icon = androidx.compose.material.icons.Icons.Default.Restaurant,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenLight,
            co2PerKg = 6.9, // kg CO₂/kg - menor que outras carnes
            caloriesPerKg = 1650.0, // kcal/kg
            proteinPerKg = 310.0, // g/kg
            carbsPerKg = 0.0, // g/kg
            fatPerKg = 35.0, // g/kg
            climatiqActivityId = FoodTypeEnum.CHICKEN.activityId,
            description = "Frango - opção mais sustentável entre carnes",
            category = FoodCategory.MEAT
        ),
        FoodType(
            id = "fish",
            name = "Peixe",
            icon = androidx.compose.material.icons.Icons.Default.SetMeal,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PerKg = 3.5, // kg CO₂/kg - varia conforme tipo de pesca
            caloriesPerKg = 2060.0, // kcal/kg
            proteinPerKg = 220.0, // g/kg
            carbsPerKg = 0.0, // g/kg
            fatPerKg = 120.0, // g/kg
            climatiqActivityId = FoodTypeEnum.FISH.activityId,
            description = "Peixe - proteína com menor impacto",
            category = FoodCategory.MEAT
        ),
        
        // Laticínios
        FoodType(
            id = "milk",
            name = "Leite",
            icon = androidx.compose.material.icons.Icons.Default.LocalDrink,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenLight,
            co2PerKg = 3.2, // kg CO₂/kg
            caloriesPerKg = 640.0, // kcal/kg
            proteinPerKg = 33.0, // g/kg
            carbsPerKg = 48.0, // g/kg
            fatPerKg = 34.0, // g/kg
            climatiqActivityId = FoodTypeEnum.MILK.activityId,
            description = "Leite - laticínio básico",
            category = FoodCategory.DAIRY
        ),
        FoodType(
            id = "cheese",
            name = "Queijo",
            icon = androidx.compose.material.icons.Icons.Default.SetMeal,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenLight,
            co2PerKg = 13.5, // kg CO₂/kg - concentrado, maior impacto
            caloriesPerKg = 3560.0, // kcal/kg
            proteinPerKg = 250.0, // g/kg
            carbsPerKg = 20.0, // g/kg
            fatPerKg = 290.0, // g/kg
            climatiqActivityId = FoodTypeEnum.CHEESE.activityId,
            description = "Queijo - laticínio concentrado",
            category = FoodCategory.DAIRY
        ),
        FoodType(
            id = "eggs",
            name = "Ovos",
            icon = androidx.compose.material.icons.Icons.Default.Egg,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PerKg = 4.2, // kg CO₂/kg
            caloriesPerKg = 1550.0, // kcal/kg
            proteinPerKg = 130.0, // g/kg
            carbsPerKg = 10.0, // g/kg
            fatPerKg = 110.0, // g/kg
            climatiqActivityId = FoodTypeEnum.EGGS.activityId,
            description = "Ovos - proteína animal eficiente",
            category = FoodCategory.DAIRY
        ),
        
        // Grãos
        FoodType(
            id = "rice",
            name = "Arroz",
            icon = androidx.compose.material.icons.Icons.Default.Grain,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PerKg = 4.0, // kg CO₂/kg
            caloriesPerKg = 1300.0, // kcal/kg
            proteinPerKg = 28.0, // g/kg
            carbsPerKg = 280.0, // g/kg
            fatPerKg = 2.8, // g/kg
            climatiqActivityId = FoodTypeEnum.RICE.activityId,
            description = "Arroz - grão básico",
            category = FoodCategory.GRAINS
        ),
        FoodType(
            id = "wheat",
            name = "Trigo",
            icon = androidx.compose.material.icons.Icons.Default.Grain,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PerKg = 1.4, // kg CO₂/kg
            caloriesPerKg = 3390.0, // kcal/kg
            proteinPerKg = 130.0, // g/kg
            carbsPerKg = 710.0, // g/kg
            fatPerKg = 20.0, // g/kg
            climatiqActivityId = FoodTypeEnum.WHEAT.activityId,
            description = "Trigo - base para pães e massas",
            category = FoodCategory.GRAINS
        ),
        FoodType(
            id = "corn",
            name = "Milho",
            icon = androidx.compose.material.icons.Icons.Default.Grain,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PerKg = 1.0, // kg CO₂/kg
            caloriesPerKg = 3650.0, // kcal/kg
            proteinPerKg = 90.0, // g/kg
            carbsPerKg = 740.0, // g/kg
            fatPerKg = 40.0, // g/kg
            climatiqActivityId = FoodTypeEnum.CORN.activityId,
            description = "Milho - grão versátil",
            category = FoodCategory.GRAINS
        ),
        
        // Vegetais
        FoodType(
            id = "potatoes",
            name = "Batatas",
            icon = androidx.compose.material.icons.Icons.Default.Circle,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
            co2PerKg = 0.2, // kg CO₂/kg - muito baixo
            caloriesPerKg = 770.0, // kcal/kg
            proteinPerKg = 20.0, // g/kg
            carbsPerKg = 170.0, // g/kg
            fatPerKg = 1.0, // g/kg
            climatiqActivityId = FoodTypeEnum.POTATOES.activityId,
            description = "Batatas - vegetal de baixo impacto",
            category = FoodCategory.VEGETABLES
        ),
        FoodType(
            id = "tomatoes",
            name = "Tomates",
            icon = androidx.compose.material.icons.Icons.Default.Circle,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
            co2PerKg = 2.0, // kg CO₂/kg
            caloriesPerKg = 180.0, // kcal/kg
            proteinPerKg = 9.0, // g/kg
            carbsPerKg = 38.0, // g/kg
            fatPerKg = 2.0, // g/kg
            climatiqActivityId = FoodTypeEnum.TOMATOES.activityId,
            description = "Tomates - vegetal nutritivo",
            category = FoodCategory.VEGETABLES
        ),
        
        // Frutas
        FoodType(
            id = "grapes",
            name = "Uvas",
            icon = androidx.compose.material.icons.Icons.Default.Circle,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
            co2PerKg = 0.5, // kg CO₂/kg
            caloriesPerKg = 620.0, // kcal/kg
            proteinPerKg = 6.0, // g/kg
            carbsPerKg = 160.0, // g/kg
            fatPerKg = 1.0, // g/kg
            climatiqActivityId = "food-grapes",
            description = "Uvas - fruta versátil e nutritiva",
            category = FoodCategory.FRUITS
        ),
        FoodType(
            id = "oranges",
            name = "Laranjas",
            icon = androidx.compose.material.icons.Icons.Default.Circle,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
            co2PerKg = 0.3, // kg CO₂/kg
            caloriesPerKg = 470.0, // kcal/kg
            proteinPerKg = 9.0, // g/kg
            carbsPerKg = 118.0, // g/kg
            fatPerKg = 1.0, // g/kg
            climatiqActivityId = "food-oranges",
            description = "Laranjas - fruta cítrica rica em vitamina C",
            category = FoodCategory.FRUITS
        ),
        
        // Bebidas
        FoodType(
            id = "coffee",
            name = "Café",
            icon = androidx.compose.material.icons.Icons.Default.LocalCafe,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenLight,
            co2PerKg = 16.5, // kg CO₂/kg - alto devido ao processamento
            caloriesPerKg = 0.0, // kcal/kg (líquido)
            proteinPerKg = 0.0, // g/kg
            carbsPerKg = 0.0, // g/kg
            fatPerKg = 0.0, // g/kg
            climatiqActivityId = FoodTypeEnum.COFFEE.activityId,
            description = "Café - bebida com alto impacto",
            category = FoodCategory.BEVERAGES
        ),
        FoodType(
            id = "tea",
            name = "Chá",
            icon = androidx.compose.material.icons.Icons.Default.LocalCafe,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PerKg = 2.0, // kg CO₂/kg
            caloriesPerKg = 0.0, // kcal/kg (líquido)
            proteinPerKg = 0.0, // g/kg
            carbsPerKg = 0.0, // g/kg
            fatPerKg = 0.0, // g/kg
            climatiqActivityId = FoodTypeEnum.TEA.activityId,
            description = "Chá - bebida de baixo impacto",
            category = FoodCategory.BEVERAGES
        ),
        FoodType(
            id = "beer",
            name = "Cerveja",
            icon = androidx.compose.material.icons.Icons.Default.LocalBar,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenLight,
            co2PerKg = 0.6, // kg CO₂/kg
            caloriesPerKg = 430.0, // kcal/kg
            proteinPerKg = 3.5, // g/kg
            carbsPerKg = 35.0, // g/kg
            fatPerKg = 0.0, // g/kg
            climatiqActivityId = FoodTypeEnum.BEER.activityId,
            description = "Cerveja - bebida alcoólica",
            category = FoodCategory.BEVERAGES
        ),
        FoodType(
            id = "wine",
            name = "Vinho",
            icon = androidx.compose.material.icons.Icons.Default.LocalBar,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreenLight,
            co2PerKg = 1.8, // kg CO₂/kg
            caloriesPerKg = 830.0, // kcal/kg
            proteinPerKg = 0.1, // g/kg
            carbsPerKg = 25.0, // g/kg
            fatPerKg = 0.0, // g/kg
            climatiqActivityId = FoodTypeEnum.WINE.activityId,
            description = "Vinho - bebida alcoólica",
            category = FoodCategory.BEVERAGES
        ),
        
        // Lanches
        FoodType(
            id = "chocolate",
            name = "Chocolate",
            icon = androidx.compose.material.icons.Icons.Default.Cake,
            color = br.com.fiap.ecotrack.ui.theme.EcoWarning,
            co2PerKg = 19.0, // kg CO₂/kg - alto devido ao cacau
            caloriesPerKg = 5460.0, // kcal/kg
            proteinPerKg = 75.0, // g/kg
            carbsPerKg = 610.0, // g/kg
            fatPerKg = 310.0, // g/kg
            climatiqActivityId = FoodTypeEnum.CHOCOLATE.activityId,
            description = "Chocolate - doce com alto impacto",
            category = FoodCategory.SNACKS
        ),
        FoodType(
            id = "nuts",
            name = "Nozes",
            icon = androidx.compose.material.icons.Icons.Default.Cake,
            color = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PerKg = 0.3, // kg CO₂/kg - muito baixo
            caloriesPerKg = 6540.0, // kcal/kg
            proteinPerKg = 150.0, // g/kg
            carbsPerKg = 130.0, // g/kg
            fatPerKg = 650.0, // g/kg
            climatiqActivityId = FoodTypeEnum.NUTS.activityId,
            description = "Nozes - lanche saudável e sustentável",
            category = FoodCategory.SNACKS
        )
    )
}
