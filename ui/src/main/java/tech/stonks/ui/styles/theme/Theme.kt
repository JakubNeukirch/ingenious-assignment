package tech.stonks.ui.styles.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val PRIMARY = Color(0xff0062ff)
private val SECONDARY = Color(0xffb519a8)

private val DarkColorScheme = darkColorScheme(
    primary = PRIMARY,
    secondary = SECONDARY,
    tertiary = Pink80,
    background = Color(0xFF00060f),
    surface = Color(0x199bb5de),
)

private val LightColorScheme = lightColorScheme(
    primary = PRIMARY,
    secondary = SECONDARY,
    tertiary = Pink40,
    background = Color(0xFFf0f6ff),
    surface = Color(0xFF9bb5de),
)

@Composable
fun GithubUsersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
