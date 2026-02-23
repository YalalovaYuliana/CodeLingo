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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.presentation.components.ErrorState
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.LoadingState
import com.yi_555555555.codelingo.presentation.components.ScreenScaffold
import com.yi_555555555.codelingo.presentation.components.VSpacer
import com.yi_555555555.codelingo.presentation.components.WSpacer
import com.yi_555555555.codelingo.presentation.ui.theme.CodeLingoTheme
import com.yi_555555555.codelingo.presentation.ui.theme.White

@Composable
fun ProfileScreen(
  onLogout: () -> Unit,
  viewModel: ProfileViewModel = hiltViewModel()
) {

  val state by viewModel.state.collectAsState()

  ScreenScaffold { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onPrimaryContainer)
        .padding(innerPadding)
        .padding(
          start = 24.dp,
          end = 24.dp,
          top = 16.dp,
          bottom = 8.dp
        ),
      verticalArrangement = Arrangement.Center
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
          Image(
            painter = painterResource(R.drawable.cat_temp),
            modifier = Modifier
              .size(130.dp)
              .clip(CircleShape)
              .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
            contentDescription = "profile photo"
          )
          VSpacer(16.dp)
          Header(
            modifier = Modifier.fillMaxWidth(),
            text = user.username
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
          VSpacer(64.dp)
          Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(R.string.coursers),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary
          )
          WSpacer()
          Button(
            onClick = {
              viewModel.logout()
            }
          ) {
            Header(
              modifier = Modifier.background(White),
              text = "Выйти"
            )
          }
        }

        ViewState.Logout -> {
          LaunchedEffect(Unit) { onLogout() }
        }
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
  CodeLingoTheme {
    Box(
      modifier = modifier
        .border(
          width = 3.dp,
          color = MaterialTheme.colorScheme.secondaryContainer,
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
}


@Composable
@Preview(showSystemUi = true)
private fun StatsCardPreview() {
  StatsCard(
    icon = painterResource(R.drawable.ic_streak_fire),
    value = 12.toString(),
    text = "Серия"
  )
}