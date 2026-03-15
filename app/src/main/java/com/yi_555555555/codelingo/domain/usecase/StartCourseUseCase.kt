package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StartCourseUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(courseId: Int) {
    userRepository.startCourse(courseId)

    withContext(Dispatchers.IO) {
      userRepository.writeCourseId(courseId)
    }
  }
}