package com.yi_555555555.codelingo.presentation.screens.forgot_password_screens.input_email

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.domain.usecase.GetVerifyForgotPasswordCodeUseCase
import com.yi_555555555.codelingo.presentation.navigation.Screen
import com.yi_555555555.codelingo.presentation.screens.validation.isValidEmail
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
class InputEmailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val getVerifyForgotPasswordCodeUseCase: GetVerifyForgotPasswordCodeUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val transferResetPasswordData = savedStateHandle.toRoute<Screen.InputEmailScreen>()

  private val _state =
    MutableStateFlow<ViewState>(ViewState.Input(email = transferResetPasswordData.email.trim()))
  val state = _state.asStateFlow()
  val snackbarHostState = MutableStateFlow(SnackbarHostState())

  fun processCommand(command: Command) {
    val currentState = _state.value
    if (currentState !is ViewState.Input) return

    when (command) {

      is Command.InputEmail -> {
        _state.update {
          currentState.copy(
            email = command.email,
            emailErrorMessage = null
          )
        }
      }

      Command.GetVerifyCode -> {
        if (!validateEmail()) return

        _state.update {
          currentState.copy(
            isLoading = true
          )
        }
        viewModelScope.launch {
          safeFetch(
            context = context,
            onSuccess = {
              getVerifyForgotPasswordCodeUseCase(currentState.email)
              withContext(Dispatchers.Main) {
                _state.update { ViewState.Success(currentState.email) }
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

  private fun validateEmail(): Boolean {
    val currentState = _state.value
    if (currentState !is ViewState.Input) return false

    val emailErrorMessage = if (currentState.email.isBlank()) {
      context.getString(R.string.required_to_fill)
    } else if (!currentState.email.isValidEmail()) {
      context.getString(R.string.incorrect_format)
    } else null

    _state.update {
      currentState.copy(
        emailErrorMessage = emailErrorMessage
      )
    }

    return emailErrorMessage == null
  }
}

sealed interface ViewState {
  data class Input(
    val email: String,
    val emailErrorMessage: String? = null,
    val isLoading: Boolean = false
  ) : ViewState

  data class Success(val email: String) : ViewState
}

sealed interface Command {
  data class InputEmail(val email: String) : Command
  data object GetVerifyCode : Command
}