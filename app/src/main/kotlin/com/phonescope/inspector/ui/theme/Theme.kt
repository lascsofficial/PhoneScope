package com.phonescope.inspector.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ═══════════════════════════════════════════
// PhoneScope Theme — OLED-Black, Always Dark
// ═══════════════════════════════════════════

private val PhoneScopeDarkColorScheme = darkColorScheme(
    primary = PrimaryCyan,
    onPrimary = Black,
    primaryContainer = PrimaryCyanDim,
    onPrimaryContainer = Color.White,

    secondary = SecondaryAmber,
    onSecondary = Black,
    secondaryContainer = SecondaryAmberDim,
    onSecondaryContainer = Color.White,

    tertiary = Success,
    onTertiary = Black,

    background = Black,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,

    error = Error,
    onError = Black,
    errorContainer = Critical,
    onErrorContainer = Color.White,

    outline = PanelGlassBorder,
    outlineVariant = PanelGlass,

    inverseSurface = Color.White,
    inverseOnSurface = Black,
    inversePrimary = PrimaryCyanDim,

    surfaceTint = PrimaryCyan,
    scrim = Color(0xCC000000),
)

/**
 * Extended color scheme with PhoneScope-specific colors
 * not covered by Material 3's standard color slots.
 */
data class PhoneScopeColorScheme(
    val background: Color = Black,
    val surface: Color = Surface,
    val surfaceElevated: Color = SurfaceElevated,
    val panelGlass: Color = PanelGlass,
    val panelGlassBorder: Color = PanelGlassBorder,
    val primaryCyan: Color = PrimaryCyan,
    val primaryCyanGlow: Color = PrimaryCyanGlow,
    val secondaryAmber: Color = SecondaryAmber,
    val success: Color = Success,
    val warning: Color = Warning,
    val error: Color = Error,
    val critical: Color = Critical,
    val textPrimary: Color = TextPrimary,
    val textSecondary: Color = TextSecondary,
    val textTertiary: Color = TextTertiary,
    val navBarBackground: Color = NavBarBackground,
    val navBarIndicator: Color = NavBarIndicator,
)

private val LocalPhoneScopeColors = staticCompositionLocalOf { PhoneScopeColorScheme() }

object PhoneScopeTheme {
    val colors: PhoneScopeColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalPhoneScopeColors.current
}

@Composable
fun PhoneScopeTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalPhoneScopeColors provides PhoneScopeColorScheme()
    ) {
        MaterialTheme(
            colorScheme = PhoneScopeDarkColorScheme,
            typography = PhoneScopeTypography,
            shapes = PhoneScopeShapes,
            content = content
        )
    }
}
