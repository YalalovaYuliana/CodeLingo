package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yi_555555555.codelingo.presentation.ui.theme.CodeLingoTheme

@Composable
fun PrimaryButton(
  modifier: Modifier = Modifier,
  text: String,
  onClick: () -> Unit,
  isError: Boolean = false,
  isSubtle: Boolean = false,
  isLoading: Boolean = false,
  minHeight: Dp = 50.dp
) {
  Column(
    modifier = modifier
      .clip(RoundedCornerShape(13.dp))
      .background(
        when {
          isError -> MaterialTheme.colorScheme.onError
          isSubtle -> MaterialTheme.colorScheme.tertiaryContainer
          else -> MaterialTheme.colorScheme.tertiary
        }
      )
      .clickable(
        enabled = !isLoading,
        onClick = onClick
      )
  ) {
    val backgroundColor = when {
      isError -> MaterialTheme.colorScheme.error
      isSubtle -> MaterialTheme.colorScheme.secondaryContainer
      else -> MaterialTheme.colorScheme.primaryContainer
    }
    Button(
      modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = minHeight),
      onClick = onClick,
      shape = RoundedCornerShape(13.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = backgroundColor,
        disabledContainerColor = backgroundColor
      )
    ) {
      if (isLoading) {
        CircularProgressIndicator(
          modifier = Modifier.size(20.dp),
          strokeWidth = 3.dp,
          color = MaterialTheme.colorScheme.onPrimary
        )
      } else {
        Text(
          modifier = Modifier
            .padding(vertical = 8.dp),
          text = text,
          style = MaterialTheme.typography.bodyLarge,
          color = MaterialTheme.colorScheme.onPrimary
        )
      }
    }
    VSpacer(4.dp)
  }
}

@Composable
fun OutlinedButton(
  modifier: Modifier = Modifier,
  text: String,
  onClick: () -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .border(
        width = 3.dp,
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(13.dp)
      )
      .background(
        shape = RoundedCornerShape(13.dp),
        color = Color.Transparent
      )
      .clickable(
        onClick = onClick
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      modifier = Modifier.padding(16.dp),
      text = text,
      style = MaterialTheme.typography.labelMedium,
      color = MaterialTheme.colorScheme.secondaryContainer,
      overflow = TextOverflow.Ellipsis,
      maxLines = 1
    )
  }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
  CodeLingoTheme {
    PrimaryButton(
      text = "Текст на кнопке",
      onClick = {}
    )
  }
}

@Preview
@Composable
private fun PrimaryButtonLoadingPreview() {
  CodeLingoTheme {
    PrimaryButton(
      text = "Текст на кнопке",
      onClick = {},
      isLoading = true
    )
  }
}

@Preview
@Composable
private fun PrimaryButtonErrorPreview() {
  CodeLingoTheme {
    PrimaryButton(
      text = "Текст на кнопке",
      onClick = {},
      isError = true
    )
  }
}

@Preview
@Composable
private fun PrimaryButtonSubtlePreview() {
  CodeLingoTheme {
    PrimaryButton(
      text = "Текст на кнопке",
      onClick = {},
      isSubtle = true
    )
  }
}

