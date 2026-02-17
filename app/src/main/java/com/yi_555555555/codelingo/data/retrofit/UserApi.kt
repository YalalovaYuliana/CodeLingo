package com.yi_555555555.codelingo.data.retrofit

import com.yi.myapplication.data.entity.codelingo.LoginRequest
import com.yi.myapplication.data.entity.codelingo.RegisterRequest
import com.yi.myapplication.data.entity.codelingo.UserResponse
import com.yi_555555555.codelingo.data.retrofit.entity.AccessToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {
  @POST("auth/register")
  suspend fun register(@Body registerRequest: RegisterRequest): AccessToken

  @POST("auth/login")
  suspend fun login(@Body loginRequest: LoginRequest): AccessToken

  @GET("users/me")
  suspend fun getUser(@Header("Authorization") authToken: String): UserResponse
}