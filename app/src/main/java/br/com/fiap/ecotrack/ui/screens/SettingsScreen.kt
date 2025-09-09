package br.com.fiap.ecotrack.ui.screens

/*
 * SISTEMA DE TEMA DESABILITADO
 * 
 * Para habilitar o botão de alternância entre modo claro/escuro:
 * 1. Vá na linha ~150 e descomente: ThemeState.setDarkTheme(isChecked)
 * 2. O sistema de cores dinâmicas já está pronto e funcionando
 * 3. Todas as telas usarão automaticamente o tema selecionado
 * 
 * Sistema já implementado:
 * - ThemeState.kt: Gerencia o estado do tema
 * - DynamicColors: Cores que mudam automaticamente
 * - Theme.kt: Aplica o tema globalmente
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.ecotrack.ui.theme.*
import br.com.fiap.ecotrack.ui.theme.ThemeState
import br.com.fiap.ecotrack.ui.theme.DynamicColors
import br.com.fiap.ecotrack.ui.theme.LocalDynamicColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {},
    onNotifications: () -> Unit = {},
    onPrivacy: () -> Unit = {},
    onDataManagement: () -> Unit = {},
    onLanguage: () -> Unit = {},
    onTheme: () -> Unit = {},
    onExportData: () -> Unit = {},
    onDeleteAccount: () -> Unit = {}
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    // Usar o estado global do tema
    val darkModeEnabled = ThemeState.isDarkTheme
    var dataSharingEnabled by remember { mutableStateOf(false) }
    var locationEnabled by remember { mutableStateOf(true) }
    
    val colors = LocalDynamicColors.current
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Configurações",
                    color = colors.textPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        contentDescription = "Voltar",
                        tint = colors.textPrimary,
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colors.background
            )
        )
        
        // Conteúdo com scroll suave
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 100.dp)
                .padding(bottom = 45.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Perfil do usuário
            item {
                UserProfileSettingsCard()
            }
            
            // Configurações de notificações
            item {
                SettingsSectionCard(
                    title = "Notificações",
                    icon = Icons.Default.Notifications,
                    iconColor = EcoGreen
                )
            }
            
            items(getNotificationSettings(notificationsEnabled) { notificationsEnabled = it }) { setting ->
                ModernSettingsItemCard(
                    item = setting,
                    onClick = when (setting.title) {
                        "Notificações Push" -> { {} } // Não usado para switches
                        "Lembretes de Meta" -> { {} } // Desabilitado - apenas visual
                        "Resumos Semanais" -> { {} } // Desabilitado - apenas visual
                        else -> { {} }
                    },
                    onToggleChange = when (setting.title) {
                        "Notificações Push" -> { isChecked -> notificationsEnabled = isChecked }
                        else -> null
                    }
                )
            }
            
            // Configurações de privacidade
            item {
                SettingsSectionCard(
                    title = "Privacidade e Segurança",
                    icon = Icons.Default.Security,
                    iconColor = EcoGreenLight
                )
            }
            
            items(getPrivacySettings(dataSharingEnabled, locationEnabled, { dataSharingEnabled = it }, { locationEnabled = it })) { setting ->
                ModernSettingsItemCard(
                    item = setting,
                    onClick = when (setting.title) {
                        "Compartilhar Dados" -> { {} } // Não usado para switches
                        "Localização" -> { {} } // Não usado para switches
                        "Política de Privacidade" -> { {} } // Desabilitado - apenas visual
                        "Termos de Uso" -> { {} } // Desabilitado - apenas visual
                        else -> { {} }
                    },
                    onToggleChange = when (setting.title) {
                        "Compartilhar Dados" -> { isChecked -> dataSharingEnabled = isChecked }
                        "Localização" -> { isChecked -> locationEnabled = isChecked }
                        else -> null
                    }
                )
            }
            
            // Configurações do aplicativo
            item {
                SettingsSectionCard(
                    title = "Aplicativo",
                    icon = Icons.Default.Apps,
                    iconColor = EcoGreenAccent
                )
            }
            
            items(getAppSettings(darkModeEnabled) { ThemeState.setDarkTheme(it) }) { setting ->
                ModernSettingsItemCard(
                    item = setting,
                    onClick = when (setting.title) {
                        "Modo Escuro" -> { {} } // Não usado para switches
                        "Idioma" -> { {} } // Desabilitado - apenas visual
                        "Tema" -> { {} } // Desabilitado - apenas visual
                        "Versão do App" -> { {} } // Desabilitado - apenas visual
                        else -> { {} }
                    },
                    onToggleChange = when (setting.title) {
                        "Modo Escuro" -> { isChecked -> 
                            // TODO: Para habilitar o botão de tema, descomente a linha abaixo:
                            // ThemeState.setDarkTheme(isChecked)
                        }
                        else -> null
                    }
                )
            }
            
            // Gerenciamento de dados
            item {
                SettingsSectionCard(
                    title = "Dados",
                    icon = Icons.Default.Storage,
                    iconColor = EcoWarning
                )
            }
            
            items(getDataSettings()) { setting ->
                ModernSettingsItemCard(
                    item = setting,
                    onClick = when (setting.title) {
                        "Gerenciar Dados" -> { {} } // Desabilitado - apenas visual
                        "Exportar Dados" -> { {} } // Desabilitado - apenas visual
                        "Limpar Cache" -> { {} } // Desabilitado - apenas visual
                        "Excluir Conta" -> { {} } // Desabilitado - apenas visual
                        else -> { {} }
                    }
                )
            }
        }
    }
}

@Composable
fun UserProfileSettingsCard() {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp)
                .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = EcoGreen,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar",
                    tint = EcoTextOnGreen,
                    modifier = Modifier.size(40.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "EcoUser",
                color = colors.textPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "usuario@ecotrack.com",
                color = colors.textSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* Desabilitado - apenas visual */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.green
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Editar Perfil",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun SettingsSectionCard(
    title: String,
    icon: ImageVector,
    iconColor: Color
) {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = title,
                color = colors.textPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ModernSettingsItemCard(
    item: SettingsItem,
    onClick: () -> Unit = {},
    onToggleChange: ((Boolean) -> Unit)? = null
) {
    val colors = LocalDynamicColors.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone com fundo colorido
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = item.color.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = item.color,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    color = colors.textPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                
                if (item.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.description,
                        color = colors.textSecondary,
                        fontSize = 12.sp
                    )
                }
            }
            
            // Switch ou seta
            when {
                item.isToggle -> {
                    Switch(
                        checked = item.isEnabled,
                        onCheckedChange = { isChecked ->
                            if (onToggleChange != null) {
                                onToggleChange(isChecked)
                            } else {
                                onClick()
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colors.textOnGreen,
                            checkedTrackColor = colors.green,
                            uncheckedThumbColor = colors.textSecondary,
                            uncheckedTrackColor = colors.surface
                        )
                    )
                }
                item.isDangerous -> {
                    IconButton(onClick = onClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Excluir",
                            tint = colors.error,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                else -> {
                    IconButton(onClick = onClick) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Ir para",
                            tint = colors.textSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

data class SettingsItem(
    val title: String,
    val description: String = "",
    val icon: ImageVector,
    val color: Color,
    val isToggle: Boolean = false,
    val isEnabled: Boolean = false,
    val isDangerous: Boolean = false
)

fun getNotificationSettings(
    notificationsEnabled: Boolean,
    onNotificationsChange: (Boolean) -> Unit
): List<SettingsItem> {
    return listOf(
        SettingsItem(
            title = "Notificações Push",
            description = "Receber notificações do aplicativo",
            icon = Icons.Default.Notifications,
            color = EcoGreen,
            isToggle = true,
            isEnabled = notificationsEnabled
        ),
        SettingsItem(
            title = "Lembretes de Meta",
            description = "Lembretes para atingir suas metas diárias",
            icon = Icons.Default.Flag,
            color = EcoGreenLight,
            isToggle = true,
            isEnabled = true
        ),
        SettingsItem(
            title = "Resumos Semanais",
            description = "Relatórios semanais de suas atividades",
            icon = Icons.Default.Analytics,
            color = EcoGreenAccent,
            isToggle = true,
            isEnabled = true
        )
    )
}

fun getPrivacySettings(
    dataSharingEnabled: Boolean,
    locationEnabled: Boolean,
    onDataSharingChange: (Boolean) -> Unit,
    onLocationChange: (Boolean) -> Unit
): List<SettingsItem> {
    return listOf(
        SettingsItem(
            title = "Compartilhar Dados",
            description = "Permitir compartilhamento de dados anônimos",
            icon = Icons.Default.Share,
            color = EcoGreen,
            isToggle = true,
            isEnabled = dataSharingEnabled
        ),
        SettingsItem(
            title = "Localização",
            description = "Usar localização para dados regionais",
            icon = Icons.Default.LocationOn,
            color = EcoGreenLight,
            isToggle = true,
            isEnabled = locationEnabled
        ),
        SettingsItem(
            title = "Política de Privacidade",
            description = "Visualizar nossa política de privacidade",
            icon = Icons.Default.PrivacyTip,
            color = EcoGreenAccent
        ),
        SettingsItem(
            title = "Termos de Uso",
            description = "Visualizar os termos de uso do aplicativo",
            icon = Icons.Default.Description,
            color = EcoWarning
        )
    )
}

fun getAppSettings(
    darkModeEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit
): List<SettingsItem> {
    return listOf(
        SettingsItem(
            title = "Modo Escuro",
            description = "Usar tema escuro do aplicativo",
            icon = Icons.Default.DarkMode,
            color = EcoGreen,
            isToggle = true,
            isEnabled = darkModeEnabled
        ),
        SettingsItem(
            title = "Idioma",
            description = "Português (Brasil)",
            icon = Icons.Default.Language,
            color = EcoGreenLight
        ),
        SettingsItem(
            title = "Tema",
            description = "Personalizar cores do aplicativo",
            icon = Icons.Default.Palette,
            color = EcoGreenAccent
        ),
        SettingsItem(
            title = "Versão do App",
            description = "v1.0.0 (Build 100)",
            icon = Icons.Default.Info,
            color = EcoTextSecondary
        )
    )
}

fun getDataSettings(): List<SettingsItem> {
    return listOf(
        SettingsItem(
            title = "Gerenciar Dados",
            description = "Visualizar e gerenciar seus dados",
            icon = Icons.Default.Storage,
            color = EcoGreen
        ),
        SettingsItem(
            title = "Exportar Dados",
            description = "Baixar seus dados em formato CSV",
            icon = Icons.Default.Download,
            color = EcoGreenLight
        ),
        SettingsItem(
            title = "Limpar Cache",
            description = "Remover dados temporários do aplicativo",
            icon = Icons.Default.Clear,
            color = EcoWarning
        ),
        SettingsItem(
            title = "Excluir Conta",
            description = "Excluir permanentemente sua conta",
            icon = Icons.Default.Delete,
            color = EcoError,
            isDangerous = true
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    EcoTrackTheme {
        SettingsScreen()
    }
}
