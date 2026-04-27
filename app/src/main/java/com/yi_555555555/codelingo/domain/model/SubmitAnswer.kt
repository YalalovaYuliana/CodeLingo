package com.yi_555555555.codelingo.domain.model


data class SubmitAnswer(
  val isCorrect: Boolean,
  val correctOptions: List<Int>? = null,
  val correctAnswers: List<String>? = null
)