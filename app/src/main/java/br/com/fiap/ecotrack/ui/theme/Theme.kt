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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val EcoTrackDarkColorScheme = darkColorScheme(
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

private val EcoTrackLightColorScheme = lightColorScheme(
    primary = EcoGreenDark,
    onPrimary = EcoTextPrimary,
    primaryContainer = EcoGreenLight,
    onPrimaryContainer = EcoTextOnGreen,
    secondary = EcoGreen,
    onSecondary = EcoTextOnGreen,
    secondaryContainer = EcoGreenLight,
    onSecondaryContainer = EcoTextOnGreen,
    tertiary = EcoGreenAccent,
    onTertiary = EcoTextOnGreen,
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    error = EcoError,
    onError = EcoTextPrimary,
    errorContainer = EcoError,
    onErrorContainer = EcoTextPrimary
)

@Composable
fun EcoTrackTheme(
    darkTheme: Boolean = true, // Sempre usar tema escuro para EcoTrack
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Desabilitado para manter cores personalizadas
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> EcoTrackDarkColorScheme
        else -> EcoTrackLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}