package com.yi_555555555.codelingo.presentation.mappers

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.yi_555555555.codelingo.domain.model.UserLevel

@Composable
fun UserLevel.getBackgroundColor(): Color {
  return if (this.isComplete) {
    MaterialTheme.colorScheme.outline
  } else MaterialTheme.colorScheme.primaryContainer
}