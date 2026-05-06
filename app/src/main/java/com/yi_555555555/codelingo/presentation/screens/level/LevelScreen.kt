package com.yi_555555555.codelingo.presentation.screens.level

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.colintheshots.twain.MarkdownText
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.domain.model.Task
import com.yi_555555555.codelingo.domain.model.TaskType
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.PrimaryButton
import com.yi_555555555.codelingo.presentation.components.ScreenScaffold
import com.yi_555555555.codelingo.presentation.components.TopAppBar
import com.yi_555555555.codelingo.presentation.components.VSpacer
import com.yi_555555555.codelingo.presentation.components.WSpacer

@Composable
fun LevelScreen(
  onSuccessSubmit: () -> Unit,
  onBackClick: () -> Unit,
  viewModel: LevelViewModel = hiltViewModel()
) {

  val state by viewModel.state.collectAsState()
  val snackbarHostState by viewModel.snackbarHostState.collectAsState()

  ScreenScaffold(
    topBar = {
      if (state is ViewState.Start) {
        TopAppBar(
          onBackClick = onBackClick
        )
      }
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
          bottom = 32.dp
        ),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      when (val currentState = state) {
        is ViewState.Start -> {
          WSpacer()
          Header(text = currentState.title)
          VSpacer(40.dp)
          Image(
            modifier = Modifier.padding(horizontal = 50.dp),
            painter = painterResource(R.drawable.onboarding_screen),
            contentDescription = "onboarding"
          )
          WSpacer()
          PrimaryButton(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 26.dp),
            text = stringResource(R.string.start).uppercase(),
            onClick = { viewModel.startLevel() },
            isLoading = currentState.isLoading
          )
        }

        is ViewState.Input -> {
          if (currentState.showTheory) {
            VSpacer(16.dp)
            MarkdownText(
              markdown = currentState.theory
            )
          } else {
            TaskContent(task = currentState.currentTask)
          }
          WSpacer()
          VSpacer(32.dp)
          PrimaryButton(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 26.dp),
            text = if (currentState.showTheory) {
              stringResource(R.string.next).uppercase()
            } else {
              stringResource(R.string.check).uppercase()
            },
            onClick = { viewModel.processCommand(Command.Submit) },
            isLoading = currentState.isLoading
          )
        }

        ViewState.SuccessSubmitLevel -> {
          LaunchedEffect(Unit) {
            onSuccessSubmit()
          }
        }
      }
    }
  }
}

@Composable
private fun TaskContent(
  task: Task
) {
  when (task.type) {
    TaskType.Choice -> {

    }

    TaskType.Gap -> {

    }

    TaskType.Code -> {

    }
  }
}