package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.model.User
import com.yi_555555555.codelingo.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
  private val userRepository: UserRepository
) {
  suspend operator fun invoke(): User {
    val cache = userRepository.cacheFlow.value
    val user = cache.user
    return user ?: userRepository.getUser()
  }
}