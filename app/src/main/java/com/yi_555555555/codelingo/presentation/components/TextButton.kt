package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yi_555555555.codelingo.presentation.ui.theme.CodeLingoTheme

@Composable
fun TextButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.colorScheme.secondaryContainer
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      modifier = Modifier
        .padding(8.dp)
        .clickable(
          onClick = onClick
        ),
      text = text,
      style = MaterialTheme.typography.bodyMedium,
      color = color
    )
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