package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yi_555555555.codelingo.presentation.ui.theme.CodeLingoTheme

@Composable
fun InputTextField(
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  placeholder: String? = null,
  errorMessage: String? = null,
  singleLine: Boolean = true,
  readOnly: Boolean = false,
  keyboardOptions: KeyboardOptions = KeyboardOptions(),
  visualTransformation: VisualTransformation = VisualTransformation.None,
  trailingIcon: (@Composable () -> Unit)? = null
) {
  Column {
    OutlinedTextField(
      modifier = modifier
        .fillMaxWidth()
        .border(
          width = 3.dp,
          color = if (errorMessage != null) {
            MaterialTheme.colorScheme.error
          } else {
            MaterialTheme.colorScheme.secondaryContainer
          },
          shape = RoundedCornerShape(13.dp)
        ),
      singleLine = singleLine,
      readOnly = readOnly,
      keyboardOptions = keyboardOptions,
      visualTransformation = visualTransformation,
      value = value,
      onValueChange = onValueChange,
      placeholder = {
        placeholder?.let {
          Text(
            text = placeholder,
            style = MaterialTheme.typography.labelMedium
          )
        }
      },
      isError = errorMessage != null,
      textStyle = MaterialTheme.typography.labelMedium,
      trailingIcon = trailingIcon,
      colors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.secondaryContainer,
        focusedPlaceholderColor = MaterialTheme.colorScheme.tertiaryContainer,
        unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
        focusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
        unfocusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
        focusedTextColor = MaterialTheme.colorScheme.tertiaryContainer,
        errorTextColor = MaterialTheme.colorScheme.error,
        errorPlaceholderColor = MaterialTheme.colorScheme.error,
        errorContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        errorTrailingIconColor = MaterialTheme.colorScheme.error,
        focusedTrailingIconColor = MaterialTheme.colorScheme.tertiaryContainer,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.tertiaryContainer
      ),
      shape = RoundedCornerShape(13.dp)
    )
    errorMessage?.let {
      VSpacer(4.dp)
      Text(
        text = errorMessage,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.error
      )
    }
  }
}

@Preview
@Composable
private fun TextFieldPreview() {
  CodeLingoTheme {
    InputTextField(
      value = "Текст в текстовом поле",
      onValueChange = {}
    )
  }
}

