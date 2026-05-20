package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
  trailingIcon: (@Composable () -> Unit)? = null,
  bottomContent: (@Composable () -> Unit)? = null,
  textAlign: TextAlign? = null
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
      textStyle = MaterialTheme.typography.labelMedium.copy(
        textAlign = textAlign ?: TextAlign.Unspecified
      ),
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
    bottomContent?.let {
      bottomContent()
    }
    errorMessage?.let {
      VSpacer(4.dp)
      Text(
        modifier = modifier.padding(start = 8.dp),
        text = errorMessage,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.error
      )
    }
  }
}

@Composable
fun CodeDigitTextField(
  value: String,
  onValueChange: (String) -> Unit,
  enabled: Boolean,
  isError: Boolean,
  onDeleteEmpty: () -> Unit,
  focusRequester: FocusRequester,
  modifier: Modifier = Modifier
) {
  var isFocused by remember { mutableStateOf(false) }

  Column {
    OutlinedTextField(
      modifier = modifier
        .focusRequester(focusRequester)
        .onFocusChanged { isFocused = it.isFocused }
        .onPreviewKeyEvent { keyEvent ->
          if (keyEvent.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_DEL
            && keyEvent.nativeKeyEvent.action == android.view.KeyEvent.ACTION_DOWN
          ) {
            onDeleteEmpty()
            true
          } else false
        }
        .widthIn(max = 48.dp)
        .border(
          width = 3.dp,
          color = when {
            isError -> MaterialTheme.colorScheme.error
            isFocused -> MaterialTheme.colorScheme.tertiary
            else -> MaterialTheme.colorScheme.secondaryContainer
          },
          shape = RoundedCornerShape(13.dp)
        ),
      singleLine = true,
      readOnly = !enabled,
      value = value,
      onValueChange = onValueChange,
      textStyle = MaterialTheme.typography.titleSmall.copy(
        textAlign = TextAlign.Center
      ),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
      colors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.secondaryContainer,
        focusedPlaceholderColor = MaterialTheme.colorScheme.tertiaryContainer,
        unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
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

