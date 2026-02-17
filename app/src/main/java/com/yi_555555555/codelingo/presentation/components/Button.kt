package com.yi_555555555.codelingo.presentation.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
  modifier: Modifier = Modifier,
  text: String,
  onClick: () -> Unit,
  isLoading: Boolean = false
) {
  Button(
    modifier = modifier.heightIn(min = 50.dp),
    onClick = onClick,
    enabled = !isLoading,
    shape = RoundedCornerShape(13.dp),
    elevation = ButtonDefaults.buttonElevation(
      3.dp
    ),
    colors = ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer,
      disabledContainerColor = MaterialTheme.colorScheme.primaryContainer
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
}