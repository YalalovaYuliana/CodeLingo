package com.yi_555555555.codelingo.presentation.screens.settings

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.domain.usecase.LogoutUseCase
import com.yi_555555555.codelingo.utils.safeFetch
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val logoutUseCase: LogoutUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val _state = MutableStateFlow<ViewState>(ViewState.Initial())
  val state = _state.asStateFlow()
  val snackbarHostState = MutableStateFlow(SnackbarHostState())

  fun processCommand(command: Command) {
    val currentState = _state.value
    if (currentState !is ViewState.Initial) return

    when (command) {
      Command.Logout -> {
        viewModelScope.launch {
          safeFetch(
            context = context,
            onSuccess = {
              logoutUseCase()
            },
            onFailure = { errorMessage ->
              snackbarHostState.value.showSnackbar(
                message = errorMessage
              )
            }
          )
        }
      }

      Command.ChangeAppTheme -> {}
      Command.ChangeProfileName -> {}
      Command.ChangeProfilePhoto -> {}
      Command.SaveChanges -> {}
    }
  }
}

sealed interface ViewState {
  data class Initial(
    val name: String = ""
  ) : ViewState

  data object Logout : ViewState
}

sealed interface Command {
  data object ChangeProfilePhoto : Command
  data object ChangeProfileName : Command
  data object ChangeAppTheme : Command
  data object SaveChanges : Command // todo think about change design
  data object Logout : Command
}