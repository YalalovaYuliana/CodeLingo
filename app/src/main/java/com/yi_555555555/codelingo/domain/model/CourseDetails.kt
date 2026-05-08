package com.yi_555555555.codelingo.domain.model


data class CourseDetails(
  val course: Course,
  val levels: List<CourseLevel>,
  val progress: Double,
  val isComplete: Boolean,
  val startedAt: String
) {
  data class CourseLevel(
    val id: Int,
    val title: String,
    val xp: Int
  )
}