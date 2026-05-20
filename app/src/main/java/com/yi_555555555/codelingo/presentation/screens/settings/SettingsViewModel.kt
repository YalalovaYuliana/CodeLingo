package com.yi_555555555.codelingo.presentation.screens.settings

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.domain.usecase.ChangeProfileDataUseCase
import com.yi_555555555.codelingo.domain.usecase.DeleteAccountUseCase
import com.yi_555555555.codelingo.domain.usecase.GetUserUseCase
import com.yi_555555555.codelingo.domain.usecase.LogoutUseCase
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
import java.io.File
import java.io.InputStream

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val getUserUseCase: GetUserUseCase,
  private val logoutUseCase: LogoutUseCase,
  private val changeProfileDataUseCase: ChangeProfileDataUseCase,
  private val deleteAccountUseCase: DeleteAccountUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val _state = MutableStateFlow<ViewState>(ViewState.Input())
  val state = _state.asStateFlow()
  val snackbarHostState = MutableStateFlow(SnackbarHostState())

  init {
    viewModelScope.launch {
      getUserUseCase.user.collect { user ->
        val currentState = _state.value
        if (currentState is ViewState.Input) {
          _state.update {
            currentState.copy(
              currentUsername = user.username
            )
          }
        }
      }
    }
  }

  fun processCommand(command: Command) {
    val currentState = _state.value
    if (currentState !is ViewState.Input) return

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

      Command.DeleteAccount -> {
        viewModelScope.launch {
          safeFetch(
            context = context,
            onSuccess = {
              deleteAccountUseCase()
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
      is Command.ChangeProfileName -> {
        _state.update {
          currentState.copy(
            newUserName = command.newValue,
            saveButtonEnabled = checkSaveEnabledByNameChange(command.newValue)
          )
        }
      }

      is Command.ChangeProfilePhoto -> {
        val file = uriToFile(command.uri)
        val mimeType = getMimeTypeFromUri(context, command.uri)
        if (file != null) {
          _state.update {
            currentState.copy(
              newProfilePhoto = file,
              mimeType = mimeType ?: currentState.mimeType,
              saveButtonEnabled = true
            )
          }
        } else {
          viewModelScope.launch {
            snackbarHostState.value.showSnackbar(
              message = "He удалось загрузить файл"
            )
          }
        }
      }

      Command.SaveChanges -> {
        _state.update {
          currentState.copy(
            isLoading = true
          )
        }

        viewModelScope.launch {
          safeFetch(
            context = context,
            onSuccess = {
              changeProfileDataUseCase(
                username = currentState.newUserName,
                file = currentState.newProfilePhoto,
                mimeType = currentState.mimeType
              )
              withContext(Dispatchers.Main) {
                _state.update {
                  ViewState.SuccessChangedProfileData
                }
              }
            },
            onFailure = { errorMessage ->
              withContext(Dispatchers.Main) {
                _state.update {
                  currentState.copy(
                    isLoading = false
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

  private fun checkSaveEnabledByNameChange(
    newUserName: String?
  ): Boolean {
    val currentState = _state.value
    if (currentState !is ViewState.Input) return false
    return currentState.currentUsername != newUserName?.trim() && !newUserName.isNullOrBlank()
  }

  private fun getMimeTypeFromUri(context: Context, uri: Uri): String? {
    return context.contentResolver.getType(uri)
  }

  private fun uriToFile(uri: Uri): File? {
    val contentResolver: ContentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(uri) ?: return null

    val tempFile = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")

    return try {
      tempFile.outputStream().use { outputStream ->
        inputStream?.copyTo(outputStream)
      }
      tempFile
    } catch (e: Exception) {
      e.printStackTrace()
      null
    } finally {
      inputStream?.close()
    }
  }
}


sealed interface ViewState {
  data class Input(
    val currentUsername: String = "",
    val newUserName: String? = null,
    val newProfilePhoto: File? = null,
    val mimeType: String = "application/octet-stream",
    val isLoading: Boolean = false,
    val saveButtonEnabled: Boolean = false
  ) : ViewState

  data object SuccessChangedProfileData : ViewState

  //data object Logout : ViewState
}

sealed interface Command {
  data class ChangeProfilePhoto(val uri: Uri) : Command
  data class ChangeProfileName(val newValue: String) : Command
  data object ChangeAppTheme : Command
  data object SaveChanges : Command // todo think about change design
  data object Logout : Command
  data object DeleteAccount : Command
}