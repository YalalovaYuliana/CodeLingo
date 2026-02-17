package com.yi_555555555.codelingo.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.VSpacer
import com.yi_555555555.codelingo.presentation.ui.theme.White

@Composable
fun MainScreen(
  onLogout: () -> Unit,
  viewModel: MainViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsState()

  when (state) {
    ViewState.Initial -> {
      Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Header(
          text = "Тут что-то будет"
        )
        VSpacer(100.dp)
        Button(
          onClick = {
            viewModel.processCommand(Command.Logout)
          }
        ) {
          Header(
            modifier = Modifier.background(White),
            text = "Выйти"
          )
        }
      }
    }

    ViewState.Logout -> {
      LaunchedEffect(Unit) {
        onLogout()
      }
    }
  }
}