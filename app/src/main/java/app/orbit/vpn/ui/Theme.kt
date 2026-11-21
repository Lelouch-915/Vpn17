package app.orbit.vpn.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/** GEAS / Code Geass crimson & charcoal palette. */
val Brand = Color(0xFFC8102E)
val BrandDark = Color(0xFFE8384F)
val Upload = Color(0xFF2F80ED)

private val DarkColors = darkColorScheme(
    primary = BrandDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF361018),
    onPrimaryContainer = Color(0xFFFFD6DD),
    secondary = Brand,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF361018),
    onSecondaryContainer = Color(0xFFFFD6DD),
    background = Color(0xFF0B0B0E),
    onBackground = Color(0xFFF2F2F7),
    surface = Color(0xFF0B0B0E),
    onSurface = Color(0xFFF2F2F7),
    surfaceContainerLowest = Color(0xFF09090C),
    surfaceContainerLow = Color(0xFF121217),
    surfaceContainer = Color(0xFF16161D),
    surfaceContainerHigh = Color(0xFF1E1E27),
    surfaceContainerHighest = Color(0xFF262632),
    surfaceVariant = Color(0xFF1E1E27),
    onSurfaceVariant = Color(0xFF9E9EA8),
    outline = Color(0xFF343442),
    outlineVariant = Color(0xFF23232D),
)

@Composable
fun OrbitTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        shapes = Shapes(
            small = RoundedCornerShape(10.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(20.dp),
        ),
        content = content,
    )
}
