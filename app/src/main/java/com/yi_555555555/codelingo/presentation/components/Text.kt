package com.yi_555555555.codelingo.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Header(
  text: String,
  modifier: Modifier = Modifier
) {
  Text(
    modifier = modifier,
    text = text.uppercase(),
    style = MaterialTheme.typography.titleMedium,
    color = MaterialTheme.colorScheme.secondary
  )
}