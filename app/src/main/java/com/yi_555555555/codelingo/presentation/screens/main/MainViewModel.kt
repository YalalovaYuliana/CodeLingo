package com.yi_555555555.codelingo.presentation.screens.main

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.domain.model.Level
import com.yi_555555555.codelingo.domain.usecase.GetCourseDetailsUseCase
import com.yi_555555555.codelingo.domain.usecase.GetLevelsUseCase
import com.yi_555555555.codelingo.domain.usecase.GetUserCourseUseCase
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
class MainViewModel @Inject constructor(
  private val getLevelsUseCase: GetLevelsUseCase,
  private val getUserCourseUseCase: GetUserCourseUseCase,
  private val getCourseDetailsUseCase: GetCourseDetailsUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val _state = MutableStateFlow<ViewState>(ViewState.Input())
  val state = _state.asStateFlow()
  val snackbarHostState = MutableStateFlow(SnackbarHostState())

  init {
    getLevels()

    viewModelScope.launch {
      getLevelsUseCase.levels.collect { levels ->
        val currentState = _state.value
        val currentLevel = levels.find { !it.isComplete } ?: levels.last()
        if (currentState is ViewState.Input) {
          _state.update {
            currentState.copy(
              levels = levels,
              currentLevel = currentLevel
            )
          }
        }
      }
    }
  }

  fun getLevels() {
    _state.update { ViewState.Loading }
    viewModelScope.launch {
      safeFetch(
        context = context,
        onSuccess = {
          val courseId = getUserCourseUseCase() ?: error("missing user course")
          val levels = getLevelsUseCase(courseId)
          val courseDetails = getCourseDetailsUseCase(courseId)
          val currentLevel = levels.find { !it.isComplete } ?: levels.last()
          withContext(Dispatchers.Main) {
            _state.update {
              ViewState.Input(
                courseName = courseDetails.course.title,
                currentLevel = currentLevel,
                levels = levels
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


sealed interface ViewState {
  data class Input(
    val courseName: String = "",
    val currentLevel: Level? = null,
    val levels: List<Level> = emptyList(),
    val isLoading: Boolean = false
  ) : ViewState

  data object Loading : ViewState
  data class Error(
    val errorMessage: String
  ) : ViewState
}