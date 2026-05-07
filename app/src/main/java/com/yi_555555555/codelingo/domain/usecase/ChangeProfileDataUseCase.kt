package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class ChangeProfileDataUseCase @Inject constructor(
  private val userRepository: UserRepository
) {

  suspend operator fun invoke(
    username: String?,
    file: File?,
    mimeType: String
  ) {
    userRepository.changeProfile(username, file, mimeType)
  }
}