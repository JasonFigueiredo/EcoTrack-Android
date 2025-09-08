package br.com.fiap.ecotrack.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.model.*
import br.com.fiap.ecotrack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaResultadosEnergia(
    comparacaoEnergia: ComparacaoEmissaoEnergia,
    onVoltarClick: () -> Unit = {},
    onCalcularNovo: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDark)
            .padding(bottom = 30.dp, top = 30.dp)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Resultados de Energia",
                    color = EcoTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onVoltarClick) {
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
        
        // Conteúdo com scroll
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Resultado principal
                MainEnergyResultCard(comparacaoEnergia.energiaSelecionada)
            }
            
            item {
                // Informações Selecionadas
                InformacoesSelecionadasCard(comparacaoEnergia.energiaSelecionada)
            }
            
            // Custo Estimado
            if (comparacaoEnergia.energiaSelecionada.custoEstimado != null) {
                item {
                    CustoEstimadoCard(comparacaoEnergia.energiaSelecionada)
                }
            }
            
            item {
                // Dicas específicas do aparelho
                EnergyTipsCard(comparacaoEnergia.energiaSelecionada.tipoEnergia)
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            item {
                // Botões de navegação
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Botão Voltar
                    Button(
                        onClick = onVoltarClick,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EcoDarkSurface,
                            contentColor = EcoTextPrimary
                        ),
                        border = BorderStroke(1.dp, EcoGreen)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Voltar")
                    }
                    
                    // Botão Novo Cálculo
                    Button(
                        onClick = onCalcularNovo,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EcoGreen,
                            contentColor = EcoTextOnGreen
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Novo Cálculo",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Novo Cálculo")
                    }
                }
            }
        }
    }
}

