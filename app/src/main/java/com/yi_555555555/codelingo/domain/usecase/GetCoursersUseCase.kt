package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.model.Course
import com.yi_555555555.codelingo.domain.repository.UserRepository
import javax.inject.Inject

class GetCoursersUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(): List<Course> {
    val cache = userRepository.cacheFlow.value
    val coursers = cache.coursers
    return if (!coursers.isNullOrEmpty()) {
      coursers
    } else {
      userRepository.getCoursers()
    }
  }
}