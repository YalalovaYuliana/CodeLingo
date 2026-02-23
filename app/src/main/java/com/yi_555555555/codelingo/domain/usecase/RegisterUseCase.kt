package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.model.RegisterCredentials
import com.yi_555555555.codelingo.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(
    username: String,
    email: String,
    password: String
  ) {
    val accessToken = userRepository.register(
      RegisterCredentials(
        username = username,
        email = email,
        password = password
      )
    )

    withContext(Dispatchers.IO) {
      userRepository.writeAccessToken(accessToken)
    }
  }
}