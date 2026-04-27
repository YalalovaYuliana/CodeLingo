package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.model.Level
import com.yi_555555555.codelingo.domain.repository.UserRepository
import javax.inject.Inject

class GetLevelDetailsUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(levelId: Int): Pair<Level, String> {
    val level = userRepository.getLevelTasks(levelId)
    val theory = userRepository.getLevelTheory(levelId)

    return level to theory
  }
}