package com.yi_555555555.codelingo.presentation.screens.forgot_password_screens.reset_password

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.domain.usecase.ResetPasswordUseCase
import com.yi_555555555.codelingo.presentation.navigation.Screen
import com.yi_555555555.codelingo.presentation.screens.validation.isValidPassword
import com.yi_555555555.codelingo.utils.safeFetch
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val resetPasswordUseCase: ResetPasswordUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val transferResetPasswordData = savedStateHandle.toRoute<Screen.ResetPasswordScreen>()

  private val _state = MutableStateFlow<ViewState>(
    ViewState.Input(
      email = transferResetPasswordData.email,
      code = transferResetPasswordData.code
    )
  )
  val state = _state.asStateFlow()
  val snackbarHostState = MutableStateFlow(SnackbarHostState())

  fun processCommand(command: Command) {
    val currentState = _state.value
    if (currentState !is ViewState.Input) return

    when (command) {
      is Command.ChangePasswordVisibility -> {
        _state.update {
          currentState.copy(
            isPasswordVisible = !currentState.isPasswordVisible
          )
        }
      }

      is Command.InputPassword -> {
        _state.update {
          currentState.copy(
            newPassword = command.password,
            newPasswordErrorMessage = null
          )
        }
      }

      Command.ResetPassword -> {
        if (!validatePassword()) return
        _state.update {
          currentState.copy(
            isLoading = true
          )
        }
        viewModelScope.launch {
          safeFetch(
            context = context,
            onSuccess = {
              resetPasswordUseCase(
                email = currentState.email,
                code = currentState.code,
                newPassword = currentState.newPassword
              )
              snackbarHostState.value.showSnackbar(
                message = "Пароль успешно сброшен!"
              )
              withContext(Dispatchers.Main) {
                _state.update { ViewState.Success }
              }
            },
            onFailure = { errorMessage ->
              snackbarHostState.value.showSnackbar(
                message = errorMessage
              )
              withContext(Dispatchers.Main) {
                _state.update {
                  currentState.copy(
                    isLoading = false
                  )
                }
              }
            }
          )
        }
      }
    }
  }

  private fun validatePassword(): Boolean {
    val currentState = _state.value
    if (currentState !is ViewState.Input) return false

    val passwordErrorMessage = if (!currentState.newPassword.isValidPassword()) {
      context.getString(R.string.password_requirement)
    } else null

    _state.update {
      currentState.copy(
        newPasswordErrorMessage = passwordErrorMessage
      )
    }

    return passwordErrorMessage == null
  }
}

sealed interface ViewState {
  data class Input(
    val email: String,
    val code: String,
    val newPassword: String = "",
    val newPasswordErrorMessage: String? = null,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false
  ) : ViewState

  data object Success : ViewState
}

sealed interface Command {
  data class InputPassword(val password: String) : Command
  data object ChangePasswordVisibility : Command
  data object ResetPassword : Command
}