package com.yi_555555555.codelingo.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yi_555555555.codelingo.presentation.components.ErrorState
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.LevelCard
import com.yi_555555555.codelingo.presentation.components.LoadingState
import com.yi_555555555.codelingo.presentation.components.VSpacer

@Composable
fun MainScreen(
  modifier: Modifier = Modifier,
  viewModel: MainViewModel = hiltViewModel()
) {

  val state by viewModel.state.collectAsState()
  val snackbarHostState by viewModel.snackbarHostState.collectAsState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.onPrimaryContainer),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    when (val currentState = state) {
      ViewState.Loading -> {
        LoadingState()
      }

      is ViewState.Error -> {
        ErrorState(
          errorMessage = currentState.errorMessage,
          onRetryClick = {
            viewModel.getLevels()
          }
        )
      }

      is ViewState.Input -> {
        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.spacedBy(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          item {
            Header(
              text = currentState.courseName
            )
            VSpacer(16.dp)
          }
          itemsIndexed(currentState.levels) { index, level ->
            val offsetX = if (index % 2 == 0) 0.dp else 56.dp
            LevelCard(
              level = level,
              onClick = {},
              modifier = Modifier.offset(x = offsetX)
            )
          }
        }
      }

      ViewState.Success -> {
        LaunchedEffect(Unit) {
          //onSuccessStart()
        }
      }
    }
  }
}