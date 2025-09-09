package br.com.fiap.ecotrack.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
private fun EcoTrackDarkColorScheme() = darkColorScheme(
    primary = EcoGreen,
    onPrimary = EcoTextOnGreen,
    primaryContainer = EcoGreenDark,
    onPrimaryContainer = EcoTextPrimary,
    secondary = EcoGreenLight,
    onSecondary = EcoTextOnGreen,
    secondaryContainer = EcoGreenDark,
    onSecondaryContainer = EcoTextPrimary,
    tertiary = EcoGreenAccent,
    onTertiary = EcoTextOnGreen,
    background = EcoDark,
    onBackground = EcoTextPrimary,
    surface = EcoDarkSurface,
    onSurface = EcoTextPrimary,
    surfaceVariant = EcoDarkSurfaceVariant,
    onSurfaceVariant = EcoTextSecondary,
    error = EcoError,
    onError = EcoTextPrimary,
    errorContainer = EcoError,
    onErrorContainer = EcoTextPrimary
)

@Composable
private fun EcoTrackLightColorScheme() = lightColorScheme(
    primary = EcoGreenDark,
    onPrimary = EcoTextPrimaryLight,
    primaryContainer = EcoGreenLight,
    onPrimaryContainer = EcoTextOnGreen,
    secondary = EcoGreen,
    onSecondary = EcoTextOnGreen,
    secondaryContainer = EcoGreenLight,
    onSecondaryContainer = EcoTextOnGreen,
    tertiary = EcoGreenAccent,
    onTertiary = EcoTextOnGreen,
    background = EcoLight,
    onBackground = EcoTextPrimaryLight,
    surface = EcoLightSurface,
    onSurface = EcoTextPrimaryLight,
    surfaceVariant = EcoLightSurfaceVariant,
    onSurfaceVariant = EcoTextSecondaryLight,
    error = EcoError,
    onError = EcoTextPrimaryLight,
    errorContainer = EcoError,
    onErrorContainer = EcoTextPrimaryLight
)

@Composable
fun EcoTrackTheme(
    darkTheme: Boolean = ThemeState.isDarkTheme, // Usar estado dinâmico do tema
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Desabilitado para manter cores personalizadas
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> EcoTrackDarkColorScheme()
        else -> EcoTrackLightColorScheme()
    }

    CompositionLocalProvider(LocalDynamicColors provides DynamicColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}