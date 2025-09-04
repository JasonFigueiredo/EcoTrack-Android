package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddConfigScreen(
    onBackClick: () -> Unit = {}
) {
    var userName by remember { mutableStateOf("EcoUser") }
    var birthDate by remember { mutableStateOf("15/03/1990") }
    var userHeight by remember { mutableStateOf("170") }
    
    // Metas de Transporte
    var transportGoal by remember { mutableStateOf("50") }
    var walkingGoal by remember { mutableStateOf("5") }
    var cyclingGoal by remember { mutableStateOf("10") }
    
    // Metas de Energia
    var energyGoal by remember { mutableStateOf("200") }
    var ledGoal by remember { mutableStateOf("15") }
    var solarGoal by remember { mutableStateOf("100") }
    
    // Metas de Alimentação
    var foodGoal by remember { mutableStateOf("80") }
    var localFoodGoal by remember { mutableStateOf("60") }
    var wasteReductionGoal by remember { mutableStateOf("90") }
    
    // Metas de Resíduos
    var recyclingGoal by remember { mutableStateOf("95") }
    var compostingGoal by remember { mutableStateOf("70") }
    var plasticReductionGoal by remember { mutableStateOf("85") }
    
    // Metas de Água
    var waterGoal by remember { mutableStateOf("120") }
    var rainwaterGoal by remember { mutableStateOf("50") }
    var showerGoal by remember { mutableStateOf("5") }
    
    // Metas de Plantação
    var treePlantingGoal by remember { mutableStateOf("12") }
    var gardenGoal by remember { mutableStateOf("8") }
    var communityGoal by remember { mutableStateOf("20") }
    
    // Estado para o efeito de carregamento
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
            .padding(bottom = 30.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Configurações",
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

        if (isLoading) {
            // Tela de carregamento
            LoadingScreen()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Perfil do Usuário
                item {
                    ProfileConfigSection(
                        userName = userName,
                        onUserNameChange = { userName = it },
                        birthDate = birthDate,
                        onBirthDateChange = { birthDate = it },
                        userHeight = userHeight,
                        onUserHeightChange = { userHeight = it }
                    )
                }
                
                // Metas de Transporte
                item {
                    TransportGoalsSection(
                        transportGoal = transportGoal,
                        onTransportGoalChange = { transportGoal = it },
                        walkingGoal = walkingGoal,
                        onWalkingGoalChange = { walkingGoal = it },
                        cyclingGoal = cyclingGoal,
                        onCyclingGoalChange = { cyclingGoal = it }
                    )
                }
                
                // Metas de Energia
                item {
                    EnergyGoalsSection(
                        energyGoal = energyGoal,
                        onEnergyGoalChange = { energyGoal = it },
                        ledGoal = ledGoal,
                        onLedGoalChange = { ledGoal = it },
                        solarGoal = solarGoal,
                        onSolarGoalChange = { solarGoal = it }
                    )
                }
                
                // Metas de Alimentação
                item {
                    FoodGoalsSection(
                        foodGoal = foodGoal,
                        onFoodGoalChange = { foodGoal = it },
                        localFoodGoal = localFoodGoal,
                        onLocalFoodGoalChange = { localFoodGoal = it },
                        wasteReductionGoal = wasteReductionGoal,
                        onWasteReductionGoalChange = { wasteReductionGoal = it }
                    )
                }
                
                // Metas de Resíduos
                item {
                    WasteGoalsSection(
                        recyclingGoal = recyclingGoal,
                        onRecyclingGoalChange = { recyclingGoal = it },
                        compostingGoal = compostingGoal,
                        onCompostingGoalChange = { compostingGoal = it },
                        plasticReductionGoal = plasticReductionGoal,
                        onPlasticReductionGoalChange = { plasticReductionGoal = it }
                    )
                }
                
                // Metas de Água
                item {
                    WaterGoalsSection(
                        waterGoal = waterGoal,
                        onWaterGoalChange = { waterGoal = it },
                        rainwaterGoal = rainwaterGoal,
                        onRainwaterGoalChange = { rainwaterGoal = it },
                        showerGoal = showerGoal,
                        onShowerGoalChange = { showerGoal = it }
                    )
                }
                
                // Metas de Plantação
                item {
                    PlantingGoalsSection(
                        treePlantingGoal = treePlantingGoal,
                        onTreePlantingGoalChange = { treePlantingGoal = it },
                        gardenGoal = gardenGoal,
                        onGardenGoalChange = { gardenGoal = it },
                        communityGoal = communityGoal,
                        onCommunityGoalChange = { communityGoal = it }
                    )
                }
                
                // Botão Salvar
                item {
                    SaveButton(
                        onSaveClick = {
                            isLoading = true
                            // Simula um delay de 2 segundos para mostrar o carregamento
                            // Depois retorna para a tela de perfil
                            coroutineScope.launch {
                                delay(2000)
                                onBackClick()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileConfigSection(
    userName: String,
    onUserNameChange: (String) -> Unit,
    birthDate: String,
    onBirthDateChange: (String) -> Unit,
    userHeight: String,
    onUserHeightChange: (String) -> Unit
) {
    ConfigSectionCard(
        title = "Perfil do Usuário",
        icon = Icons.Default.Person,
        color = EcoGreen
    ) {
        ConfigTextField(
            label = "Nome",
            value = userName,
            onValueChange = onUserNameChange,
            icon = Icons.Default.Person,
            placeholder = "Digite seu nome"
        )
        
        ConfigTextField(
            label = "Data de Nascimento",
            value = birthDate,
            onValueChange = onBirthDateChange,
            icon = Icons.Default.CalendarToday,
            placeholder = "DD/MM/AAAA"
        )
        
        ConfigTextField(
            label = "Altura (cm)",
            value = userHeight,
            onValueChange = onUserHeightChange,
            icon = Icons.Default.Height,
            placeholder = "170",
            keyboardType = KeyboardType.Number
        )
    }
}

@Composable
fun TransportGoalsSection(
    transportGoal: String,
    onTransportGoalChange: (String) -> Unit,
    walkingGoal: String,
    onWalkingGoalChange: (String) -> Unit,
    cyclingGoal: String,
    onCyclingGoalChange: (String) -> Unit
) {
    ConfigSectionCard(
        title = "Metas de Transporte",
        icon = Icons.Default.DirectionsCar,
        color = EcoGreen
    ) {
        ConfigTextField(
            label = "Meta de CO₂ (kg/mês)",
            value = transportGoal,
            onValueChange = onTransportGoalChange,
            icon = Icons.Default.Eco,
            placeholder = "50",
            keyboardType = KeyboardType.Number
        )
        
        ConfigTextField(
            label = "Meta de Caminhada (km/semana)",
            value = walkingGoal,
            onValueChange = onWalkingGoalChange,
            placeholder = "5",
            icon = Icons.AutoMirrored.Filled.DirectionsWalk,
            keyboardType = KeyboardType.Number
        )
        
        ConfigTextField(
            label = "Meta de Bicicleta (km/semana)",
            value = cyclingGoal,
            onValueChange = onCyclingGoalChange,
            placeholder = "10",
            keyboardType = KeyboardType.Number,
            icon = Icons.AutoMirrored.Filled.DirectionsBike
        )
    }
}

@Composable
fun EnergyGoalsSection(
    energyGoal: String,
    onEnergyGoalChange: (String) -> Unit,
    ledGoal: String,
    onLedGoalChange: (String) -> Unit,
    solarGoal: String,
    onSolarGoalChange: (String) -> Unit
) {
    ConfigSectionCard(
        title = "Metas de Energia",
        icon = Icons.Default.EnergySavingsLeaf,
        color = EcoGreenLight
    ) {
        ConfigTextField(
            label = "Meta de Economia (kWh/mês)",
            value = energyGoal,
            onValueChange = onEnergyGoalChange,
            icon = Icons.Default.Power,
            placeholder = "200",
            keyboardType = KeyboardType.Number
        )
        
        ConfigTextField(
            label = "Meta de Lâmpadas LED",
            value = ledGoal,
            onValueChange = onLedGoalChange,
            icon = Icons.Default.Lightbulb,
            placeholder = "15",
            keyboardType = KeyboardType.Number
        )
        
        ConfigTextField(
            label = "Meta de Energia Solar (kWh/mês)",
            value = solarGoal,
            onValueChange = onSolarGoalChange,
            icon = Icons.Default.WbSunny,
            placeholder = "100",
            keyboardType = KeyboardType.Number
        )
    }
}

@Composable
fun FoodGoalsSection(
    foodGoal: String,
    onFoodGoalChange: (String) -> Unit,
    localFoodGoal: String,
    onLocalFoodGoalChange: (String) -> Unit,
    wasteReductionGoal: String,
    onWasteReductionGoalChange: (String) -> Unit
) {
    ConfigSectionCard(
        title = "Metas de Alimentação",
        icon = Icons.Default.Restaurant,
        color = EcoGreenAccent
    ) {
        ConfigTextField(
            label = "Meta de Redução de CO₂ (%)",
            value = foodGoal,
            onValueChange = onFoodGoalChange,
            icon = Icons.Default.Eco,
            placeholder = "80",
            keyboardType = KeyboardType.Number
        )
        
        ConfigTextField(
            label = "Meta de Alimentos Locais (%)",
            value = localFoodGoal,
            onValueChange = onLocalFoodGoalChange,
            icon = Icons.Default.LocalGroceryStore,
            placeholder = "60",
            keyboardType = KeyboardType.Number
        )
        
        ConfigTextField(
            label = "Meta de Redução de Desperdício (%)",
            value = wasteReductionGoal,
            onValueChange = onWasteReductionGoalChange,
            icon = Icons.Default.NoFood,
            placeholder = "90",
            keyboardType = KeyboardType.Number
        )
    }
}

@Composable
fun WasteGoalsSection(
    recyclingGoal: String,
    onRecyclingGoalChange: (String) -> Unit,
    compostingGoal: String,
    onCompostingGoalChange: (String) -> Unit,
    plasticReductionGoal: String,
    onPlasticReductionGoalChange: (String) -> Unit
) {
    ConfigSectionCard(
        title = "Metas de Resíduos",
        icon = Icons.Default.Recycling,
        color = EcoGreen
    ) {
        ConfigTextField(
            label = "Meta de Reciclagem (%)",
            value = recyclingGoal,
            onValueChange = onRecyclingGoalChange,
            icon = Icons.Default.Recycling,
            placeholder = "95",
            keyboardType = KeyboardType.Number
        )
        
        ConfigTextField(
            label = "Meta de Compostagem (%)",
            value = compostingGoal,
            onValueChange = onCompostingGoalChange,
            icon = Icons.Default.Grass,
            placeholder = "70",
            keyboardType = KeyboardType.Number
        )
        
        ConfigTextField(
            label = "Meta de Redução de Plásticos (%)",
            value = plasticReductionGoal,
            onValueChange = onPlasticReductionGoalChange,
            icon = Icons.Default.DeleteOutline,
            placeholder = "85",
            keyboardType = KeyboardType.Number
        )
    }
}

@Composable
fun WaterGoalsSection(
    waterGoal: String,
    onWaterGoalChange: (String) -> Unit,
    rainwaterGoal: String,
    onRainwaterGoalChange: (String) -> Unit,
    showerGoal: String,
    onShowerGoalChange: (String) -> Unit
) {
    ConfigSectionCard(
        title = "Metas de Água",
        icon = Icons.Default.WaterDrop,
        color = EcoGreenLight
    ) {
        ConfigTextField(
            label = "Meta de Economia (litros/dia)",
            value = waterGoal,
            onValueChange = onWaterGoalChange,
            icon = Icons.Default.Water,
            placeholder = "120",
            keyboardType = KeyboardType.Number
        )
        
        ConfigTextField(
            label = "Meta de Reutilização de Água da Chuva (litros/mês)",
            value = rainwaterGoal,
            onValueChange = onRainwaterGoalChange,
            icon = Icons.Default.WaterDrop,
            placeholder = "50",
            keyboardType = KeyboardType.Number
        )
        
        ConfigTextField(
            label = "Meta de Tempo de Chuveiro (minutos)",
            value = showerGoal,
            onValueChange = onShowerGoalChange,
            icon = Icons.Default.Shower,
            placeholder = "5",
            keyboardType = KeyboardType.Number
        )
    }
}

@Composable
fun PlantingGoalsSection(
    treePlantingGoal: String,
    onTreePlantingGoalChange: (String) -> Unit,
    gardenGoal: String,
    onGardenGoalChange: (String) -> Unit,
    communityGoal: String,
    onCommunityGoalChange: (String) -> Unit
) {
    ConfigSectionCard(
        title = "Metas de Plantação",
        icon = Icons.Default.Park,
        color = EcoGreenAccent
    ) {
        ConfigTextField(
            label = "Meta de Árvores Plantadas (por ano)",
            value = treePlantingGoal,
            onValueChange = onTreePlantingGoalChange,
            icon = Icons.Default.Park,
            placeholder = "12",
            keyboardType = KeyboardType.Number
        )
        
        ConfigTextField(
            label = "Meta de Horta (m²)",
            value = gardenGoal,
            onValueChange = onGardenGoalChange,
            icon = Icons.Default.Grass,
            placeholder = "8",
            keyboardType = KeyboardType.Number
        )
        
        ConfigTextField(
            label = "Meta de Participação Comunitária (horas/mês)",
            value = communityGoal,
            onValueChange = onCommunityGoalChange,
            icon = Icons.Default.Group,
            placeholder = "20",
            keyboardType = KeyboardType.Number
        )
    }
}

@Composable
fun ConfigSectionCard(
    title: String,
    icon: ImageVector,
    color: Color,
    content: @Composable () -> Unit
) {
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
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    color = EcoTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            content()
        }
    }
}

@Composable
fun ConfigTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = EcoTextSecondary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = EcoTextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = EcoTextSecondary
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EcoGreen,
                unfocusedBorderColor = EcoTextSecondary.copy(alpha = 0.5f),
                focusedTextColor = EcoTextPrimary,
                unfocusedTextColor = EcoTextPrimary
            ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SaveButton(
    onSaveClick: () -> Unit = {}
) {
    Button(
        onClick = onSaveClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = EcoGreen
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = "Salvar",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Salvar Configurações",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Bolinha de carregamento verde
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = EcoGreen,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Ícone de carregamento
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp),
                    color = Color.White,
                    strokeWidth = 4.dp
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Salvando configurações...",
                color = EcoTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Aguarde um momento",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddConfigScreenPreview() {
    EcoTrackTheme {
        AddConfigScreen()
    }
}
