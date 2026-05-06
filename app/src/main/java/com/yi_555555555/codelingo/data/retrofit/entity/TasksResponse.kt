package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class TaskResponse(
  @SerializedName("id")
  val id: Int,
  @SerializedName("title")
  val title: String,
  @SerializedName("description")
  val description: String,
  @SerializedName("task_type")
  val taskType: String,
  @SerializedName("num_in_order")
  val numInOrder: Int,
  @SerializedName("options")
  val options: List<OptionResponse>? = null,
  @SerializedName("gaps")
  val gaps: List<GapResponse>? = null,
  @SerializedName("code")
  val code: List<CodeResponse>? = null,
  @SerializedName("hint")
  val hint: String? = null
) {
  data class OptionResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String
  )

  data class GapResponse(
    @SerializedName("template")
    val template: String
  )

  data class CodeResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("language")
    val language: String
  )
}