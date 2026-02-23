package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ScreenScaffold(
  modifier: Modifier = Modifier,
  topBar: @Composable (() -> Unit) = {},
  bottomBar: @Composable (() -> Unit) = {},
  snackbarHost: @Composable (() -> Unit) = {},
  snackbarHostState: SnackbarHostState? = null,
  floatingActionButton: @Composable () -> Unit = {},
  floatingActionButtonPosition: FabPosition = FabPosition.End,
  containerColor: Color = MaterialTheme.colorScheme.background,
  contentColor: Color = contentColorFor(containerColor),
  contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
  content: @Composable (PaddingValues) -> Unit
) {
  Scaffold(
    modifier = modifier,
    topBar = topBar,
    bottomBar = bottomBar,
    snackbarHost = snackbarHost,
    floatingActionButton = floatingActionButton,
    floatingActionButtonPosition = floatingActionButtonPosition,
    contentColor = contentColor,
    contentWindowInsets = contentWindowInsets,
  ) { innerPadding ->
    Box(
      modifier = Modifier.fillMaxSize()
    ) {
      content(innerPadding)
      snackbarHostState?.let {
        SnackbarHost(
          hostState = snackbarHostState,
          modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
        )
      }
    }
  }
}