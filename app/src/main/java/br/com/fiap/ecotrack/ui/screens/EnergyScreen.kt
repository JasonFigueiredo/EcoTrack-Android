package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnergyScreen(
    onBackClick: () -> Unit = {},
    onAddEnergy: () -> Unit = {}
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
            actions = {
                IconButton(onClick = onAddEnergy) {
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
            // Resumo do dia
            EnergySummaryCard()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Lista de energias
            Text(
                text = "Hoje",
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
                        text = "Consumo de Energia",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "12.5 kWh",
                        color = EcoGreen,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Custo",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "R$ 8.75",
                        color = EcoTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Gráfico de barras simples
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                EnergyBar("Ar Cond.", 0.6f, EcoGreen)
                EnergyBar("Geladeira", 0.4f, EcoGreenLight)
                EnergyBar("TV", 0.2f, EcoGreenAccent)
            }
        }
    }
}

@Composable
fun EnergyBar(
    label: String,
    height: Float,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(40.dp)
                .height((height * 60).dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(4.dp)
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = EcoTextSecondary,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
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
                    text = energy.consumption,
                    color = energy.color,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = energy.duration,
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
    val consumption: String,
    val duration: String
)

fun getEnergyItems(): List<EnergyItem> {
    return listOf(
        EnergyItem(
            type = "Ar Condicionado",
            details = "Sala de estar",
            icon = Icons.Default.AcUnit,
            color = EcoGreen,
            consumption = "4.5 kWh",
            duration = "3 horas"
        ),
        EnergyItem(
            type = "Geladeira",
            details = "Cozinha",
            icon = Icons.Default.Kitchen,
            color = EcoGreenLight,
            consumption = "2.4 kWh",
            duration = "24 horas"
        ),
        EnergyItem(
            type = "TV",
            details = "Sala de estar",
            icon = Icons.Default.Tv,
            color = EcoGreenAccent,
            consumption = "0.6 kWh",
            duration = "3 horas"
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