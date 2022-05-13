package org.quevedo.quevedovirtualclassrooms.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = ThemePurple,
    primaryVariant = ThemeYellow,
    secondary = ThemeGreen,
    background = ThemeBackground,
    surface = ThemeBlue,
    onPrimary = ThemeBlack,
    onSecondary = ThemeBlack,
    onBackground = ThemeBlack,
    onSurface = ThemeBlack,
    error = Color.Red
)



@Composable
fun QueVirtualClassTheme(
    content: @Composable () -> Unit
) {
    val colors = LightColorPalette


    MaterialTheme(
        colors = colors,
        typography = customTypography,
        shapes = Shapes,
        content = content
    )
}