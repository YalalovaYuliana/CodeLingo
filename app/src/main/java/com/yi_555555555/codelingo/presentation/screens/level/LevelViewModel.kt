package com.yi_555555555.codelingo.presentation.screens.level

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yi_555555555.codelingo.domain.model.Task
import com.yi_555555555.codelingo.domain.model.TaskType
import com.yi_555555555.codelingo.domain.usecase.GetLevelDetailsUseCase
import com.yi_555555555.codelingo.domain.usecase.SubmitTaskUseCase
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
  private val submitTaskUseCase: SubmitTaskUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val transferLevelData = savedStateHandle.toRoute<Screen.LevelScreen>()

  private val _state =
    MutableStateFlow<ViewState>(ViewState.Start(transferLevelData.levelId, transferLevelData.title))
  val state = _state.asStateFlow()
  val snackbarHostState = MutableStateFlow(SnackbarHostState())

  fun processCommand(command: Command) {
    val currentState = _state.value

    if (currentState !is ViewState.Input) return

    when (command) {
      is Command.Submit -> {
        
        if (currentState.showTheory) {
          _state.update {
            currentState.copy(
              showTheory = false
            )
          }
        } else {
          submitTask()
        }
      }

      else -> {}
    }
  }

  fun submitTask() {
    val currentState = _state.value
    if (currentState !is ViewState.Input) return
    _state.update {
      currentState.copy(
        isLoading = true
      )
    }
    viewModelScope.launch {
      safeFetch(
        context = context,
        onSuccess = {
          val currentTask = currentState.currentTask
          val submitAnswer = submitTaskUseCase(
            taskId = currentState.currentTask.id,
            answers = when (currentState.currentTask.type) {
              TaskType.Choice -> {
                currentTask.options?.filter { it.isChosen }?.map { it.id }.orEmpty()
              }

              TaskType.Gap -> {
                currentTask.gaps?.map { it.userAnswer }.orEmpty()
              }

              else -> emptyList()
            },
            codeAnswer = currentTask.code?.userAnswer
          )
          withContext(Dispatchers.Main) {
            val currentTask = currentState.currentTask
            if (submitAnswer.isCorrect && currentTask.numInOrder < currentState.tasks.size) {
              _state.update {
                currentState.copy(
                  currentTask = currentState.tasks[currentTask.numInOrder + 1]
                )
              }
            } else if (submitAnswer.isCorrect) {
              _state.update { ViewState.SuccessSubmitLevel }
            } else {
              when (currentTask.type) {
                TaskType.Choice -> {
                  _state.update {
                    currentState.copy(
                      currentTask = currentTask.copy(
                        options = currentTask.options?.map { option ->
                          option.copy(
                            isError = submitAnswer.correctOptions?.find { it == option.id } == null
                          )
                        }
                      )
                    )
                  }
                }

                TaskType.Gap -> {
                  _state.update {
                    currentState.copy(
                      currentTask = currentTask.copy(
                        gaps = currentTask.gaps?.map { gap ->
                          gap.copy(
                            isError = submitAnswer.correctAnswers?.find { it == gap.userAnswer } == null
                          )
                        }
                      )
                    )
                  }
                }

                TaskType.Code -> {
                  _state.update {
                    currentState.copy(
                      currentTask = currentTask.copy(
                        code = currentTask.code?.copy(
                          isError = true
                        )
                      )
                    )
                  }
                }
              }
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

  fun startLevel() {
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
          val levelDetails = getLevelDetailsUseCase(transferLevelData.levelId)
          val tasks = levelDetails.first
          withContext(Dispatchers.Main) {
            _state.update {
              ViewState.Input(
                tasks = tasks.sortedBy { it.numInOrder },
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
    val isLoading: Boolean = false,
    val isError: Boolean = false
  ) : ViewState

  data object SuccessSubmitLevel : ViewState
}

sealed interface Command {
  data class InputCode(val code: String) : Command
  data class InputGap(val gap: String) : Command
  data class SelectOption(val option: Task.Option) : Command
  data object Submit : Command
}