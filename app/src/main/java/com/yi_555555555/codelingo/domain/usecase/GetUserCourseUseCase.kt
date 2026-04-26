package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserCourseUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(): Int? {
    val cache = userRepository.cacheFlow.value
    val courseId = cache.selectedCourseId ?: userRepository.readCourseId()

    return if (courseId == null) {
      userRepository.getUserCourseId()?.also {
        withContext(Dispatchers.IO) {
          userRepository.writeCourseId(it)
        }
      }
    } else courseId
  }
}