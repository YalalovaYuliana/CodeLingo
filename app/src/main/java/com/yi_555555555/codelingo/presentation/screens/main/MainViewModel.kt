package com.yi_555555555.codelingo.presentation.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

  private val _state = MutableStateFlow<ViewState>(ViewState.Initial)
  val state = _state.asStateFlow()

  fun processCommand(command: Command) {
    val currentState = _state.value
    if (currentState !is ViewState.Initial) return

    when (command) {
      is Command.StartLevel -> {

      }
    }
  }
}


sealed interface ViewState {
  data object Initial : ViewState
}

sealed interface Command {
  data object StartLevel : Command
}