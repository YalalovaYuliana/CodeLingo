package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class UserCourseResponse(
  @SerializedName("course_id")
  val courseId: Int?
)

data class UserCourseStatsResponse(
  @SerializedName("id")
  val id: Int,
  @SerializedName("course_name")
  val courseName: String,
  @SerializedName("progress")
  val progress: Double,
  @SerializedName("is_complete")
  val isComplete: Boolean,
  @SerializedName("started_at")
  val startedAt: String
)