package com.yi_555555555.codelingo.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yi_555555555.codelingo.presentation.components.Header

@Composable
fun MainScreen(
  modifier: Modifier = Modifier,
  viewModel: MainViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsState()

  Column(
    modifier = modifier
      .fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    when (state) {
      ViewState.Initial -> {
        Header(
          text = "Главный экран"
        )
      }
    }
  }
}