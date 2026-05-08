package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.yi_555555555.codelingo.presentation.ui.theme.CodeLingoTheme

@Composable
fun TextButton(
  text: String,
  onClick: () -> Unit,
  color: Color = MaterialTheme.colorScheme.secondaryContainer
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    TextButton(
      onClick = onClick
    ) {
      Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = color
      )
    }
  }
}

@Preview
@Composable
private fun TextButtonPreview() {
  CodeLingoTheme {
    TextButton(
      text = "Текст на текстовой кнопке",
      onClick = {}
    )
  }
}