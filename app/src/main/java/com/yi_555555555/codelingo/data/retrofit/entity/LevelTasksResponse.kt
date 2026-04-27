package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class LevelTasksResponse(
  @SerializedName("level_id")
  val levelId: Int,
  @SerializedName("tasks")
  val tasks: List<TaskResponse>
) {
  data class TaskResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("task_type")
    val type: String,
    @SerializedName("hint")
    val hint: String?
  )
}