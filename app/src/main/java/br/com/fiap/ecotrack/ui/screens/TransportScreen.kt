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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*
import br.com.fiap.ecotrack.model.getAvailableTransportTypes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransportScreen(
    onBackClick: () -> Unit = {},
    onAddTransport: () -> Unit = {},
    onEmissionCalculator: () -> Unit = {}
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
                    text = "Transporte",
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
                IconButton(onClick = onEmissionCalculator) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = "Calculadora de Emissões",
                        tint = EcoGreen
                    )
                }
                IconButton(onClick = onAddTransport) {
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
            TransportSummaryCard()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Lista de transportes
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
                items(getTransportItems()) { transport ->
                    TransportCard(transport = transport)
                }
            }
        }
    }
}

@Composable
fun TransportSummaryCard() {
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
                        text = "CO₂ do Transporte",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "7.8 kg",
                        color = EcoGreen,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Distância",
                        color = EcoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "45.2 km",
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
                TransportBar("Carro", 0.7f, EcoGreen)
                TransportBar("Ônibus", 0.3f, EcoGreenLight)
                TransportBar("Bicicleta", 0.1f, EcoGreenAccent)
            }
        }
    }
}

@Composable
fun TransportBar(
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
fun TransportCard(
    transport: TransportItem
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
                imageVector = transport.icon,
                contentDescription = transport.type,
                tint = transport.color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transport.type,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = transport.details,
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = transport.co2Amount,
                    color = transport.color,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = transport.distance,
                    color = EcoTextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

data class TransportItem(
    val type: String,
    val details: String,
    val icon: ImageVector,
    val color: Color,
    val co2Amount: String,
    val distance: String
)

fun getTransportItems(): List<TransportItem> {
    val transportTypes = getAvailableTransportTypes()
    return listOf(
        TransportItem(
            type = transportTypes[0].name, // Carro (Gasolina)
            details = "Casa → Trabalho",
            icon = transportTypes[0].icon,
            color = transportTypes[0].color,
            co2Amount = "${String.format("%.2f", transportTypes[0].co2PerKm * 25)} kg", // 25 km * 0.192
            distance = "25 km"
        ),
        TransportItem(
            type = transportTypes[4].name, // Ônibus
            details = "Trabalho → Shopping",
            icon = transportTypes[4].icon,
            color = transportTypes[4].color,
            co2Amount = "${String.format("%.2f", transportTypes[4].co2PerKm * 15)} kg", // 15 km * 0.089
            distance = "15 km"
        ),
        TransportItem(
            type = transportTypes[6].name, // Bicicleta
            details = "Shopping → Casa",
            icon = transportTypes[6].icon,
            color = transportTypes[6].color,
            co2Amount = "${String.format("%.2f", transportTypes[6].co2PerKm * 5.2)} kg", // 5.2 km * 0.0
            distance = "5.2 km"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TransportScreenPreview() {
    EcoTrackTheme {
        TransportScreen()
    }
}
