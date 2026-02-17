package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.data.room.entity.AccessTokenDbModel
import com.yi_555555555.codelingo.domain.model.LoginCredentials
import com.yi_555555555.codelingo.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(
    email: String,
    password: String
  ) {
    val accessToken = userRepository.login(
      LoginCredentials(
        email = email,
        password = password
      )
    ).accessToken

    withContext(Dispatchers.IO) {
      userRepository.writeAccessToken(
        AccessTokenDbModel(
          accessToken = accessToken
        )
      )
    }
  }
}