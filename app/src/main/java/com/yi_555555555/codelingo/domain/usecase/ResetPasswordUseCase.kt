package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.repository.UserRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(
    email: String,
    code: String,
    newPassword: String
  ) {
    userRepository.resetPassword(
      email = email,
      code = code,
      newPassword = newPassword
    )
  }
}