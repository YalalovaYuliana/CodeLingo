package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class AchievmentResponse(
  @SerializedName("id")
  val id: Int,
  @SerializedName("title")
  val title: String,
  @SerializedName("description")
  val description: String?,
  @SerializedName("icon")
  val iconUrl: String,
  @SerializedName("received")
  val received: Boolean

)