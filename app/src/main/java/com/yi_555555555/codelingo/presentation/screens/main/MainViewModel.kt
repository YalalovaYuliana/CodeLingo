package com.yi_555555555.codelingo.presentation.screens.main

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
class MainViewModel @Inject constructor(
  private val userRepository: UserRepository
) : ViewModel() {

  private val _state = MutableStateFlow<ViewState>(ViewState.Initial)
  val state = _state.asStateFlow()

  fun processCommand(command: Command) {
    val currentState = _state.value
    if (currentState !is ViewState.Initial) return

    when (command) {
      is Command.Logout -> {
        _state.update {
          viewModelScope.launch {
            withContext(Dispatchers.IO) {
              userRepository.logout()
            }
          }
          ViewState.Logout
        }
      }
    }
  }
}


sealed interface ViewState {
  data object Initial : ViewState

  data object Logout : ViewState
}

sealed interface Command {
  data object Logout : Command
}