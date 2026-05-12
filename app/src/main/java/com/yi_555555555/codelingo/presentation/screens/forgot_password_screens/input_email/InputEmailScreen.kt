package com.yi_555555555.codelingo.presentation.screens.forgot_password_screens.input_email

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.InputTextField
import com.yi_555555555.codelingo.presentation.components.PrimaryButton
import com.yi_555555555.codelingo.presentation.components.ScreenScaffold
import com.yi_555555555.codelingo.presentation.components.TopAppBar
import com.yi_555555555.codelingo.presentation.components.VSpacer
import com.yi_555555555.codelingo.presentation.components.WSpacer

@Composable
fun InputEmailScreen(
  onStartVerifyCode: (String) -> Unit,
  onBackClick: () -> Unit,
  viewModel: InputEmailViewModel = hiltViewModel()
) {

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
        .imePadding()
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
          Header(
            text = stringResource(R.string.reset_password_title)
          )
          Image(
            modifier = Modifier
              .padding(vertical = 20.dp)
              .widthIn(max = 120.dp),
            painter = painterResource(R.drawable.forgot_password),
            contentDescription = "forgot password"
          )
          InputTextField(
            value = currentState.email,
            readOnly = currentState.isLoading,
            onValueChange = { newValue ->
              viewModel.processCommand(Command.InputEmail(newValue))
            },
            placeholder = stringResource(R.string.email_hint),
            errorMessage = currentState.emailErrorMessage
          )
          WSpacer()
          VSpacer(16.dp)
          PrimaryButton(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 26.dp),
            text = stringResource(R.string.get_code).uppercase(),
            onClick = { viewModel.processCommand(Command.GetVerifyCode) },
            isLoading = currentState.isLoading
          )
        }

        is ViewState.Success -> {
          LaunchedEffect(Unit) {
            onStartVerifyCode(currentState.email)
          }
        }
      }
    }
  }
}