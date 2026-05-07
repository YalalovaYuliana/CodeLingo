package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class CompleteTaskResponse(
  @SerializedName("xp_added")
  val xpAdded: Int,
  @SerializedName("total_xp")
  val totalXp: Int,
  @SerializedName("streak")
  val streak: Int,
  @SerializedName("course_progress")
  val courseProgress: Double
)