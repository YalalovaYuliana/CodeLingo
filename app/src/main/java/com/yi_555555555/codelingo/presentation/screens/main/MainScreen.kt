package com.yi_555555555.codelingo.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yi_555555555.codelingo.domain.model.Level
import com.yi_555555555.codelingo.presentation.components.ErrorState
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.LevelCard
import com.yi_555555555.codelingo.presentation.components.LoadingState

@Composable
fun MainScreen(
  modifier: Modifier = Modifier,
  onLevelClick: (Level) -> Unit,
  viewModel: MainViewModel = hiltViewModel()
) {

  val state by viewModel.state.collectAsState()

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
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.outline),
          contentAlignment = Alignment.Center
        ) {
          Header(
            modifier = Modifier.padding(vertical = 12.dp),
            text = currentState.courseName
          )
        }

        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.spacedBy(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)
        ) {
          itemsIndexed(currentState.levels) { index, level ->
            val offsetX = if (index % 2 == 0) (-64).dp else 64.dp
            LevelCard(
              index = index,
              level = level,
              onClick = { onLevelClick(level) },
              modifier = Modifier.offset(x = offsetX)
            )
          }
        }
      }
    }
  }
}