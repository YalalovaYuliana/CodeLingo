package com.yi_555555555.codelingo.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun LoginScreen(
  onSuccessLogin: () -> Unit,
  onBackClick: () -> Unit,
  viewModel: LoginViewModel = hiltViewModel()
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
        .padding(innerPadding)
        .imePadding()
        .padding(
          start = 24.dp,
          end = 24.dp,
          bottom = 64.dp
        ),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      when (val currentState = state) {
        is ViewState.Input -> {
          Header(
            text = stringResource(R.string.login_title)
          )
          Image(
            modifier = Modifier
              .padding(vertical = 40.dp)
              .widthIn(min = 120.dp, max = 180.dp),
            painter = painterResource(R.drawable.welcome_cat),
            contentDescription = "welcome cat"
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
          VSpacer(21.dp)
          InputTextField(
            value = currentState.password,
            readOnly = currentState.isLoading,
            onValueChange = { newValue ->
              viewModel.processCommand(Command.InputPassword(newValue))
            },
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Password,
              imeAction = ImeAction.Done
            ),
            visualTransformation = if (currentState.isPasswordVisible) {
              VisualTransformation.None
            } else {
              PasswordVisualTransformation()
            },
            placeholder = stringResource(R.string.password_hint),
            trailingIcon = {
              IconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = { viewModel.processCommand(Command.ChangePasswordVisibility) }
              ) {
                Icon(
                  modifier = Modifier.size(28.dp),
                  painter = painterResource(
                    if (currentState.isPasswordVisible) {
                      R.drawable.ic_open_eye
                    } else {
                      R.drawable.ic_close_eye
                    }
                  ),
                  contentDescription = "show password"
                )
              }
            },
            errorMessage = currentState.passwordErrorMessage
          )
          WSpacer()
          //VSpacer(40.dp)
          PrimaryButton(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 26.dp),
            text = stringResource(R.string.login).uppercase(),
            onClick = { viewModel.processCommand(Command.Login) },
            isLoading = currentState.isLoading
          )
        }

        ViewState.Success -> {
          LaunchedEffect(Unit) {
            onSuccessLogin()
          }
        }
      }
    }
  }
}