package com.yi_555555555.codelingo.presentation.mappers

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.yi_555555555.codelingo.domain.model.Level

@Composable
fun Level.getBackgroundColor(enabled: Boolean): Color {
  return when {
    this.isComplete -> MaterialTheme.colorScheme.outline
    !enabled -> MaterialTheme.colorScheme.secondaryContainer
    else -> MaterialTheme.colorScheme.primaryContainer
  }
}

@Composable
fun Level.getBackgroundShadowColor(enabled: Boolean): Color {
  return when {
    this.isComplete -> MaterialTheme.colorScheme.inversePrimary
    !enabled -> MaterialTheme.colorScheme.tertiaryContainer
    else -> MaterialTheme.colorScheme.tertiary
  }
}