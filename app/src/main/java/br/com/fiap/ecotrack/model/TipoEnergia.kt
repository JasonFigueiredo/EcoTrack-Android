package br.com.fiap.ecotrack.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.Microwave
import androidx.compose.material.icons.filled.Shower
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector

data class TipoEnergia(
    val id: String,
    val nome: String,
    val icone: ImageVector,
    val cor: androidx.compose.ui.graphics.Color,
    val co2PorKwh: Double, // kg CO2 por kWh
    val climatiqActivityId: String, // ID para usar na API do Climatiq
    val descricao: String,
    val potenciaMedia: Double // Watts - potência média do aparelho
)

// Tipos de energia disponíveis
enum class TipoEnergiaEnum(val activityId: String, val nomeExibicao: String) {
    // Eletrodomésticos
    AR_CONDICIONADO("electricity-energy_source_grid-electricity_type_na-country_BR", "Ar Condicionado"),
    GELADEIRA("electricity-energy_source_grid-electricity_type_na-country_BR", "Geladeira"),
    TELEVISAO("electricity-energy_source_grid-electricity_type_na-country_BR", "Televisão"),
    COMPUTADOR("electricity-energy_source_grid-electricity_type_na-country_BR", "Computador"),
    LAVADORA("electricity-energy_source_grid-electricity_type_na-country_BR", "Lavadora"),
    MICROONDAS("electricity-energy_source_grid-electricity_type_na-country_BR", "Microondas"),
    FERRO_DE_PASSAR("electricity-energy_source_grid-electricity_type_na-country_BR", "Ferro de Passar"),
    CHUVEIRO_ELETRICO("electricity-energy_source_grid-electricity_type_na-country_BR", "Chuveiro Elétrico"),
    
    // Iluminação
    LUZES_LED("electricity-energy_source_grid-electricity_type_na-country_BR", "Luzes LED"),
    LUZES_INCANDESCENTES("electricity-energy_source_grid-electricity_type_na-country_BR", "Luzes Incandescentes"),
    
    // Energia renovável
    ENERGIA_SOLAR("electricity-energy_source_solar-electricity_type_na-country_BR", "Energia Solar"),
    ENERGIA_EOLICA("electricity-energy_source_wind-electricity_type_na-country_BR", "Energia Eólica")
}

