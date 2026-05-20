package com.yi_555555555.codelingo.presentation.screens.onboarding

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.domain.usecase.GetUserCourseUseCase
import com.yi_555555555.codelingo.domain.usecase.HandleGoogleCallbackUseCase
import com.yi_555555555.codelingo.domain.usecase.LoginByGoogleUseCase
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
class OnboardingViewModel @Inject constructor(
  private val loginByGoogleUseCase: LoginByGoogleUseCase,
  private val handleGoogleCallbackUseCase: HandleGoogleCallbackUseCase,
  private val getUserCourseUseCase: GetUserCourseUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val _state = MutableStateFlow<ViewState>(ViewState.Input)
  val state = _state.asStateFlow()
  val snackbarHostState = MutableStateFlow(SnackbarHostState())

  fun login() {
    val currentState = _state.value
    if (currentState !is ViewState.Input) return

//    _state.update {
//      currentState.copy(
//        isLoading = true
//      )
//    }

    viewModelScope.launch {
      safeFetch(
        context = context,
        onSuccess = {
          loginByGoogleUseCase()
          val selectedCourseId = getUserCourseUseCase()
          withContext(Dispatchers.Main) {
            _state.update { ViewState.Success(selectedCourseId != null) }
          }
        },
        onFailure = { errorMessage ->
          snackbarHostState.value.showSnackbar(
            message = errorMessage
          )
//          withContext(Dispatchers.Main) {
//            _state.update {
//              currentState.copy(
//                isLoading = false
//              )
//            }
//          }
        }
      )
    }
  }

}

sealed interface ViewState {
  data object Input : ViewState

  data class Success(val hasSelectedCourseId: Boolean) : ViewState
}