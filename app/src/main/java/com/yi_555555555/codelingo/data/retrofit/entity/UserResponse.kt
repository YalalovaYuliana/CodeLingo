package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class UserResponse(
  @SerializedName("id")
  val id: Int,
  @SerializedName("username")
  val username: String,
  @SerializedName("email")
  val email: String,
  @SerializedName("picture_link")
  val pictureLink: String?,
  @SerializedName("streak")
  val streak: Int,
  @SerializedName("xp")
  val xp: Int
)

data class UserUpdateResponse(
  @SerializedName("id")
  val id: Int,
  @SerializedName("username")
  val username: String,
  @SerializedName("email")
  val email: String,
  @SerializedName("picture_link")
  val pictureLink: String?
)