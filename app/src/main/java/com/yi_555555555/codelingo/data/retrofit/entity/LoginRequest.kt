package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class LoginRequest(
  @SerializedName("email")
  val email: String,
  @SerializedName("password")
  val password: String
)