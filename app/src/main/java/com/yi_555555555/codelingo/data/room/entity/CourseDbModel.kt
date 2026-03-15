package com.yi_555555555.codelingo.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course")
data class CourseDbModel(
  @PrimaryKey(autoGenerate = false)
  val id: Int = 1,
  val selectedCourseId: Int
)
