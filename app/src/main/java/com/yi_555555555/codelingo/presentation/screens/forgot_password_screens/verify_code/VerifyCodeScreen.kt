package com.yi_555555555.codelingo.presentation.screens.forgot_password_screens.verify_code

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.presentation.components.CodeDigitTextField
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.PrimaryButton
import com.yi_555555555.codelingo.presentation.components.ScreenScaffold
import com.yi_555555555.codelingo.presentation.components.TopAppBar
import com.yi_555555555.codelingo.presentation.components.VSpacer
import com.yi_555555555.codelingo.presentation.components.WSpacer

@Composable
fun VerifyCodeScreen(
  onSuccessSendVerifyCode: (String, String) -> Unit,
  onBackClick: () -> Unit,
  viewModel: VerifyCodeViewModel = hiltViewModel()
) {

  BackHandler { onBackClick() }

  val state by viewModel.state.collectAsState()
  val snackbarHostState by viewModel.snackbarHostState.collectAsState()

  ScreenScaffold(
    topBar = {
      TopAppBar(
        onBackClick = onBackClick
      )
    },
    snackbarHostState = snackbarHostState
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(state = rememberScrollState())
        .background(MaterialTheme.colorScheme.onPrimaryContainer)
        .padding(innerPadding)
        .padding(
          start = 24.dp,
          end = 24.dp,
          bottom = 16.dp
        ),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      when (val currentState = state) {
        is ViewState.Input -> {
          val focusRequesters = List(currentState.code.size) { remember { FocusRequester() } }

          LaunchedEffect(Unit) {
            focusRequesters.first().requestFocus()
          }

          Header(
            text = stringResource(R.string.code_input)
          )
          Image(
            modifier = Modifier
              .padding(vertical = 20.dp)
              .widthIn(max = 120.dp),
            painter = painterResource(R.drawable.forgot_password),
            contentDescription = "forgot password"
          )
          VSpacer(8.dp)
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.code_send_to, currentState.email),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondaryContainer
          )
          VSpacer(40.dp)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            WSpacer()
            repeat(currentState.code.size) { index ->
              val currentValue = currentState.code[index]?.toString() ?: ""
              CodeDigitTextField(
                value = currentValue,
                onValueChange = { newValue ->
                  viewModel.processCommand(
                    Command.InputCode(
                      index,
                      code = newValue.lastOrNull()?.toString()
                    )
                  )
                  if (newValue.isNotEmpty() && index < currentState.code.size - 1) {
                    focusRequesters[index + 1].requestFocus()
                  }
                },
                enabled = !currentState.isLoading,
                isError = currentState.isCodeValidationError,
                focusRequester = focusRequesters[index],
                onDeleteEmpty = {
                  focusRequesters[index - 1].requestFocus()
                }
              )
            }
            WSpacer()
          }
          WSpacer()
          VSpacer(16.dp)
          PrimaryButton(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 26.dp),
            text = stringResource(R.string.confirm).uppercase(),
            onClick = { viewModel.processCommand(Command.SendVerifyCode) },
            isLoading = currentState.isLoading
          )
        }

        is ViewState.Success -> {
          LaunchedEffect(Unit) {
            onSuccessSendVerifyCode(currentState.email, currentState.code)
          }
        }
      }
    }
  }
}