package com.yi_555555555.codelingo.domain.model


data class Task(
  val id: Int,
  val title: String,
  val description: String,
  val type: TaskType,
  val numInOrder: Int,
  val options: List<Option>?,
  val gaps: List<Gap>?,
  val code: Code?,
  val hint: String?,
) {
  data class Option(
    val id: Int,
    val text: String,
    val isChosen: Boolean = false,
    val isError: Boolean = false
  )

  data class Gap(
    val template: String,
    val userAnswer: String = "",
    val isError: Boolean = false
  )

  data class Code(
    val id: Int,
    val userAnswer: String = "",
    val language: String,
    val isError: Boolean = false
  )
}


enum class TaskType(val value: String) {
  Choice("choice"),
  Gap("gap"),
  Code("code");

  companion object {
    fun fromValue(value: String): TaskType? {
      return entries.find { it.value == value }
    }
  }
}