package br.com.fiap.ecotrack.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object ThemeState {
    private var _isDarkTheme by mutableStateOf(true)
    val isDarkTheme: Boolean get() = _isDarkTheme
    
    fun toggleTheme() {
        _isDarkTheme = !_isDarkTheme
    }
    
    fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme = isDark
    }
}

// Cores dinâmicas baseadas no tema
object DynamicColors {
    val background: Color
        @Composable get() = if (ThemeState.isDarkTheme) EcoDark else EcoLight
    
    val surface: Color
        @Composable get() = if (ThemeState.isDarkTheme) EcoDarkSurface else EcoLightSurface
    
    val surfaceVariant: Color
        @Composable get() = if (ThemeState.isDarkTheme) EcoDarkSurfaceVariant else EcoLightSurfaceVariant
    
    val textPrimary: Color
        @Composable get() = if (ThemeState.isDarkTheme) EcoTextPrimary else EcoTextPrimaryLight
    
    val textSecondary: Color
        @Composable get() = if (ThemeState.isDarkTheme) EcoTextSecondary else EcoTextSecondaryLight
    
    val textOnGreen: Color
        @Composable get() = EcoTextOnGreen // Sempre preto para contraste com verde
    
    val green: Color
        @Composable get() = EcoGreen // Verde sempre igual
    
    val greenLight: Color
        @Composable get() = EcoGreenLight // Verde claro sempre igual
    
    val greenDark: Color
        @Composable get() = EcoGreenDark // Verde escuro sempre igual
    
    val greenAccent: Color
        @Composable get() = EcoGreenAccent // Verde accent sempre igual
    
    val error: Color
        @Composable get() = EcoError // Erro sempre igual
    
    val success: Color
        @Composable get() = EcoSuccess // Sucesso sempre igual
    
    val warning: Color
        @Composable get() = EcoWarning // Aviso sempre igual
}

// LocalCompositionProvider para acesso às cores dinâmicas
val LocalDynamicColors = staticCompositionLocalOf { DynamicColors }
