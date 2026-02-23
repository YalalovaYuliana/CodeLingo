package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class CoursersResponse(
  val coursers: List<CourseData>
) {
  data class CourseData(
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
  )
}