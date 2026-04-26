package com.yi_555555555.codelingo.domain.model


data class CourseDetails(
  val course: Course,
  val levels: List<CourseLevel>
) {
  data class CourseLevel(
    val id: Int,
    val title: String,
    val xp: Int
  )
}