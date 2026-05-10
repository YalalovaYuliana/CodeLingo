package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ScreenScaffold(
  modifier: Modifier = Modifier,
  topBar: @Composable (() -> Unit) = {},
  bottomBar: @Composable (() -> Unit) = {},
  snackbarHostState: SnackbarHostState? = null,
  floatingActionButton: @Composable () -> Unit = {},
  floatingActionButtonPosition: FabPosition = FabPosition.End,
  containerColor: Color = MaterialTheme.colorScheme.background,
  contentColor: Color = contentColorFor(containerColor),
  contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
  content: @Composable (PaddingValues) -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
  ) {
    Scaffold(
      modifier = modifier,
      topBar = topBar,
      bottomBar = bottomBar,
      floatingActionButton = floatingActionButton,
      floatingActionButtonPosition = floatingActionButtonPosition,
      contentColor = contentColor,
      contentWindowInsets = contentWindowInsets,
    ) { innerPadding ->
      content(innerPadding)
    }

    snackbarHostState?.let {
      SnackbarHost(
        hostState = it,
        modifier = Modifier
          .fillMaxWidth()
          .systemBarsPadding()
          .padding(40.dp),
        snackbar = { data ->
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .border(
                width = 3.dp,
                shape = RoundedCornerShape(25),
                color = MaterialTheme.colorScheme.background
              )
              .clip(RoundedCornerShape(25))
              .background(MaterialTheme.colorScheme.outlineVariant),
            contentAlignment = Alignment.Center
          ) {
            Text(
              modifier = Modifier.padding(12.dp),
              text = data.visuals.message.uppercase(),
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.secondary
            )
          }
        }
      )
    }
  }
}