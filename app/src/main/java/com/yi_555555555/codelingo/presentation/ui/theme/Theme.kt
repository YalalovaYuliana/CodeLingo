package com.yi_555555555.codelingo.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
  primaryContainer = LightViolet,
  onPrimaryContainer = White,
  secondary = DarkGrey,
  secondaryContainer = LightGrey,
  tertiaryContainer = Grey,
  tertiary = DarkViolet,
  errorContainer = LightRed,
  error = Red
)

private val LightColorScheme = lightColorScheme(
  primaryContainer = LightViolet,
  onPrimaryContainer = White,
  secondary = DarkGrey,
  secondaryContainer = LightGrey,
  tertiaryContainer = Grey,
  tertiary = DarkViolet,
  errorContainer = LightRed,
  error = Red
)

@Composable
fun CodeLingoTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    //darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}