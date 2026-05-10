package com.yi_555555555.codelingo.presentation.screens.achievments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.SubcomposeAsyncImage
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.domain.model.Achievment
import com.yi_555555555.codelingo.presentation.components.ErrorState
import com.yi_555555555.codelingo.presentation.components.HSpacer
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.LoadingState
import com.yi_555555555.codelingo.presentation.components.VSpacer
import com.yi_555555555.codelingo.presentation.components.rememberImageLoader

@Composable
fun AchievmentsScreen(
  modifier: Modifier = Modifier,
  viewModel: AchievmentsViewModel = hiltViewModel()
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
          modifier = Modifier.padding(24.dp),
          errorMessage = currentState.errorMessage,
          onRetryClick = {
            viewModel.getAchievments()
          }
        )
      }

      is ViewState.Input -> {
        VSpacer(32.dp)
        Header(
          text = stringResource(R.string.achievments_title)
        )
        VSpacer(8.dp)
        LazyColumn(
          modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
          contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)
        ) {
          items(currentState.userAchievments) { achievment ->
            AchievmentCard(achievment = achievment)
          }

          item {
            Text(
              modifier = Modifier.padding(start = 8.dp),
              text = stringResource(R.string.available),
              style = MaterialTheme.typography.titleSmall,
              color = MaterialTheme.colorScheme.secondary
            )
          }

          items(currentState.availableAchievments) { achievment ->
            AchievmentCard(achievment = achievment)
          }
        }
      }
    }
  }
}

@Composable
private fun AchievmentCard(
  achievment: Achievment,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .border(
        width = 3.dp,
        color = MaterialTheme.colorScheme.outlineVariant,
        shape = RoundedCornerShape(13.dp)
      )
      .background(
        shape = RoundedCornerShape(13.dp),
        color = Color.Transparent
      )
      .alpha(if (achievment.received) 1f else .3f),
    contentAlignment = Alignment.Center
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      SubcomposeAsyncImage(
        modifier = Modifier.size(60.dp),
        model = achievment.iconUrl,
        imageLoader = rememberImageLoader(),
        loading = {
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
          ) {
            CircularProgressIndicator(
              modifier = Modifier.size(20.dp),
              strokeWidth = 2.dp
            )
          }
        },
        error = {
          Image(
            modifier = Modifier.size(60.dp),
            painter = painterResource(R.drawable.course_temp),
            contentDescription = "achievment icon error"
          )
        },
        contentDescription = "achievment icon"
      )
      HSpacer(8.dp)
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(
            vertical = 16.dp
          ),
        horizontalAlignment = Alignment.Start
      ) {
        Text(
          text = achievment.title,
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.secondary
        )
        achievment.description?.let {
          VSpacer(12.dp)
          Text(
            text = achievment.description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
          )
        }
      }
    }
  }
}