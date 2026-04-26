package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.model.CourseDetails
import com.yi_555555555.codelingo.domain.repository.UserRepository
import javax.inject.Inject

class GetCourseDetailsUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(courseId: Int): CourseDetails {
    val cache = userRepository.cacheFlow.value
    val courseDetails = cache.courseDetails

    return if (courseDetails == null) {
      userRepository.getCourseDetails(courseId)
    } else courseDetails
  }
}