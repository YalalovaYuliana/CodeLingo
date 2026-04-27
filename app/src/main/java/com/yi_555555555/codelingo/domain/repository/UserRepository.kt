package com.yi_555555555.codelingo.domain.repository

import com.yi_555555555.codelingo.domain.model.AccessToken
import com.yi_555555555.codelingo.domain.model.Course
import com.yi_555555555.codelingo.domain.model.CourseDetails
import com.yi_555555555.codelingo.domain.model.Level
import com.yi_555555555.codelingo.domain.model.LoginCredentials
import com.yi_555555555.codelingo.domain.model.RegisterCredentials
import com.yi_555555555.codelingo.domain.model.SubmitAnswer
import com.yi_555555555.codelingo.domain.model.User
import com.yi_555555555.codelingo.domain.model.UserLevel
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
  val cacheFlow: StateFlow<Cache>
  suspend fun register(registerCredentials: RegisterCredentials): AccessToken
  suspend fun login(loginCredentials: LoginCredentials): AccessToken
  suspend fun getUser(): User
  suspend fun getUserCourseId(): Int?
  suspend fun readAccessToken(): AccessToken?
  suspend fun writeAccessToken(accessToken: AccessToken)
  suspend fun logout()
  suspend fun readCourseId(): Int?
  suspend fun writeCourseId(courseId: Int)
  suspend fun getCourses(): List<Course>
  suspend fun startCourse(courseId: Int)
  suspend fun getUserCourseLevels(courseId: Int): List<UserLevel>
  suspend fun getCourseDetails(courseId: Int): CourseDetails
  suspend fun getLevelTheory(levelId: Int): String
  suspend fun getLevelTasks(levelId: Int): Level
  suspend fun submitTask(
    taskId: Int,
    answers: List<Any>
  ): SubmitAnswer
}

data class Cache(
  val accessToken: AccessToken? = null,
  val user: User? = null,
  val selectedCourseId: Int? = null,
  val courseDetails: CourseDetails? = null,
  val courses: List<Course>? = null,
  val levels: List<UserLevel>? = null
)