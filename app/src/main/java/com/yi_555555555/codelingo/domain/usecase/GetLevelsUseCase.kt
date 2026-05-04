package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.model.Level
import com.yi_555555555.codelingo.domain.repository.UserRepository
import javax.inject.Inject

class GetLevelsUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(courseId: Int): List<Level> {
    val cache = userRepository.cacheFlow.value
    val levels = cache.levels
    return if (!levels.isNullOrEmpty()) {
      levels
    } else {
      userRepository.getUserCourseLevels(courseId)
    }
  }
}