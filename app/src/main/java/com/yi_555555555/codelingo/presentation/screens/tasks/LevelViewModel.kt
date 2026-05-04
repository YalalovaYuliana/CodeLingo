package com.yi_555555555.codelingo.presentation.screens.tasks

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yi_555555555.codelingo.domain.model.Task
import com.yi_555555555.codelingo.domain.usecase.GetLevelDetailsUseCase
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
class LevelViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val getLevelDetailsUseCase: GetLevelDetailsUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val transferLevelData = savedStateHandle.toRoute<Screen.LevelScreen>()

  private val _state =
    MutableStateFlow<ViewState>(ViewState.Start(transferLevelData.levelId, transferLevelData.title))
  val state = _state.asStateFlow()
  val snackbarHostState = MutableStateFlow(SnackbarHostState())

  fun processCommand(command: Command) {
    println("yuliana processCommand")

    val currentState = _state.value

    println("yuliana currentState: ${currentState}")

    if (currentState !is ViewState.Start) return

    println("yuliana start level")

    when (command) {
      is Command.StartLevel -> {
        startLevel(transferLevelData.levelId)
      }

      else -> {}
    }
  }

  fun startLevel(levelId: Int) {
    val currentState = _state.value
    if (currentState !is ViewState.Start) return
    _state.update {
      currentState.copy(
        isLoading = true
      )
    }
    viewModelScope.launch {
      safeFetch(
        context = context,
        onSuccess = {
          val levelDetails = getLevelDetailsUseCase(levelId)
          val tasks = levelDetails.first
          withContext(Dispatchers.Main) {
            _state.update {
              ViewState.Input(
                tasks = tasks,
                theory = levelDetails.second,
                showTheory = true,
                currentTask = tasks.first()
              )
            }
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

sealed interface ViewState {
  data class Start(
    val levelId: Int,
    val title: String,
    val isLoading: Boolean = false
  ) : ViewState

  data class Input(
    val tasks: List<Task>,
    val theory: String,
    val showTheory: Boolean,
    val currentTask: Task,
    val isLoading: Boolean = false
  ) : ViewState

  data object SuccessSubmitLevel : ViewState
}

sealed interface Command {
  data class InputCode(val code: String) : Command
  data class InputGap(val gap: String) : Command
  data class SelectOption(val option: Task.Option) : Command
  data object StartLevel : Command
  data object Submit : Command
}