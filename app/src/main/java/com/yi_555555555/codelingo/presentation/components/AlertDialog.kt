package com.yi_555555555.codelingo.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryAlertDialog(
  visible: Boolean,
  title: String?,
  description: String?,
  confirmText: String,
  dismissText: String,
  onDismissClick: () -> Unit,
  onConfirmClick: () -> Unit
) {
  if (visible) {
    AlertDialog(
      containerColor = MaterialTheme.colorScheme.background,
      onDismissRequest = onDismissClick,
      title = {
        title?.let {
          Text(
            text = title,
            style = MaterialTheme.typography.titleSmall
          )
        }
      },
      text = {
        description?.let {
          Text(description)
        }
      },
      confirmButton = {
        PrimaryButton(
          text = confirmText,
          isError = true,
          onClick = onConfirmClick,
          minHeight = 0.dp
        )
      },
      dismissButton = {
        PrimaryButton(
          text = dismissText,
          onClick = onDismissClick,
          minHeight = 0.dp,
          isSecondaryButton = true
        )
      }
    )
  }
}