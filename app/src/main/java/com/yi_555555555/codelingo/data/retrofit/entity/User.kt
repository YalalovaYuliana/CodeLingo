package com.yi_555555555.codelingo.data.retrofit.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
  @SerialName("accessToken")
  val accessToken: String,
  @SerialName("email")
  val email: String,
  @SerialName("firstName")
  val firstName: String,
  @SerialName("gender")
  val gender: String,
  @SerialName("id")
  val id: Int,
  @SerialName("image")
  val image: String,
  @SerialName("lastName")
  val lastName: String,
  @SerialName("refreshToken")
  val refreshToken: String,
  @SerialName("username")
  val username: String
)