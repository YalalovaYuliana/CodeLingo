package com.yi_555555555.codelingo.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.presentation.components.ErrorState
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.LoadingState
import com.yi_555555555.codelingo.presentation.components.ScreenScaffold
import com.yi_555555555.codelingo.presentation.components.VSpacer
import com.yi_555555555.codelingo.presentation.components.WSpacer
import com.yi_555555555.codelingo.presentation.ui.theme.White

@Composable
fun ProfileScreen(
  onLogout: () -> Unit,
  viewModel: ProfileViewModel = hiltViewModel()
) {

  val state by viewModel.state.collectAsState()

  ScreenScaffold(
  ) { innerPadding ->
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
              .clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = "profile photo"
          )
          VSpacer(16.dp)
          Header(
            text = user.username
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