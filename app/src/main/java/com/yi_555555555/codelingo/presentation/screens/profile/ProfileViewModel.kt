package com.yi_555555555.codelingo.presentation.screens.profile

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.domain.model.Course
import com.yi_555555555.codelingo.domain.model.User
import com.yi_555555555.codelingo.domain.usecase.DeleteUseCase
import com.yi_555555555.codelingo.domain.usecase.GetCoursesUseCase
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
  private val getCoursesUseCase: GetCoursesUseCase,
  private val deleteUseCase: DeleteUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
  val state = _state.asStateFlow()

  val snackbarHostState = MutableStateFlow(SnackbarHostState())

  init {
    getUser()
  }

  fun getUser() {
    _state.update { ViewState.Loading }
    viewModelScope.launch {
      safeFetch(
        context = context,
        onSuccess = {
          val user = getUserUseCase()
          val courses = getCoursesUseCase()
          withContext(Dispatchers.Main) {
            _state.update {
              ViewState.Profile(
                user = user,
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

sealed interface ViewState {
  data object Loading : ViewState

  data class Profile(
    val user: User,
    val courses: List<Course>
  ) : ViewState

  data class Error(
    val errorMessage: String
  ) : ViewState
}


