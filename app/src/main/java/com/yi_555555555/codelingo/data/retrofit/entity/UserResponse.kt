package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class UserResponse(
  @SerializedName("email")
  val email: String,
  @SerializedName("id")
  val id: Int,
  @SerializedName("picture_link")
  val pictureLink: String?,
  @SerializedName("streak")
  val streak: Int,
  @SerializedName("username")
  val username: String,
  @SerializedName("xp")
  val xp: Int
)