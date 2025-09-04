package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
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
import androidx.compose.ui.window.Dialog
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMetaScreen(
    onBackClick: () -> Unit = {}
) {
    var selectedMeta by remember { mutableStateOf<MetaItem?>(null) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
            .padding(bottom = 30.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Metas para Redução de CO₂",
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
            Text(
                text = "Aqui estão algumas ações que você pode adotar no seu dia a dia para ajudar na redução das emissões de carbono e contribuir para um futuro mais sustentável.",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getMetaItems()) { meta ->
                    MetaCard(
                        meta = meta,
                        onClick = { selectedMeta = meta }
                    )
                }
            }
        }
    }
    
    // Popup dialog para mostrar detalhes da meta
    selectedMeta?.let { meta ->
        MetaDetailDialog(
            meta = meta,
            onDismiss = { selectedMeta = null }
        )
    }
}

@Composable
fun MetaCard(
    meta: MetaItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = meta.icon,
                contentDescription = meta.title,
                tint = meta.color,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = meta.title,
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                if (meta.subtitle.isNotEmpty()) {
                    Text(
                        text = meta.subtitle,
                        color = EcoTextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Ver detalhes",
                tint = EcoTextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun MetaDetailDialog(
    meta: MetaItem,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = EcoDarkSurface
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Header com ícone, título e botão de fechar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = meta.icon,
                            contentDescription = meta.title,
                            tint = meta.color,
                            modifier = Modifier.size(32.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Text(
                            text = meta.title,
                            color = EcoTextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = EcoTextSecondary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Conteúdo detalhado da meta
                Text(
                    text = getMetaDetails(meta.title),
                    color = EcoTextSecondary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

data class MetaItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color
)

fun getMetaItems(): List<MetaItem> {
    return listOf(
        MetaItem(
            title = "Reduzir o uso de transporte a combustão",
            subtitle = "Evitar o uso excessivo de carros e motos movidos a gasolina ou diesel.",
            icon = Icons.Default.DirectionsCar,
            color = EcoGreen
        ),
        MetaItem(
            title = "Optar por andar a pé ou de bicicleta",
            subtitle = "Priorizar deslocamentos curtos sem uso de veículos motorizados.",
            color = EcoGreenLight,
            icon = Icons.AutoMirrored.Filled.DirectionsWalk
        ),
        MetaItem(
            title = "Utilizar transporte público sempre que possível",
            subtitle = "Substituir o carro individual por ônibus, metrô ou trem.",
            icon = Icons.Default.DirectionsBus,
            color = EcoGreenAccent
        ),
        MetaItem(
            title = "Reduzir o consumo de carne vermelha",
            subtitle = "Diminuir a frequência de consumo de carnes, especialmente bovina.",
            icon = Icons.Default.Restaurant,
            color = EcoGreen
        ),
        MetaItem(
            title = "Economizar energia elétrica",
            subtitle = "Desligar luzes e eletrônicos quando não estiverem em uso. Utilizar lâmpadas LED e aparelhos eficientes.",
            icon = Icons.Default.EnergySavingsLeaf,
            color = EcoGreenLight
        ),
        MetaItem(
            title = "Separar e reciclar o lixo corretamente",
            subtitle = "Adotar práticas de coleta seletiva em casa e no trabalho.",
            icon = Icons.Default.Recycling,
            color = EcoGreenAccent
        ),
        MetaItem(
            title = "Plantar árvores e apoiar projetos ambientais",
            subtitle = "Participar de iniciativas de reflorestamento ou doar para causas ambientais.",
            icon = Icons.Default.Forest,
            color = EcoGreen
        ),
        MetaItem(
            title = "Consumir de forma consciente",
            subtitle = "Comprar apenas o necessário. Priorizar produtos duráveis, recicláveis ou reutilizáveis.",
            icon = Icons.Default.ShoppingCart,
            color = EcoGreenLight
        ),
        MetaItem(
            title = "Reduzir o uso de plásticos descartáveis",
            subtitle = "Substituir sacolas, garrafas e embalagens plásticas por alternativas reutilizáveis.",
            icon = Icons.Default.DeleteOutline,
            color = EcoGreenAccent
        ),
        MetaItem(
            title = "Utilizar energia solar quando possível",
            subtitle = "Instalar painéis solares ou usar energia solar térmica para aquecimento.",
            icon = Icons.Default.WbSunny,
            color = EcoGreen
        ),
        MetaItem(
            title = "Reduzir o desperdício de alimentos",
            subtitle = "Planejar refeições, armazenar corretamente e aproveitar sobras.",
            icon = Icons.Default.RestaurantMenu,
            color = EcoGreenLight
        ),
        MetaItem(
            title = "Usar produtos de limpeza ecológicos",
            subtitle = "Substituir produtos químicos por alternativas naturais e biodegradáveis.",
            icon = Icons.Default.CleaningServices,
            color = EcoGreenAccent
        ),
        MetaItem(
            title = "Reduzir o uso de papel",
            subtitle = "Digitalizar documentos, usar frente e verso e reciclar papel usado.",
            icon = Icons.Default.Description,
            color = EcoGreen
        ),
        MetaItem(
            title = "Comprar produtos locais e sazonais",
            subtitle = "Reduzir distância de transporte e apoiar produtores locais.",
            icon = Icons.Default.LocalGroceryStore,
            color = EcoGreenLight
        ),
        MetaItem(
            title = "Usar roupas sustentáveis",
            subtitle = "Escolher tecidos orgânicos, reciclar roupas e evitar fast fashion.",
            icon = Icons.Default.Checkroom,
            color = EcoGreenAccent
        ),
        MetaItem(
            title = "Reduzir o uso de ar condicionado",
            subtitle = "Usar ventiladores, sombrear janelas e ajustar temperatura conscientemente.",
            icon = Icons.Default.AcUnit,
            color = EcoGreen
        ),
        MetaItem(
            title = "Compostar resíduos orgânicos",
            subtitle = "Transformar restos de comida e jardim em adubo natural.",
            icon = Icons.Default.Grass,
            color = EcoGreenLight
        ),
        MetaItem(
            title = "Usar transporte compartilhado",
            subtitle = "Participar de caronas, usar aplicativos de compartilhamento de veículos.",
            icon = Icons.Default.Group,
            color = EcoGreenAccent
        ),
        MetaItem(
            title = "Reduzir o uso de água",
            subtitle = "Consertar vazamentos, usar dispositivos economizadores e reutilizar água.",
            icon = Icons.Default.WaterDrop,
            color = EcoGreen
        ),
        MetaItem(
            title = "Apoiar empresas sustentáveis",
            subtitle = "Escolher empresas com práticas ambientais responsáveis.",
            icon = Icons.Default.Business,
            color = EcoGreenLight
        )
    )
}

fun getMetaDetails(title: String): String {
    return when (title) {
        "Reduzir o uso de transporte a combustão" -> 
            "1 semana: Economiza até 15 kg de CO₂ evitando o carro por 3 dias.\n\n" +
            "1 mês: Redução de até 60 kg de CO₂ com deslocamentos alternativos.\n\n" +
            "1 ano: Você pode deixar de emitir mais de 700 kg de CO₂ — o equivalente a plantar 35 árvores."
        
        "Optar por andar a pé ou de bicicleta" -> 
            "1 semana: Até 10 km evitados de carro poupam cerca de 2 kg de CO₂.\n\n" +
            "1 mês: Redução de cerca de 10 kg de CO₂.\n\n" +
            "1 ano: Caminhar ou pedalar regularmente pode evitar a emissão de 120 kg de CO₂."
        
        "Utilizar transporte público sempre que possível" -> 
            "1 semana: Reduz em até 85% as emissões por passageiro comparado ao carro individual.\n\n" +
            "1 mês: Economia de 120-150 kg de CO₂ em trajetos diários de 20 km.\n\n" +
            "1 ano: Redução de 1.400-1.800 kg de CO₂ — equivalente a não usar o carro por 6 meses."
        
        "Reduzir o consumo de carne vermelha" -> 
            "1 semana: Uma refeição sem carne vermelha evita cerca de 3 kg de CO₂.\n\n" +
            "1 mês: Redução de até 90 kg de CO₂ com 1 dia sem carne por semana.\n\n" +
            "1 ano: Um dia por semana sem carne pode evitar 1 tonelada de CO₂."
        
        "Economizar energia elétrica" -> 
            "1 semana: Desligar eletrônicos pode economizar 2 a 5 kWh, ou 2 kg de CO₂.\n\n" +
            "1 mês: Uso consciente da energia pode reduzir 30 a 50 kg de CO₂.\n\n" +
            "1 ano: Economia de até 600 kg de CO₂ — o mesmo que deixar de usar o ar-condicionado por 3 meses."
        
        "Separar e reciclar o lixo corretamente" -> 
            "1 semana: Reciclar papel, vidro e plástico evita cerca de 3 kg de CO₂.\n\n" +
            "1 mês: Pode reduzir até 15 kg de CO₂.\n\n" +
            "1 ano: Evita mais de 180 kg de CO₂, além de reduzir volume em aterros."
        
        "Plantar árvores e apoiar projetos ambientais" -> 
            "1 árvore absorve em média 20 kg de CO₂ por ano.\n\n" +
            "1 mês: Apoiar 1 projeto de reflorestamento pode compensar 80 kg de CO₂.\n\n" +
            "1 ano: Apoiar reflorestamento regular pode compensar até 1 tonelada de CO₂."
        
        "Consumir de forma consciente" -> 
            "1 semana: Evitar compras desnecessárias pode economizar 5 a 10 kg de CO₂.\n\n" +
            "1 mês: Menos consumo = menos produção = até 40 kg de CO₂ evitados.\n\n" +
            "1 ano: Pode reduzir sua pegada em até 500 kg de CO₂ ao repensar hábitos de consumo."
        
        "Reduzir o uso de plásticos descartáveis" -> 
            "1 semana: Evitar sacolas e garrafas plásticas economiza 1-2 kg de CO₂.\n\n" +
            "1 mês: Redução de 8-15 kg de CO₂ com mudanças nos hábitos diários.\n\n" +
            "1 ano: Economia de 100-180 kg de CO₂ — equivalente a não usar o carro por 1 mês."
        
        "Utilizar energia solar quando possível" -> 
            "1 semana: Painéis solares residenciais podem gerar 50-100 kWh livres de CO₂.\n\n" +
            "1 mês: Redução de 200-400 kg de CO₂ dependendo do tamanho da instalação.\n\n" +
            "1 ano: Economia de 2.400-4.800 kg de CO₂ — compensa o uso anual de 2-4 carros."
        
        "Reduzir o desperdício de alimentos" -> 
            "1 semana: Evitar desperdício de 2 kg de comida economiza 4-6 kg de CO₂.\n\n" +
            "1 mês: Redução de 20-30 kg de CO₂ com melhor planejamento alimentar.\n\n" +
            "1 ano: Economia de 240-360 kg de CO₂ — equivalente a plantar 12-18 árvores."
        
        "Usar produtos de limpeza ecológicos" -> 
            "1 semana: Produtos naturais reduzem 0.5-1 kg de CO₂ por uso.\n\n" +
            "1 mês: Redução de 5-10 kg de CO₂ com mudança nos produtos de limpeza.\n\n" +
            "1 ano: Economia de 60-120 kg de CO₂ — além de proteger a saúde e o meio ambiente."
        
        "Reduzir o uso de papel" -> 
            "1 semana: Digitalizar e reciclar pode economizar 1-2 kg de CO₂.\n\n" +
            "1 mês: Redução de 8-15 kg de CO₂ com práticas de escritório sustentável.\n\n" +
            "1 ano: Economia de 100-180 kg de CO₂ — equivalente a não usar o carro por 1 mês."
        
        "Comprar produtos locais e sazonais" -> 
            "1 semana: Produtos locais reduzem 2-3 kg de CO₂ por compra.\n\n" +
            "1 mês: Redução de 15-25 kg de CO₂ com escolhas alimentares locais.\n\n" +
            "1 ano: Economia de 180-300 kg de CO₂ — além de apoiar a economia local."
        
        "Usar roupas sustentáveis" -> 
            "1 semana: Escolher tecidos orgânicos reduz 1-2 kg de CO₂ por peça.\n\n" +
            "1 mês: Redução de 10-20 kg de CO₂ com guarda-roupa sustentável.\n\n" +
            "1 ano: Economia de 120-240 kg de CO₂ — equivalente a não usar o carro por 1-2 meses."
        
        "Reduzir o uso de ar condicionado" -> 
            "1 semana: Ajustar temperatura economiza 3-5 kg de CO₂.\n\n" +
            "1 mês: Redução de 25-40 kg de CO₂ com uso consciente do ar condicionado.\n\n" +
            "1 ano: Economia de 300-480 kg de CO₂ — equivalente a plantar 15-24 árvores."
        
        "Compostar resíduos orgânicos" -> 
            "1 semana: Compostar 2 kg de resíduos evita 1-2 kg de CO₂.\n\n" +
            "1 mês: Redução de 8-15 kg de CO₂ com compostagem doméstica.\n\n" +
            "1 ano: Economia de 100-180 kg de CO₂ — além de produzir adubo natural."
        
        "Usar transporte compartilhado" -> 
            "1 semana: Caronas reduzem 5-8 kg de CO₂ por viagem compartilhada.\n\n" +
            "1 mês: Redução de 40-60 kg de CO₂ com uso regular de caronas.\n\n" +
            "1 ano: Economia de 480-720 kg de CO₂ — equivalente a não usar o carro por 2-3 meses."
        
        "Reduzir o uso de água" -> 
            "1 semana: Economia de água reduz 1-2 kg de CO₂ indiretamente.\n\n" +
            "1 mês: Redução de 8-15 kg de CO₂ com práticas de conservação hídrica.\n\n" +
            "1 ano: Economia de 100-180 kg de CO₂ — além de preservar recursos hídricos."
        
        "Apoiar empresas sustentáveis" -> 
            "1 semana: Escolhas conscientes reduzem 2-3 kg de CO₂ por compra.\n\n" +
            "1 mês: Redução de 15-25 kg de CO₂ com apoio a empresas responsáveis.\n\n" +
            "1 ano: Economia de 180-300 kg de CO₂ — incentivando práticas sustentáveis."
        
        else -> "Informações detalhadas não disponíveis."
    }
}

@Preview(showBackground = true)
@Composable
fun AddMetaScreenPreview() {
    EcoTrackTheme {
        AddMetaScreen()
    }
}
