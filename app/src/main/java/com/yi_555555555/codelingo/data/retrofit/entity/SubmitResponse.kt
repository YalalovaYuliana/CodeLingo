package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class SubmitResponse(
  @SerializedName("is_correct")
  val isCorrect: Boolean,
  @SerializedName("correct_options")
  val correctOptions: List<Int>? = null,
  @SerializedName("correct_answers")
  val correctAnswers: List<String>? = null
)