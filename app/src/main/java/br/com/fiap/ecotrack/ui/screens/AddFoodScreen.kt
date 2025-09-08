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
import br.com.fiap.ecotrack.ui.theme.*
import br.com.fiap.ecotrack.model.FoodType
import br.com.fiap.ecotrack.model.FoodCategory
import br.com.fiap.ecotrack.model.getAvailableFoodTypes

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
                items(getAvailableFoodTypes()) { foodType ->
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
            Icon(
                imageVector = foodType.icon,
                contentDescription = foodType.name,
                tint = foodType.color,
                modifier = Modifier.size(32.dp)
            )

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
                Text(
                    text = foodType.description,
                    color = EcoTextSecondary,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddFoodScreenPreview() {
    EcoTrackTheme {
        AddFoodScreen()
    }
}
