package com.yi_555555555.codelingo.presentation.screens.login

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.domain.usecase.LoginUseCase
import com.yi_555555555.codelingo.presentation.screens.validation.isValidEmail
import com.yi_555555555.codelingo.presentation.screens.validation.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginUseCase: LoginUseCase,
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

      is Command.InputPassword -> {
        _state.update {
          currentState.copy(
            password = command.password,
            passwordErrorMessage = null
          )
        }
      }

      Command.Login -> {
        if (!validateFields()) return
        _state.update {
          currentState.copy(
            isLoading = true,
            errorMessage = null
          )
        }
        viewModelScope.launch {
          _state.update {
            var errorMessage: String? = null
            try {
              loginUseCase(
                email = currentState.email,
                password = currentState.password
              )
            } catch (e: HttpException) {
              errorMessage = when (e.code()) {
                409 -> "Email уже зарегистрирован"
                else -> context.getString(R.string.something_went_wrong)
              }
            } catch (_: IOException) {
              errorMessage = "Проверьте подключение к интернету"
            }
//            catch (_: Exception) {
//              errorMessage = context.getString(R.string.something_went_wrong)
//            }
            if (errorMessage != null) {
              currentState.copy(
                isLoading = false,
                errorMessage = errorMessage
              )
            } else ViewState.Success
          }
        }
      }
    }
  }

  private fun validateFields(): Boolean {
    val currentState = _state.value
    if (currentState !is ViewState.Input) return false

    val emailErrorMessage = if (!currentState.email.isValidEmail()) {
      context.getString(R.string.email_requirement)
    } else null

    val passwordErrorMessage = if (!currentState.password.isValidPassword()) {
      context.getString(R.string.password_requirement)
    } else null

    _state.update {
      currentState.copy(
        emailErrorMessage = emailErrorMessage,
        passwordErrorMessage = passwordErrorMessage
      )
    }

    return emailErrorMessage == null && passwordErrorMessage == null
  }
}

sealed interface ViewState {
  data class Input(
    val email: String = "",
    val emailErrorMessage: String? = null,
    val password: String = "",
    val passwordErrorMessage: String? = null,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
  ) : ViewState

  data object Success : ViewState
}

sealed interface Command {
  data class InputEmail(val email: String) : Command
  data class InputPassword(val password: String) : Command
  data object ChangePasswordVisibility : Command
  data object Login : Command
}