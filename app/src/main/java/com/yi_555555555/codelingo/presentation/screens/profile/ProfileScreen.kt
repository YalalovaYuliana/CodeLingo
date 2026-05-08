package com.yi_555555555.codelingo.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.SubcomposeAsyncImage
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.presentation.components.CourseCard
import com.yi_555555555.codelingo.presentation.components.ErrorState
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.LoadingState
import com.yi_555555555.codelingo.presentation.components.VSpacer
import com.yi_555555555.codelingo.presentation.ui.theme.CodeLingoTheme
import kotlin.math.roundToInt

@Composable
fun ProfileScreen(
  onSettingsClick: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: ProfileViewModel = hiltViewModel()
) {

  val state by viewModel.state.collectAsState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(
        start = 24.dp,
        end = 24.dp,
        top = 16.dp
      ),
    verticalArrangement = Arrangement.Top
  ) {
    when (val currentState = state) {
      ViewState.Loading -> {
        LoadingState()
      }

      is ViewState.Error -> {
        ErrorState(
          errorMessage = currentState.errorMessage,
          onRetryClick = {
            viewModel.getUser()
          }
        )
      }

      is ViewState.Profile -> {
        val user = currentState.user
        IconButton(
          modifier = Modifier.align(Alignment.End),
          onClick = onSettingsClick
        ) {
          Image(
            painter = painterResource(R.drawable.ic_settings),
            contentDescription = "settings"
          )
        }
        SubcomposeAsyncImage(
          modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .align(Alignment.CenterHorizontally),
          model = user.pictureLink,
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
              modifier = Modifier.size(120.dp),
              painter = painterResource(R.drawable.profile_placeholder_head),
              contentDescription = "profile photo error",
              contentScale = ContentScale.Crop
            )
          },
          contentDescription = "profile photo",
          contentScale = ContentScale.Crop
        )
        VSpacer(16.dp)
        Header(
          modifier = Modifier.fillMaxWidth(),
          text = user.username
        )
        VSpacer(12.dp)
        Text(
          modifier = Modifier.fillMaxWidth(),
          text = user.email,
          textAlign = TextAlign.Center,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.tertiaryContainer
        )
        VSpacer(40.dp)
        Row(
          modifier = Modifier.padding(horizontal = 8.dp),
          horizontalArrangement = Arrangement.spacedBy(40.dp)
        ) {
          StatsCard(
            modifier = Modifier.weight(1f),
            icon = painterResource(R.drawable.ic_streak_fire),
            value = user.streak.toString(),
            text = stringResource(R.string.streak)
          )
          StatsCard(
            modifier = Modifier.weight(1f),
            icon = painterResource(R.drawable.ic_lightning),
            value = user.xp.toString(),
            text = stringResource(R.string.xp)
          )
        }
        VSpacer(40.dp)
        Text(
          modifier = Modifier.padding(start = 8.dp),
          text = stringResource(R.string.courses),
          style = MaterialTheme.typography.titleSmall,
          color = MaterialTheme.colorScheme.secondary
        )
        VSpacer(8.dp)
        CourseCard(
          title = currentState.courseName,
          description = stringResource(
            R.string.x_progress,
            currentState.courseProgress.roundToInt()
          ),
          iconUrl = currentState.courseIconUrl
        )
        VSpacer(8.dp)
      }
    }
  }
}

@Composable
private fun StatsCard(
  icon: Painter,
  value: String,
  text: String,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .border(
        width = 3.dp,
        color = MaterialTheme.colorScheme.outlineVariant,
        shape = RoundedCornerShape(24.dp)
      )
      .background(
        shape = RoundedCornerShape(24.dp),
        color = Color.Transparent
      ),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier
        .padding(
          vertical = 16.dp
        ),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Icon(
        painter = icon,
        contentDescription = "Strike",
        tint = Color.Unspecified
      )
      Text(
        text = value,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
      )
      Text(
        text = text.lowercase(),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.secondaryContainer
      )
    }
  }
}


@Composable
@Preview(showSystemUi = true)
private fun StatsCardPreview() {
  CodeLingoTheme {
    StatsCard(
      icon = painterResource(R.drawable.ic_streak_fire),
      value = 12.toString(),
      text = "Серия"
    )
  }
}