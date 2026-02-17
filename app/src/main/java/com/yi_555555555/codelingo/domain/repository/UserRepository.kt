package com.yi_555555555.codelingo.domain.repository

import com.yi.myapplication.data.entity.codelingo.UserResponse
import com.yi_555555555.codelingo.data.retrofit.entity.AccessToken
import com.yi_555555555.codelingo.data.room.entity.AccessTokenDbModel
import com.yi_555555555.codelingo.domain.model.LoginCredentials
import com.yi_555555555.codelingo.domain.model.RegisterCredentials

interface UserRepository {
  //  val accessToken: StateFlow<AccessTokenDbModel>
  suspend fun register(registerCredentials: RegisterCredentials): AccessToken
  suspend fun login(loginCredentials: LoginCredentials): AccessToken
  suspend fun getUser(authToken: String): UserResponse
  suspend fun readAccessToken(): AccessTokenDbModel?
  suspend fun writeAccessToken(accessToken: AccessTokenDbModel)
  suspend fun deleteAccessToken()
}