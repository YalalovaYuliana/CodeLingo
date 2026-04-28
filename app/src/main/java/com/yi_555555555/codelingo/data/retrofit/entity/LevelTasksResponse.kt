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
    @SerializedName("num_in_order")
    val numInOrder: Int,
    @SerializedName("options")
    val options: List<OptionResponse>? = null,
    @SerializedName("gaps")
    val gaps: List<GapResponse>? = null,
    @SerializedName("hint")
    val hint: String? = null
  ) {
    data class OptionResponse(
      @SerializedName("id")
      val id: Int,
      @SerializedName("text")
      val text: String,
      @SerializedName("is_correct")
      val isCorrect: String
    )

    data class GapResponse(
      @SerializedName("template")
      val template: String,
      @SerializedName("answer")
      val answer: String
    )
  }
}