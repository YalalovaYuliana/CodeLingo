package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.model.SubmitAnswer
import com.yi_555555555.codelingo.domain.repository.UserRepository
import javax.inject.Inject

class SubmitTaskUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(
    taskId: Int,
    answers: List<Any>,
    codeAnswer: String?
  ): SubmitAnswer {
    return if (codeAnswer != null) {
      userRepository.submitCodeTask(
        taskId = taskId,
        answers = codeAnswer
      )
    } else {
      userRepository.submitTask(
        taskId = taskId,
        answers = answers
      )
    }
  }
}