package com.yi_555555555.codelingo.presentation.navigation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.domain.repository.UserRepository
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
class NavViewModel @Inject constructor(
  private val userRepository: UserRepository,
  private val getUserCourseUseCase: GetUserCourseUseCase,
  @ApplicationContext context: Context
) : ViewModel() {

  private val _state = MutableStateFlow<ViewState?>(null)
  val state = _state.asStateFlow()

  init {
    viewModelScope.launch {
      safeFetch(
        context = context,
        onSuccess = {
          val token = withContext(Dispatchers.IO) {
            userRepository.readAccessToken()
          }
          val selectedCourseId = if (token != null) {
            getUserCourseUseCase() // todo move to onCreate MainActivity
          } else null
          _state.update {
            ViewState(
              hasAccessToken = token != null,
              hasSelectedCourseId = selectedCourseId != null
            )
          }
          userRepository.cacheFlow.collect { cache ->
            _state.update { currentState ->
              println("YULIANA ${cache.selectedCourseId}")
              currentState?.copy(
                hasAccessToken = cache.accessToken != null,
                hasSelectedCourseId = cache.selectedCourseId != null
              )
            }
          }
        },
        onFailure = { }
      )
    }
  }
}

data class ViewState(
  val hasAccessToken: Boolean?,
  val hasSelectedCourseId: Boolean?
)
