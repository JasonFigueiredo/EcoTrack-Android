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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AddEnergyScreen(
    onBackClick: () -> Unit = {}
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
                    text = "Consumo de Energia",
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
                text = "Tipos de Energia",
                color = EcoTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(getEnergyTypes()) { energyType ->
                    EnergyTypeCard(energyType = energyType)
                }
            }
        }
    }
}

@Composable
fun EnergyTypeCard(
    energyType: EnergyType
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
                imageVector = energyType.icon,
                contentDescription = energyType.name,
                tint = energyType.color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = energyType.name,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${energyType.consumptionPerHour} kWh/hora",
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

// Data class
data class EnergyType(
    val name: String,
    val icon: ImageVector,
    val color: Color,
    val consumptionPerHour: Double // kWh por hora
)

fun getEnergyTypes(): List<EnergyType> {
    return listOf(
        EnergyType(
            name = "Ar Condicionado",
            icon = Icons.Default.AcUnit,
            color = EcoGreen,
            consumptionPerHour = 1.5 // kWh por hora
        ),
        EnergyType(
            name = "Geladeira",
            icon = Icons.Default.Kitchen,
            color = EcoGreenLight,
            consumptionPerHour = 0.8 // kWh por hora
        ),
        EnergyType(
            name = "Micro-ondas",
            icon = Icons.Default.Microwave,
            color = EcoGreenAccent,
            consumptionPerHour = 1.2 // kWh por hora
        ),
        EnergyType(
            name = "Lavadora",
            icon = Icons.Default.LocalLaundryService,
            color = EcoGreen,
            consumptionPerHour = 2.0 // kWh por hora
        ),
        EnergyType(
            name = "Secadora",
            icon = Icons.Default.DryCleaning,
            color = EcoGreenLight,
            consumptionPerHour = 3.5 // kWh por hora
        ),
        EnergyType(
            name = "Forno Elétrico",
            icon = Icons.Default.LocalPizza,
            color = EcoGreenAccent,
            consumptionPerHour = 2.5 // kWh por hora
        ),
        EnergyType(
            name = "Chuveiro Elétrico",
            icon = Icons.Default.Shower,
            color = EcoGreen,
            consumptionPerHour = 4.5 // kWh por hora
        ),
        EnergyType(
            name = "Computador",
            icon = Icons.Default.Computer,
            color = EcoGreenLight,
            consumptionPerHour = 0.3 // kWh por hora
        ),
        EnergyType(
            name = "TV",
            icon = Icons.Default.Tv,
            color = EcoGreenAccent,
            consumptionPerHour = 0.2 // kWh por hora
        ),
        EnergyType(
            name = "Iluminação LED",
            icon = Icons.Default.Lightbulb,
            color = EcoGreen,
            consumptionPerHour = 0.1 // kWh por hora
        ),
        EnergyType(
            name = "Carregador de Celular",
            icon = Icons.Default.BatteryChargingFull,
            color = EcoGreenLight,
            consumptionPerHour = 0.05 // kWh por hora
        ),
        EnergyType(
            name = "Aspirador de Pó",
            icon = Icons.Default.CleaningServices,
            color = EcoGreenAccent,
            consumptionPerHour = 1.8 // kWh por hora
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AddEnergyScreenPreview() {
    EcoTrackTheme {
        AddEnergyScreen()
    }
}
