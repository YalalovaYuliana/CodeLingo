package com.yi.myapplication.data.entity.codelingo


import com.google.gson.annotations.SerializedName

data class VerifyCodeRequest(
  @SerializedName("email")
  val email: String,
  @SerializedName("code")
  val code: String
)

data class ForgotPasswordRequest(
  @SerializedName("email")
  val email: String
)