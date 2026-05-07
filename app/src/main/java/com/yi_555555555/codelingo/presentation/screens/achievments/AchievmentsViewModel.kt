package com.yi_555555555.codelingo.presentation.screens.achievments

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi_555555555.codelingo.domain.model.Achievment
import com.yi_555555555.codelingo.domain.usecase.GetMyAchievmentsUseCase
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
class AchievmentsViewModel @Inject constructor(
  private val getMyAchievmentsUseCase: GetMyAchievmentsUseCase,
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val _state = MutableStateFlow<ViewState>(ViewState.Input())
  val state = _state.asStateFlow()

  init {
    getAchievments()

    viewModelScope.launch {
      getMyAchievmentsUseCase.achievments.collect { achievments ->
        val currentState = _state.value
        if (currentState is ViewState.Input) {
          _state.update {
            currentState.copy(
              userAchievments = achievments.filter { it.received },
              availableAchievments = achievments.filter { !it.received }
            )
          }
        }
      }
    }
  }

  fun getAchievments() {
    _state.update { ViewState.Loading }
    viewModelScope.launch {
      safeFetch(
        context = context,
        onSuccess = {
          val achievments = getMyAchievmentsUseCase()
          withContext(Dispatchers.Main) {
            _state.update {
              ViewState.Input(
                userAchievments = achievments.filter { it.received },
                availableAchievments = achievments.filter { !it.received }
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
    val userAchievments: List<Achievment> = emptyList(),
    val availableAchievments: List<Achievment> = emptyList()
  ) : ViewState

  data object Loading : ViewState
  data class Error(
    val errorMessage: String
  ) : ViewState
}