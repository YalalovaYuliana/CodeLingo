package com.yi_555555555.codelingo.presentation.screens.courses

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.domain.model.Course
import com.yi_555555555.codelingo.domain.usecase.GetCoursesUseCase
import com.yi_555555555.codelingo.domain.usecase.StartCourseUseCase
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
class CoursesViewModel @Inject constructor(
  private val getCoursesUseCase: GetCoursesUseCase,
  private val startCourseUseCase: StartCourseUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val _state = MutableStateFlow<ViewState>(ViewState.Input())
  val state = _state.asStateFlow()
  val snackbarHostState = MutableStateFlow(SnackbarHostState())

  init {
    getCourses()
  }

  fun processCommand(command: Command) {
    val currentState = _state.value
    if (currentState !is ViewState.Input) return

    when (command) {
      is Command.SelectCourse -> {
        _state.update {
          currentState.copy(
            selectedCourseId = command.courseId
          )
        }
      }

      Command.Start -> {
        if (currentState.selectedCourseId == null) {
          Toast.makeText(context, R.string.select_course, Toast.LENGTH_LONG).show()
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
              startCourseUseCase(currentState.selectedCourseId)
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

  fun getCourses() {
    viewModelScope.launch {
      _state.update { ViewState.Loading }
      viewModelScope.launch {
        safeFetch(
          context = context,
          onSuccess = {
            val courses = getCoursesUseCase()
            withContext(Dispatchers.Main) {
              _state.update {
                ViewState.Input(
                  courses = courses
                )
              }
            }
          },
          onFailure = { errorMessage ->
            _state.update {
              ViewState.Error(
                errorMessage = errorMessage
              )
            }
          }
        )
      }
    }
  }
}

sealed interface ViewState {
  data class Input(
    val courses: List<Course> = emptyList(),
    val selectedCourseId: Int? = null,
    val isLoading: Boolean = false
  ) : ViewState

  data object Success : ViewState
  data object Loading : ViewState
  data class Error(
    val errorMessage: String
  ) : ViewState
}

sealed interface Command {
  data class SelectCourse(val courseId: Int) : Command
  data object Start : Command
}