package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yi_555555555.codelingo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
  centerContent: (@Composable () -> Unit)? = null,
  containerColor: Color = Color.Transparent,
  onBackClick: (() -> Unit)? = null,
  onCloseClick: (() -> Unit)? = null,
) {
  TopAppBar(
    title = { centerContent?.invoke() },
    navigationIcon = {
      onBackClick?.let {
        IconButton(
          onClick = onBackClick
        ) {
          Icon(
            modifier = Modifier.widthIn(max = 32.dp),
            painter = painterResource(R.drawable.arrow_left),
            contentDescription = "Back"
          )
        }
      }
    },
    actions = {
      onCloseClick?.let {
        IconButton(
          onClick = onCloseClick
        ) {
          Icon(
            modifier = Modifier.widthIn(max = 32.dp),
            painter = painterResource(R.drawable.ic_close),
            contentDescription = "Close"
          )
        }
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = containerColor,
      navigationIconContentColor = MaterialTheme.colorScheme.tertiaryContainer
    )
  )
}