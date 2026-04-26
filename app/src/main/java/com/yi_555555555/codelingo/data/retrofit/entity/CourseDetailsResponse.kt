package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class CourseDetailsResponse(
  @SerializedName("id")
  val id: Int,
  @SerializedName("title")
  val title: String,
  @SerializedName("description")
  val description: String?,
  @SerializedName("levels")
  val levels: List<CourseLevelResponse>
) {
  data class CourseLevelResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("xp")
    val xp: Int
  )
}