@Composable
fun MainEnergyResultCard(resultado: ResultadoEmissaoEnergia) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoDarkSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
       Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .wrapContentHeight(Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ícone do aparelho
            Icon(
                imageVector = resultado.tipoEnergia.icone,
                contentDescription = resultado.tipoEnergia.nome,
                tint = resultado.tipoEnergia.cor,
                modifier = Modifier.size(48.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = resultado.tipoEnergia.nome,
                color = EcoTextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Período: ${resultado.periodo.displayName}",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Emissões de CO2
            Text(
                text = "Emissões de CO₂",
                color = EcoTextSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${String.format("%.2f", resultado.co2Emissoes)} ${resultado.co2Unidade}",
                color = EcoGreen,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Barra de impacto
            val impactLevel = when {
                resultado.co2Emissoes < 0.5 -> "Baixo"
                resultado.co2Emissoes < 2.0 -> "Médio"
                else -> "Alto"
            }
            
            val impactColor = when (impactLevel) {
                "Baixo" -> EcoGreen
                "Médio" -> EcoWarning
                else -> EcoError
            }
            
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = impactColor.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when (impactLevel) {
                            "Baixo" -> Icons.Default.CheckCircle
                            "Médio" -> Icons.Default.Warning
                            else -> Icons.Default.Error
                        },
                        contentDescription = "Impacto",
                        tint = impactColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Impacto: $impactLevel",
                        color = impactColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}



@Composable
fun InformacoesSelecionadasCard(resultado: ResultadoEmissaoEnergia) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoGreen.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Informações",
                    tint = EcoGreen,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Informações Selecionadas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = EcoGreen
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Aparelho: ${resultado.tipoEnergia.nome}",
                fontSize = 14.sp,
                color = EcoTextPrimary
            )
            Text(
                text = "Horas: ${String.format("%.1f", resultado.horasUso)} ${resultado.periodo.displayName.lowercase()}",
                fontSize = 14.sp,
                color = EcoTextPrimary
            )
            Text(
                text = "Potência: ${resultado.tipoEnergia.potenciaMedia}W",
                fontSize = 14.sp,
                color = EcoTextPrimary
            )
            Text(
                text = "Consumo estimado: ${String.format("%.2f", resultado.consumoKwh)} kWh",
                fontSize = 14.sp,
                color = EcoGreenAccent,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun CustoEstimadoCard(resultado: ResultadoEmissaoEnergia) {
    val custo = resultado.custoEstimado ?: return
    val estado = resultado.estadoSelecionado ?: "SP"
    val tipoConsumidor = resultado.tipoConsumidor ?: TipoConsumidor.RESIDENCIAL
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EcoGreenAccent.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AttachMoney,
                    contentDescription = "Custo",
                    tint = EcoGreenAccent,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Custo Estimado",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = EcoGreenAccent
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Valor do custo
            Text(
                text = "R$ ${String.format("%.2f", custo)}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = EcoGreenAccent
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Informações adicionais
            Text(
                text = "Para ${String.format("%.2f", resultado.consumoKwh)} kWh",
                fontSize = 14.sp,
                color = EcoTextSecondary
            )
            
            Text(
                text = "Estado: $estado | Tipo: ${tipoConsumidor.displayName}",
                fontSize = 12.sp,
                color = EcoTextSecondary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Tarifa por kWh
            val tarifa = getTarifaPorEstado(estado, tipoConsumidor)
            tarifa?.let {
                val tarifaPorKwh = when (tipoConsumidor) {
                    TipoConsumidor.RESIDENCIAL -> it.tarifaResidencial
                    TipoConsumidor.COMERCIAL -> it.tarifaComercial
                    TipoConsumidor.INDUSTRIAL -> it.tarifaIndustrial
                }
                
                Text(
                    text = "Tarifa: R$ ${String.format("%.3f", tarifaPorKwh)}/kWh",
                    fontSize = 12.sp,
                    color = EcoTextSecondary
                )
                
                Text(
                    text = "Distribuidora: ${it.distribuidora}",
                    fontSize = 12.sp,
                    color = EcoTextSecondary
                )
            }
        }
    }
}

@Composable
fun EnergyTipsCard(tipoEnergia: TipoEnergia) {
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
                    imageVector = Icons.Default.Eco,
                    contentDescription = "Dicas",
                    tint = EcoGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Dicas para ${tipoEnergia.nome}",
                    color = EcoTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val dicas = getDicasEspecificas(tipoEnergia.id)
            
            dicas.forEach { dica ->
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = "Dica",
                        tint = EcoGreenAccent,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = dica,
                        color = EcoTextSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

private fun getDicasEspecificas(aparelhoId: String): List<String> {
    return when (aparelhoId) {
        "ar_condicionado" -> listOf(
            "Configure a temperatura para 24°C ou mais",
            "Desligue ao sair da sala ou de casa",
            "Use o modo de circulação de ar",
            "Ative o modo economia de energia",
            "Mantenha portas e janelas fechadas",
            "Limpe os filtros regularmente"
        )
        "geladeira" -> listOf(
            "Mantenha longe de fontes de calor",
            "Não deixe a porta aberta por muito tempo",
            "Organize os alimentos para facilitar o acesso",
            "Verifique se a borracha da porta está vedando",
            "Descongele regularmente se não for frost-free",
            "Não coloque alimentos quentes diretamente"
        )
        "televisao" -> listOf(
            "Desligue completamente quando não estiver assistindo",
            "Use o modo economia de energia",
            "Ajuste o brilho conforme o ambiente",
            "Desconecte da tomada se ficar muito tempo fora",
            "Evite deixar em standby por longos períodos",
            "Use timer para desligar automaticamente"
        )
        "computador" -> listOf(
            "Configure para hibernar após inatividade",
            "Desligue o monitor quando não estiver usando",
            "Use modo economia de energia",
            "Feche programas desnecessários",
            "Desconecte periféricos não utilizados",
            "Mantenha o sistema atualizado"
        )
        "lavadora" -> listOf(
            "Use sempre com carga completa",
            "Prefira água fria ou morna",
            "Use ciclos mais curtos quando possível",
            "Seque as roupas no varal",
            "Limpe o filtro regularmente",
            "Use detergente na quantidade recomendada"
        )
        "microondas" -> listOf(
            "Use para aquecer pequenas porções",
            "Cubra os alimentos para reter o calor",
            "Use potes adequados para microondas",
            "Desligue após o uso",
            "Limpe regularmente o interior",
            "Use para descongelar alimentos"
        )
        "ferro_passar" -> listOf(
            "Passe várias peças de uma vez",
            "Use a temperatura adequada para cada tecido",
            "Desligue quando não estiver passando",
            "Use vapor apenas quando necessário",
            "Organize as roupas por tipo de tecido",
            "Não deixe ligado desnecessariamente"
        )
        "chuveiro_eletrico" -> listOf(
            "Use o modo verão quando possível",
            "Tome banhos mais rápidos",
            "Desligue durante o ensaboamento",
            "Mantenha a temperatura controlada",
            "Instale chuveiro de baixa potência",
            "Use timer para controlar o tempo"
        )
        "luzes_led" -> listOf(
            "Desligue quando sair do ambiente",
            "Use sensores de presença",
            "Ajuste a intensidade conforme necessário",
            "Mantenha as lâmpadas limpas",
            "Use luz natural sempre que possível",
            "Instale dimmers para controle de intensidade"
        )
        "luzes_incandescentes" -> listOf(
            "Substitua por lâmpadas LED",
            "Desligue quando não estiver usando",
            "Use lâmpadas de menor potência",
            "Mantenha as lâmpadas limpas",
            "Use luz natural durante o dia",
            "Instale interruptores com timer"
        )
        "energia_solar" -> listOf(
            "Mantenha os painéis limpos",
            "Monitore a produção de energia",
            "Use durante o horário de pico solar",
            "Armazene energia em baterias",
            "Verifique a orientação dos painéis",
            "Faça manutenção regular do sistema"
        )
        "energia_eolica" -> listOf(
            "Instale em local com vento constante",
            "Monitore a produção de energia",
            "Faça manutenção regular das pás",
            "Use para complementar outras fontes",
            "Verifique a altura da instalação",
            "Armazene energia em baterias"
        )
        else -> listOf(
            "Desligue quando não estiver usando",
            "Use modo economia de energia",
            "Mantenha limpo e em bom estado",
            "Verifique se está funcionando corretamente",
            "Desconecte da tomada se não usar por muito tempo",
            "Siga as instruções do fabricante"
        )
    }
}

