package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class CourseResponse(
  @SerializedName("id")
  val id: Int,
  @SerializedName("title")
  val title: String,
  @SerializedName("description")
  val description: String?
)