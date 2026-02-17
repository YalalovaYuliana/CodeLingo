package com.yi_555555555.codelingo.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.domain.repository.UserRepository
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
  private val userRepository: UserRepository
) : ViewModel() {

  private val _hasAccessToken = MutableStateFlow<Boolean?>(null)
  val hasAccessToken = _hasAccessToken.asStateFlow()

  init {
    viewModelScope.launch {
      val token = withContext(Dispatchers.IO) {
        userRepository.readAccessToken()
      }
      _hasAccessToken.update { token != null }
    }
  }
}
