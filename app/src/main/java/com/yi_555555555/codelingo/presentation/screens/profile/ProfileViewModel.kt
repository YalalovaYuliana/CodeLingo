package com.yi_555555555.codelingo.presentation.screens.profile

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.domain.model.User
import com.yi_555555555.codelingo.domain.usecase.GetCourseDetailsUseCase
import com.yi_555555555.codelingo.domain.usecase.GetUserCourseUseCase
import com.yi_555555555.codelingo.domain.usecase.GetUserUseCase
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
class ProfileViewModel @Inject constructor(
  private val getUserUseCase: GetUserUseCase,
  private val getUserCourseUseCase: GetUserCourseUseCase,
  private val getCourseDetailsUseCase: GetCourseDetailsUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
  val state = _state.asStateFlow()

  val snackbarHostState = MutableStateFlow(SnackbarHostState())

  init {
    getUser()
    viewModelScope.launch {
      getUserUseCase.user.collect { user ->
        val currentState = _state.value
        if (currentState is ViewState.Profile) {
          _state.update {
            currentState.copy(
              user = user
            )
          }
        }
      }
    }
    viewModelScope.launch {
      getCourseDetailsUseCase.courseProgress.collect { courseProgress ->
        val currentState = _state.value
        if (currentState is ViewState.Profile) {
          _state.update {
            currentState.copy(
              courseProgress = courseProgress
            )
          }
        }
      }
    }
  }

  fun getUser() {
    _state.update { ViewState.Loading }
    viewModelScope.launch {
      safeFetch(
        context = context,
        onSuccess = {
          val user = getUserUseCase()
          val courseId = getUserCourseUseCase() ?: error("missing user course")
          val courseDetails = getCourseDetailsUseCase(courseId)
          withContext(Dispatchers.Main) {
            _state.update {
              ViewState.Profile(
                user = user,
                courseName = courseDetails.course.title,
                courseIconUrl = courseDetails.course.iconUrl,
                courseProgress = courseDetails.progress
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
  data object Loading : ViewState

  data class Profile(
    val user: User,
    val courseName: String,
    val courseIconUrl: String?,
    val courseProgress: Double
  ) : ViewState

  data class Error(
    val errorMessage: String
  ) : ViewState
}


