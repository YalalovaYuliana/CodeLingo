package com.yi_555555555.codelingo.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  // primaryContainer - был LightViolet, в тёмной теме делаем темнее
  primaryContainer = DarkViolet,
  onPrimaryContainer = LightViolet,  // на тёмном фоне светлый текст

  // secondary - был DarkGrey, в тёмной теме используем светлый
  secondary = LightGrey2,
  secondaryContainer = DarkGrey,  // инвертируем контейнер

  // tertiaryContainer - был Grey, делаем светлее для контраста
  tertiaryContainer = LightGrey1,
  tertiary = LightViolet,  // инвертируем tertiary

  // error цвета
  errorContainer = Red.copy(alpha = 0.2f),  // полупрозрачный красный на тёмном фоне
  error = LightRed,  // светлый красный для ошибок
  onError = Red,  // тёмный красный для текста на ошибке

  // surface и background
  surface = DarkGrey,  // тёмная поверхность
  background = Color(0xFF1A1A1A),  // чуть темнее для фона

  // outline и accent цвета
  outline = Orange,  // инвертируем yellow на orange
  inversePrimary = Yellow,  // инвертируем orange на yellow
  outlineVariant = Grey  // используем серый для вариантов
)

private val LightColorScheme = lightColorScheme(
  primaryContainer = LightViolet,
  onPrimaryContainer = White,
  secondary = DarkGrey,
  secondaryContainer = LightGrey1,
  tertiaryContainer = Grey,
  tertiary = DarkViolet,
  errorContainer = LightRed,
  error = Red,
  onError = RedShadow,
  surface = White,
  background = White,
  outline = Yellow,
  inversePrimary = Orange,
  outlineVariant = LightGrey2
)

@Composable
fun CodeLingoTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}