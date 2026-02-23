package com.yi_555555555.codelingo.presentation.screens.register

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.domain.usecase.RegisterUseCase
import com.yi_555555555.codelingo.presentation.screens.validation.isValidEmail
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
class RegisterViewModel @Inject constructor(
  private val registerUseCase: RegisterUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {
  private val _state = MutableStateFlow<ViewState>(ViewState.Input())
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

      is Command.InputEmail -> {
        _state.update {
          currentState.copy(
            email = command.email,
            emailErrorMessage = null
          )
        }
      }

      is Command.InputName -> {
        _state.update {
          currentState.copy(
            username = command.username,
            usernameErrorMessage = null
          )
        }
      }

      is Command.InputPassword -> {
        _state.update {
          currentState.copy(
            password = command.password,
            passwordErrorMessage = null
          )
        }
      }

      Command.Register -> {
        if (!validateFields()) return
        _state.update {
          currentState.copy(
            isLoading = true
          )
        }
        viewModelScope.launch {
          safeFetch(
            context = context,
            onSuccess = {
              registerUseCase(
                username = currentState.username,
                email = currentState.email.trim(),
                password = currentState.password
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

  private fun validateFields(): Boolean {
    val currentState = _state.value
    if (currentState !is ViewState.Input) return false

    val usernameErrorMessage = if (currentState.username.length < 2) {
      context.getString(R.string.username_requirement)
    } else null

    val emailErrorMessage = if (!currentState.email.isValidEmail()) {
      context.getString(R.string.email_requirement)
    } else null

    val passwordErrorMessage = if (!currentState.password.isValidPassword()) {
      context.getString(R.string.password_requirement)
    } else null

    _state.update {
      currentState.copy(
        usernameErrorMessage = usernameErrorMessage,
        emailErrorMessage = emailErrorMessage,
        passwordErrorMessage = passwordErrorMessage
      )
    }

    return usernameErrorMessage == null &&
      emailErrorMessage == null &&
      passwordErrorMessage == null
  }
}

sealed interface ViewState {
  data class Input(
    val username: String = "",
    val usernameErrorMessage: String? = null,
    val email: String = "",
    val emailErrorMessage: String? = null,
    val password: String = "",
    val passwordErrorMessage: String? = null,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false
  ) : ViewState

  data object Success : ViewState
}

sealed interface Command {
  data class InputName(val username: String) : Command
  data class InputEmail(val email: String) : Command
  data class InputPassword(val password: String) : Command
  data object ChangePasswordVisibility : Command
  data object Register : Command
}