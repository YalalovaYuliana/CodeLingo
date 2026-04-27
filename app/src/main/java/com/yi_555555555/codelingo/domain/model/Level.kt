package com.yi_555555555.codelingo.domain.model


data class Level(
  val levelId: Int,
  val tasks: List<Task>
) {
  data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val type: TaskType,
    val hint: String?
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