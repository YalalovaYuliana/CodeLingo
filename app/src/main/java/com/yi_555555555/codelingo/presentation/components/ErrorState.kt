package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yi_555555555.codelingo.R

@Composable
fun ErrorState(
  errorMessage: String,
  onRetryClick: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .systemBarsPadding(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(R.drawable.sad_cat),
      modifier = Modifier
        .size(240.dp)
        .padding(horizontal = 16.dp),
      contentDescription = "sad cat"
    )
    VSpacer(16.dp)
    Header(text = errorMessage)
    VSpacer(16.dp)
    PrimaryButton(
      text = stringResource(R.string.retry),
      onClick = onRetryClick
    )
  }
}