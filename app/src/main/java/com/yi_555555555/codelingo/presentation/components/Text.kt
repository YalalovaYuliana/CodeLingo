package com.yi_555555555.codelingo.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Header(
  text: String,
  modifier: Modifier = Modifier,
  textColor: Color = MaterialTheme.colorScheme.secondary
) {
  Text(
    modifier = modifier,
    text = text.uppercase(),
    textAlign = TextAlign.Center,
    style = MaterialTheme.typography.titleMedium,
    color = textColor
  )
}