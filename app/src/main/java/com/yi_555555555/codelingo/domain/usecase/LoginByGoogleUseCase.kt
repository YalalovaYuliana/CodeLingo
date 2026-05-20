package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.repository.UserRepository
import javax.inject.Inject

class LoginByGoogleUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(
  ) {
    userRepository.loginByGoogle()
  }
}