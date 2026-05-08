package com.yi_555555555.codelingo.presentation.screens.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.presentation.components.HSpacer
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.InputTextField
import com.yi_555555555.codelingo.presentation.components.OutlinedButton
import com.yi_555555555.codelingo.presentation.components.PrimaryAlertDialog
import com.yi_555555555.codelingo.presentation.components.PrimaryButton
import com.yi_555555555.codelingo.presentation.components.ScreenScaffold
import com.yi_555555555.codelingo.presentation.components.TextButton
import com.yi_555555555.codelingo.presentation.components.TopAppBar
import com.yi_555555555.codelingo.presentation.components.VSpacer
import com.yi_555555555.codelingo.presentation.components.WSpacer

@Composable
fun SettingsScreen(
  onBackClick: () -> Unit,
  viewModel: SettingsViewModel = hiltViewModel()
) {

  val state by viewModel.state.collectAsState()
  val snackbarHostState by viewModel.snackbarHostState.collectAsState()

  val galleryLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) { uri: Uri? ->
    uri?.let {
      viewModel.processCommand(Command.ChangeProfilePhoto(uri))
    }
  }

  var showLogoutDialog by remember { mutableStateOf(false) }
  var showDeleteAccountDialog by remember { mutableStateOf(false) }

  PrimaryAlertDialog(
    visible = showLogoutDialog,
    title = stringResource(R.string.leave_account),
    description = null,
    confirmText = stringResource(R.string.yes_leave),
    dismissText = stringResource(R.string.cancel),
    onDismissClick = {
      showLogoutDialog = false
    },
    onConfirmClick = {
      showLogoutDialog = false
      viewModel.processCommand(Command.Logout)
    }
  )

  PrimaryAlertDialog(
    visible = showDeleteAccountDialog,
    title = stringResource(R.string.delete_account_dialog_title),
    description = null,
    confirmText = stringResource(R.string.yes_delete),
    dismissText = stringResource(R.string.cancel),
    onDismissClick = {
      showDeleteAccountDialog = false
    },
    onConfirmClick = {
      showDeleteAccountDialog = false
      viewModel.processCommand(Command.DeleteAccount)
    }
  )

  ScreenScaffold(
    topBar = {
      TopAppBar(
        onBackClick = onBackClick
      )
    },
    snackbarHostState = snackbarHostState
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onPrimaryContainer)
        .padding(innerPadding)
        .padding(
          start = 24.dp,
          end = 24.dp,
          bottom = 64.dp
        ),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      when (val currentState = state) {
        is ViewState.Input -> {
          Header(
            text = stringResource(R.string.account_title)
          )
          VSpacer(64.dp)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = stringResource(R.string.profile_photo),
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.secondary
            )
            HSpacer(8.dp)
            OutlinedButton(
              modifier = Modifier.widthIn(max = 180.dp),
              text = currentState.newProfilePhoto?.name ?: stringResource(R.string.choose_file),
              onClick = {
                galleryLauncher.launch("image/*")
              }
            )
          }
          VSpacer(40.dp)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = stringResource(R.string.name_hint),
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.secondary,
              textAlign = TextAlign.Center
            )
            HSpacer(8.dp)
            InputTextField(
              modifier = Modifier.widthIn(max = 180.dp),
              value = currentState.newUserName ?: currentState.currentUsername,
              onValueChange = { newValue ->
                viewModel.processCommand(Command.ChangeProfileName(newValue))
              }
            )
          }
          WSpacer()
          TextButton(
            text = stringResource(R.string.logout),
            onClick = {
              showLogoutDialog = true
            }
          )
          TextButton(
            text = stringResource(R.string.delete_account),
            color = MaterialTheme.colorScheme.error,
            onClick = {
              showDeleteAccountDialog = true
            }
          )
          VSpacer(40.dp)
          PrimaryButton(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 26.dp),
            text = stringResource(R.string.save).uppercase(),
            onClick = { viewModel.processCommand(Command.SaveChanges) },
            isLoading = currentState.isLoading
          )
        }

        ViewState.SuccessChangedProfileData -> {
          LaunchedEffect(Unit) {
            onBackClick()
          }
        }

//        ViewState.Logout -> {
//          LaunchedEffect(Unit) {
//            onLogout()
//          }
//        }
      }
    }
  }
}