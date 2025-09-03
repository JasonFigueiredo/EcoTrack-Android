package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
fun EnergyScreen(
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Energia",
                    color = EcoTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
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
            EnergySummaryCard()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Consumo de Hoje",
                color = EcoTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getEnergyItems()) { energy ->
                    EnergyCard(energy = energy)
                }
            }
        }
    }
}

@Composable
fun EnergySummaryCard() {
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
                        text = "CO₂ da Energia",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "2.1 kg",
                        color = EcoGreenLight,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Consumo",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "15.2 kWh",
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
fun EnergyCard(
    energy: EnergyItem
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
                imageVector = energy.icon,
                contentDescription = energy.type,
                tint = energy.color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = energy.type,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = energy.details,
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = energy.co2Amount,
                    color = energy.color,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = energy.consumption,
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

data class EnergyItem(
    val type: String,
    val details: String,
    val icon: ImageVector,
    val color: Color,
    val co2Amount: String,
    val consumption: String
)

fun getEnergyItems(): List<EnergyItem> {
    return listOf(
        EnergyItem(
            type = "Eletricidade",
            details = "Iluminação e eletrodomésticos",
            icon = Icons.Default.ElectricalServices,
            color = EcoGreenLight,
            co2Amount = "1.5 kg",
            consumption = "12.0 kWh"
        ),
        EnergyItem(
            type = "Gás",
            details = "Aquecimento e cozinha",
            icon = Icons.Default.LocalFireDepartment,
            color = EcoGreenAccent,
            co2Amount = "0.6 kg",
            consumption = "3.2 kWh"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun EnergyScreenPreview() {
    EcoTrackTheme {
        EnergyScreen()
    }
}
