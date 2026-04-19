package com.yi_555555555.codelingo.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.ScreenScaffold
import com.yi_555555555.codelingo.presentation.components.TextButton
import com.yi_555555555.codelingo.presentation.components.TopAppBar
import com.yi_555555555.codelingo.presentation.components.WSpacer

@Composable
fun SettingsScreen(
  onLogout: () -> Unit,
  onBackClick: () -> Unit,
  viewModel: SettingsViewModel = hiltViewModel()
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
        is ViewState.Initial -> {
          Header(
            text = stringResource(R.string.account_title)
          )
          WSpacer()
          TextButton(
            text = stringResource(R.string.logout),
            onClick = {
              viewModel.processCommand(Command.Logout)
            }
          )
//          PrimaryButton(
//            modifier = Modifier
//              .fillMaxWidth()
//              .padding(horizontal = 26.dp),
//            text = stringResource(R.string.login).uppercase(),
//            onClick = { viewModel.processCommand(Command.Login) },
//            isLoading = currentState.isLoading
//          )
        }

        ViewState.Logout -> {
          LaunchedEffect(Unit) {
            onLogout()
          }
        }
      }
    }
  }
}