// Função para obter os tipos de energia disponíveis
// Valores baseados em dados reais da API Climatiq e estudos do setor elétrico brasileiro
fun getTiposEnergiaDisponiveis(): List<TipoEnergia> {
    return listOf(
        // Eletrodomésticos
        TipoEnergia(
            id = "ar_condicionado",
            nome = "Ar Condicionado",
            icone = androidx.compose.material.icons.Icons.Default.AcUnit,
            cor = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PorKwh = 0.118, // kg CO₂/kWh - baseado no mix energético brasileiro
            climatiqActivityId = TipoEnergiaEnum.AR_CONDICIONADO.activityId,
            descricao = "Sistema de climatização residencial",
            potenciaMedia = 2000.0 // 2kW
        ),
        TipoEnergia(
            id = "geladeira",
            nome = "Geladeira",
            icone = androidx.compose.material.icons.Icons.Default.Kitchen,
            cor = br.com.fiap.ecotrack.ui.theme.EcoGreenLight,
            co2PorKwh = 0.118,
            climatiqActivityId = TipoEnergiaEnum.GELADEIRA.activityId,
            descricao = "Refrigerador doméstico",
            potenciaMedia = 150.0 // 150W
        ),
        TipoEnergia(
            id = "televisao",
            nome = "Televisão",
            icone = androidx.compose.material.icons.Icons.Default.Tv,
            cor = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
            co2PorKwh = 0.118,
            climatiqActivityId = TipoEnergiaEnum.TELEVISAO.activityId,
            descricao = "Televisão LED/LCD",
            potenciaMedia = 100.0 // 100W
        ),
        TipoEnergia(
            id = "computador",
            nome = "Computador",
            icone = androidx.compose.material.icons.Icons.Default.Computer,
            cor = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PorKwh = 0.118,
            climatiqActivityId = TipoEnergiaEnum.COMPUTADOR.activityId,
            descricao = "Computador desktop ou notebook",
            potenciaMedia = 200.0 // 200W
        ),
        TipoEnergia(
            id = "lavadora",
            nome = "Lavadora",
            icone = androidx.compose.material.icons.Icons.Default.LocalLaundryService,
            cor = br.com.fiap.ecotrack.ui.theme.EcoGreenLight,
            co2PorKwh = 0.118,
            climatiqActivityId = TipoEnergiaEnum.LAVADORA.activityId,
            descricao = "Máquina de lavar roupas",
            potenciaMedia = 1500.0 // 1.5kW
        ),
        TipoEnergia(
            id = "microondas",
            nome = "Microondas",
            icone = androidx.compose.material.icons.Icons.Default.Microwave,
            cor = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
            co2PorKwh = 0.118,
            climatiqActivityId = TipoEnergiaEnum.MICROONDAS.activityId,
            descricao = "Forno de microondas",
            potenciaMedia = 1000.0 // 1kW
        ),
        TipoEnergia(
            id = "ferro_passar",
            nome = "Ferro de Passar",
            icone = androidx.compose.material.icons.Icons.Default.ContentCut,
            cor = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PorKwh = 0.118,
            climatiqActivityId = TipoEnergiaEnum.FERRO_DE_PASSAR.activityId,
            descricao = "Ferro elétrico para roupas",
            potenciaMedia = 1200.0 // 1.2kW
        ),
        TipoEnergia(
            id = "chuveiro_eletrico",
            nome = "Chuveiro Elétrico",
            icone = androidx.compose.material.icons.Icons.Default.Shower,
            cor = br.com.fiap.ecotrack.ui.theme.EcoGreenLight,
            co2PorKwh = 0.118,
            climatiqActivityId = TipoEnergiaEnum.CHUVEIRO_ELETRICO.activityId,
            descricao = "Chuveiro elétrico residencial",
            potenciaMedia = 5500.0 // 5.5kW
        ),
        
        // Iluminação
        TipoEnergia(
            id = "luzes_led",
            nome = "Luzes LED",
            icone = androidx.compose.material.icons.Icons.Default.Lightbulb,
            cor = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
            co2PorKwh = 0.118,
            climatiqActivityId = TipoEnergiaEnum.LUZES_LED.activityId,
            descricao = "Iluminação LED eficiente",
            potenciaMedia = 10.0 // 10W por lâmpada
        ),
        TipoEnergia(
            id = "luzes_incandescentes",
            nome = "Luzes Incandescentes",
            icone = androidx.compose.material.icons.Icons.Default.Lightbulb,
            cor = br.com.fiap.ecotrack.ui.theme.EcoGreen,
            co2PorKwh = 0.118,
            climatiqActivityId = TipoEnergiaEnum.LUZES_INCANDESCENTES.activityId,
            descricao = "Iluminação incandescente tradicional",
            potenciaMedia = 60.0 // 60W por lâmpada
        ),
        
        // Energia renovável
        TipoEnergia(
            id = "energia_solar",
            nome = "Energia Solar",
            icone = androidx.compose.material.icons.Icons.Default.WbSunny,
            cor = br.com.fiap.ecotrack.ui.theme.EcoGreenAccent,
            co2PorKwh = 0.0, // Zero emissões diretas
            climatiqActivityId = TipoEnergiaEnum.ENERGIA_SOLAR.activityId,
            descricao = "Energia solar fotovoltaica",
            potenciaMedia = 0.0 // Varia conforme instalação
        ),
        TipoEnergia(
            id = "energia_eolica",
            nome = "Energia Eólica",
            icone = androidx.compose.material.icons.Icons.Default.Air,
            cor = br.com.fiap.ecotrack.ui.theme.EcoGreenLight,
            co2PorKwh = 0.0, // Zero emissões diretas
            climatiqActivityId = TipoEnergiaEnum.ENERGIA_EOLICA.activityId,
            descricao = "Energia eólica",
            potenciaMedia = 0.0 // Varia conforme instalação
        )
    )
}
