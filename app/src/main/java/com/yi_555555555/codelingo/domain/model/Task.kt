package com.yi_555555555.codelingo.domain.model


data class Task(
  val id: Int,
  val title: String,
  val description: String,
  val type: TaskType,
  val numInOrder: Int,
  val options: List<Option>?,
  val gaps: List<Gap>?,
  val hint: String?
) {
  data class Option(
    val id: Int,
    val text: String
  )

  data class Gap(
    val template: String,
    val answer: String
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