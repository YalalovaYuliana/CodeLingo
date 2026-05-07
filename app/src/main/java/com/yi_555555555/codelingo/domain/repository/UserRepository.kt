package com.yi_555555555.codelingo.domain.repository

import com.yi_555555555.codelingo.domain.model.AccessToken
import com.yi_555555555.codelingo.domain.model.Achievment
import com.yi_555555555.codelingo.domain.model.Course
import com.yi_555555555.codelingo.domain.model.CourseDetails
import com.yi_555555555.codelingo.domain.model.Level
import com.yi_555555555.codelingo.domain.model.LoginCredentials
import com.yi_555555555.codelingo.domain.model.RegisterCredentials
import com.yi_555555555.codelingo.domain.model.SubmitAnswer
import com.yi_555555555.codelingo.domain.model.Task
import com.yi_555555555.codelingo.domain.model.User
import kotlinx.coroutines.flow.StateFlow
import java.io.File

interface UserRepository {
  val cacheFlow: StateFlow<Cache>
  suspend fun register(registerCredentials: RegisterCredentials): AccessToken
  suspend fun login(loginCredentials: LoginCredentials): AccessToken
  suspend fun getUser(): User
  suspend fun changeProfile(
    username: String?,
    file: File?,
    mimeType: String
  )

  suspend fun getUserCourseId(): Int?
  suspend fun readAccessToken(): AccessToken?
  suspend fun writeAccessToken(accessToken: AccessToken)
  suspend fun logout()
  suspend fun readCourseId(): Int?
  suspend fun writeCourseId(courseId: Int)
  suspend fun getCourses(): List<Course>
  suspend fun startCourse(courseId: Int)
  suspend fun getUserCourseLevels(courseId: Int): List<Level>
  suspend fun getCourseDetails(courseId: Int): CourseDetails
  suspend fun getLevelTheory(levelId: Int): String
  suspend fun getLevelTasks(levelId: Int): List<Task>
  suspend fun submitTask(
    taskId: Int,
    answers: List<Any>
  ): SubmitAnswer

  suspend fun submitCodeTask(
    taskId: Int,
    answers: String
  ): SubmitAnswer

  suspend fun completeTask(levelId: Int): Int
  suspend fun getMyAchievments(): List<Achievment>
}

data class Cache(
  val accessToken: AccessToken? = null,
  val user: User? = null,
  val selectedCourseId: Int? = null,
  val courseDetails: CourseDetails? = null,
  val courses: List<Course>? = null,
  val levels: List<Level>? = null,
  val achievments: List<Achievment>? = null
)