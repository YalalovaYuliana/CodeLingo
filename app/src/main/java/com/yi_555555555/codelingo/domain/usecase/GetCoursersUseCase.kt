package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.model.Course
import com.yi_555555555.codelingo.domain.repository.UserRepository
import javax.inject.Inject

class GetCoursesUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(): List<Course> {
    val cache = userRepository.cacheFlow.value
    val courses = cache.courses
    return if (!courses.isNullOrEmpty()) {
      courses
    } else {
      userRepository.getCourses()
    }
  }
}