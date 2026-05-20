package com.yi_555555555.codelingo.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.presentation.components.HSpacer
import com.yi_555555555.codelingo.presentation.components.PrimaryButton
import com.yi_555555555.codelingo.presentation.components.ScreenScaffold
import com.yi_555555555.codelingo.presentation.components.VSpacer
import com.yi_555555555.codelingo.presentation.components.WSpacer


@Composable
fun OnboardingScreen(
  onRegisterClick: () -> Unit,
  onAlreadyHaveAccountClick: () -> Unit,
  onGoogleLoginSuccess: (hasSelectedCourseId: Boolean) -> Unit,
  viewModel: OnboardingViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsState()
  val snackbarHostState by viewModel.snackbarHostState.collectAsState()

  ScreenScaffold(
    snackbarHostState = snackbarHostState
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
        .padding(innerPadding)
        .padding(bottom = 32.dp)
    ) {
      when (val currentState = state) {
        ViewState.Input -> {
          VSpacer(90.dp)
          Image(
            modifier = Modifier.padding(horizontal = 50.dp),
            painter = painterResource(R.drawable.onboarding_screen),
            contentDescription = "onboarding"
          )
          VSpacer(90.dp)
          DescriptionText(
            modifier = Modifier.padding(start = 50.dp),
            mainWord = stringResource(R.string.learn),
            secondWord = stringResource(R.string.free)
          )
          DescriptionText(
            modifier = Modifier.padding(start = 50.dp),
            mainWord = stringResource(R.string.practice),
            secondWord = stringResource(R.string.always)
          )
          DescriptionText(
            modifier = Modifier.padding(start = 50.dp),
            mainWord = stringResource(R.string.programm),
            secondWord = stringResource(R.string.anywhere)
          )
          VSpacer(16.dp)
          WSpacer()
          PrimaryButton(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 50.dp),
            text = stringResource(R.string.start).uppercase(),
            onClick = onRegisterClick
          )
          VSpacer(4.dp)
          TextButton(
            modifier = Modifier
              .fillMaxWidth()
              .wrapContentWidth(Alignment.CenterHorizontally),
            onClick = onAlreadyHaveAccountClick
          ) {
            Text(
              text = stringResource(R.string.already_have_an_account_login),
              textAlign = TextAlign.Center,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.secondary
            )
          }
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 50.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            HorizontalDivider(
              modifier = Modifier.weight(1f)
            )
            HSpacer(4.dp)
            Text(
              text = stringResource(R.string.or),
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.tertiaryContainer
            )
            HSpacer(4.dp)
            HorizontalDivider(
              modifier = Modifier.weight(1f)
            )
          }
          VSpacer(16.dp)
          PrimaryButton(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 50.dp),
            text = stringResource(R.string.login_by_google).uppercase(),
            icon = painterResource(R.drawable.google),
            onClick = { viewModel.login() },
            isLoading = false
          )
        }

        is ViewState.Success -> {
          LaunchedEffect(Unit) {
            onGoogleLoginSuccess(currentState.hasSelectedCourseId)
          }
        }
      }
    }
  }
}

@Composable
private fun DescriptionText(
  modifier: Modifier = Modifier,
  mainWord: String,
  secondWord: String
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.Bottom
  ) {
    Text(
      text = mainWord,
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.secondary
    )
    Text(
      text = secondWord,
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.secondary
    )
  }
}