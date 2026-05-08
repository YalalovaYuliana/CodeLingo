package com.yi_555555555.codelingo.domain.usecase

import com.yi_555555555.codelingo.domain.model.Achievment
import com.yi_555555555.codelingo.domain.repository.UserRepository
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetMyAchievmentsUseCase @Inject constructor(
  private val userRepository: UserRepository
) {

  val achievments = userRepository.cacheFlow.mapNotNull { it.achievments }

  suspend operator fun invoke(): List<Achievment> {
    return userRepository.getMyAchievments()
  }
}