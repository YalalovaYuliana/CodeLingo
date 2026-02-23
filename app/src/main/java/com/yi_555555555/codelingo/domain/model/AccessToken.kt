package com.yi_555555555.codelingo.domain.model

data class AccessToken(
  val accessToken: String,
  val tokenType: String
) {
  val accessTokenWithType: String
    get() = "$tokenType $accessToken"
}