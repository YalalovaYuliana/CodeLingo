package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.presentation.ui.theme.CodeLingoTheme

@Composable
fun OptionCheckbox(
  modifier: Modifier = Modifier,
  text: String,
  selected: Boolean,
  isError: Boolean,
  onCheckedChange: () -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .border(
        width = 3.dp,
        color = if (isError) {
          MaterialTheme.colorScheme.error
        } else if (selected) {
          MaterialTheme.colorScheme.primaryContainer
        } else MaterialTheme.colorScheme.outlineVariant,
        shape = RoundedCornerShape(13.dp)
      )
      .background(
        shape = RoundedCornerShape(13.dp),
        color = Color.Transparent
      )
      .clickable(
        onClick = onCheckedChange
      )
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(24.dp)
          .border(
            width = 3.dp,
            color = if (isError) {
              MaterialTheme.colorScheme.error
            } else if (selected) {
              MaterialTheme.colorScheme.primaryContainer
            } else MaterialTheme.colorScheme.outlineVariant,
            shape = CircleShape
          )
          .background(
            shape = CircleShape,
            color = Color.Transparent
          ),
        contentAlignment = Alignment.Center
      ) {
        if (selected) {
          Icon(
            painter = painterResource(R.drawable.ic_check),
            tint = if (isError) {
              MaterialTheme.colorScheme.error
            } else MaterialTheme.colorScheme.primaryContainer,
            contentDescription = "checkbox"
          )
        }
      }
      HSpacer(16.dp)
      Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.secondary
      )
    }
  }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
  CodeLingoTheme {
    OptionCheckbox(
      text = "Текст на чекбоксе",
      onCheckedChange = {},
      selected = false,
      isError = false
    )
  }
}

