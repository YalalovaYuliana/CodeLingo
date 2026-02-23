package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class AccessTokenData(
  @SerializedName("access_token")
  val accessToken: String,
  @SerializedName("token_type")
  val tokenType: String
)