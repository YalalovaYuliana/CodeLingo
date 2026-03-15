package com.yi_555555555.codelingo.presentation.screens.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.yi_555555555.codelingo.presentation.components.CourseCard
import com.yi_555555555.codelingo.presentation.components.ErrorState
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.LoadingState
import com.yi_555555555.codelingo.presentation.components.PrimaryButton
import com.yi_555555555.codelingo.presentation.components.ScreenScaffold
import com.yi_555555555.codelingo.presentation.components.VSpacer
import com.yi_555555555.codelingo.presentation.components.WSpacer

@Composable
fun CoursesScreen(
  onSuccessStart: () -> Unit,
  viewModel: CoursesViewModel = hiltViewModel()
) {

  val state by viewModel.state.collectAsState()
  val snackbarHostState by viewModel.snackbarHostState.collectAsState()

  ScreenScaffold(
    snackbarHostState = snackbarHostState
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onPrimaryContainer)
        .padding(innerPadding)
        .imePadding()
        .padding(
          start = 24.dp,
          end = 24.dp,
          bottom = 8.dp
        ),
      verticalArrangement = Arrangement.Center,
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
              viewModel.getCourses()
            }
          )
        }

        is ViewState.Input -> {
          Header(
            text = stringResource(R.string.courses)
          )
          VSpacer(32.dp)
          LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            items(currentState.courses) { course ->
              CourseCard(
                course = course,
                selected = course.id == currentState.selectedCourseId,
                showDescription = true,
                onClick = {
                  viewModel.processCommand(Command.SelectCourse(course.id))
                }
              )
            }
          }
          WSpacer()
          VSpacer(40.dp)
          PrimaryButton(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 26.dp),
            text = stringResource(R.string.start).uppercase(),
            onClick = { viewModel.processCommand(Command.Start) },
            isLoading = currentState.isLoading
          )
          VSpacer(16.dp)
        }

        ViewState.Success -> {
          LaunchedEffect(Unit) {
            onSuccessStart()
          }
        }
      }
    }
  }
}