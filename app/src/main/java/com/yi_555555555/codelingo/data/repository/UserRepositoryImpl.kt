package com.yi_555555555.codelingo.data.repository

import com.yi.myapplication.data.entity.codelingo.LoginRequest
import com.yi.myapplication.data.entity.codelingo.RegisterRequest
import com.yi.myapplication.data.entity.codelingo.UserResponse
import com.yi_555555555.codelingo.data.retrofit.UserApi
import com.yi_555555555.codelingo.data.retrofit.entity.AccessToken
import com.yi_555555555.codelingo.data.room.UserDataBase
import com.yi_555555555.codelingo.data.room.entity.AccessTokenDbModel
import com.yi_555555555.codelingo.domain.model.LoginCredentials
import com.yi_555555555.codelingo.domain.model.RegisterCredentials
import com.yi_555555555.codelingo.domain.repository.UserRepository

class UserRepositoryImpl(
  private val userApi: UserApi,
  private val userDataBase: UserDataBase,
) : UserRepository {

  override suspend fun register(registerCredentials: RegisterCredentials): AccessToken {
    return userApi.register(
      RegisterRequest(
        username = registerCredentials.username,
        email = registerCredentials.email,
        password = registerCredentials.password
      )
    )
  }

  override suspend fun login(loginCredentials: LoginCredentials): AccessToken {
    return userApi.login(
      LoginRequest(
        email = loginCredentials.email,
        password = loginCredentials.password
      )
    )
  }

  override suspend fun getUser(authToken: String): UserResponse {
    return userApi.getUser(authToken)
  }

  override suspend fun readAccessToken(): AccessTokenDbModel? =
    userDataBase.userDao().getToken()


  override suspend fun writeAccessToken(accessToken: AccessTokenDbModel) {
    userDataBase.userDao().insertToken(accessToken)
  }

  override suspend fun deleteAccessToken() {
    userDataBase.userDao().deleteToken()
  }
}