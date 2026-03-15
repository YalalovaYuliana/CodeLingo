package com.yi_555555555.codelingo.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.domain.repository.UserRepository
import com.yi_555555555.codelingo.domain.usecase.GetUserCourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class NavViewModel @Inject constructor(
  private val userRepository: UserRepository,
  private val getUserCourseUseCase: GetUserCourseUseCase
) : ViewModel() {

  private val _state = MutableStateFlow<ViewState?>(null)
  val state = _state.asStateFlow()

  init {
    viewModelScope.launch {
      val token = withContext(Dispatchers.IO) {
        userRepository.readAccessToken()
      }
      val selectedCourseId = if (token != null) {
        getUserCourseUseCase()
      } else null
      _state.update {
        ViewState(
          hasAccessToken = token != null,
          hasSelectedCourseId = selectedCourseId != null
        )
      }
    }
  }
}

data class ViewState(
  val hasAccessToken: Boolean?,
  val hasSelectedCourseId: Boolean?
)
