package com.yi_555555555.codelingo.domain.repository

import com.yi_555555555.codelingo.domain.model.AccessToken
import com.yi_555555555.codelingo.domain.model.LoginCredentials
import com.yi_555555555.codelingo.domain.model.RegisterCredentials
import com.yi_555555555.codelingo.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
  val cacheFlow: StateFlow<Cache>
  suspend fun register(registerCredentials: RegisterCredentials): AccessToken
  suspend fun login(loginCredentials: LoginCredentials): AccessToken
  suspend fun getUser(authToken: String): User
  suspend fun readAccessToken(): AccessToken?
  suspend fun writeAccessToken(accessToken: AccessToken)
  suspend fun deleteAccessToken()
}

data class Cache(
  val accessToken: AccessToken? = null,
  val user: User? = null
)