package com.yi_555555555.codelingo.presentation.screens.verify_email

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yi_555555555.codelingo.domain.usecase.VerifyEmailUseCase
import com.yi_555555555.codelingo.presentation.navigation.Screen
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
class VerifyCodeViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val verifyEmailUseCase: VerifyEmailUseCase,
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

      is Command.InputCode -> {
        _state.update {
          currentState.copy(
            code = currentState.code.toMutableList().apply {
              this[command.index] = command.code?.toIntOrNull()
            }.toList(),
            isCodeValidationError = false
          )
        }
      }

      Command.SendVerifyCode -> {
        if (currentState.code.any { it == null }) {
          _state.update {
            currentState.copy(
              isCodeValidationError = true
            )
          }
          return
        }

        _state.update {
          currentState.copy(
            isLoading = true
          )
        }
        viewModelScope.launch {
          safeFetch(
            context = context,
            onSuccess = {
              val codeStr = currentState.code.joinToString("")
              verifyEmailUseCase(
                email = currentState.email,
                code = codeStr
              )
              withContext(Dispatchers.Main) {
                _state.update {
                  ViewState.Success
                }
              }
            },
            onFailure = { errorMessage ->
              withContext(Dispatchers.Main) {
                _state.update {
                  currentState.copy(
                    isLoading = false,
                    isCodeValidationError = true
                  )
                }
              }
              snackbarHostState.value.showSnackbar(
                message = errorMessage
              )
            }
          )
        }
      }
    }
  }
}

sealed interface ViewState {
  data class Input(
    val email: String,
    val code: List<Int?> = List(VERIFY_CODE_LENGTH) { null },
    val isCodeValidationError: Boolean = false,
    val isLoading: Boolean = false
  ) : ViewState

  data object Success : ViewState
}

sealed interface Command {
  data class InputCode(val index: Int, val code: String?) : Command
  data object SendVerifyCode : Command
}

private const val VERIFY_CODE_LENGTH = 6