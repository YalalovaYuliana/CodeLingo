package com.yi_555555555.codelingo.data.repository

import com.yi.myapplication.data.entity.codelingo.ForgotPasswordRequest
import com.yi.myapplication.data.entity.codelingo.LoginRequest
import com.yi.myapplication.data.entity.codelingo.RegisterRequest
import com.yi.myapplication.data.entity.codelingo.ResetPasswordRequest
import com.yi.myapplication.data.entity.codelingo.SubmitCodeRequest
import com.yi.myapplication.data.entity.codelingo.SubmitRequest
import com.yi.myapplication.data.entity.codelingo.VerifyCodeRequest
import com.yi_555555555.codelingo.data.mappers.toAccessTokenDb
import com.yi_555555555.codelingo.data.mappers.toAccessTokenDomain
import com.yi_555555555.codelingo.data.mappers.toDomainModel
import com.yi_555555555.codelingo.data.mappers.toUser
import com.yi_555555555.codelingo.data.retrofit.CodeLingoApi
import com.yi_555555555.codelingo.data.room.UserDataBase
import com.yi_555555555.codelingo.data.room.entity.CourseDbModel
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
import com.yi_555555555.codelingo.domain.repository.Cache
import com.yi_555555555.codelingo.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserRepositoryImpl(
  private val codeLingoApi: CodeLingoApi,
  private val userDataBase: UserDataBase,
) : UserRepository {

  private val _cacheFlow = MutableStateFlow(Cache())
  override val cacheFlow = _cacheFlow.asStateFlow()

  override suspend fun register(registerCredentials: RegisterCredentials): AccessToken {
    return codeLingoApi.register(
      RegisterRequest(
        username = registerCredentials.username,
        email = registerCredentials.email,
        password = registerCredentials.password
      )
    ).toAccessTokenDomain()
  }

  override suspend fun login(loginCredentials: LoginCredentials): AccessToken {
    return codeLingoApi.login(
      LoginRequest(
        email = loginCredentials.email,
        password = loginCredentials.password
      )
    ).toAccessTokenDomain()
  }

  override suspend fun forgotPassword(email: String) {
    codeLingoApi.forgotPassword(ForgotPasswordRequest(email))
  }

  override suspend fun verifyForgotPasswordCode(email: String, code: String) {
    codeLingoApi.verifyForgotPasswordCode(
      VerifyCodeRequest(
        email = email,
        code = code
      )
    )
  }

  override suspend fun resetPassword(
    email: String,
    code: String,
    newPassword: String
  ) {
    codeLingoApi.resetPassword(
      ResetPasswordRequest(
        email = email,
        code = code,
        newPassword = newPassword
      )
    )
  }

  override suspend fun getUser(): User {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    return accessToken?.let {
      val user = this@UserRepositoryImpl.codeLingoApi.getUser(accessToken).toUser()
      _cacheFlow.update {
        it.copy(
          user = user
        )
      }
      user
    } ?: throw Exception("missing access token")
  }

  override suspend fun changeProfile(
    username: String?,
    file: File?,
    mimeType: String
  ) {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    if (accessToken != null) {
      val updatedUserData = codeLingoApi.changeUserData(
        authToken = accessToken,
        username = username?.toRequestBody("text/plain".toMediaTypeOrNull()),
        file = file?.let {
          val requestFile = RequestBody.create(
            mimeType.toMediaTypeOrNull(),
            it
          )
          MultipartBody.Part.createFormData(
            "file",
            it.name,
            requestFile
          )
        }
      )
      _cacheFlow.update { cache ->
        cache.copy(
          user = cache.user?.copy(
            username = updatedUserData.username,
            pictureLink = updatedUserData.pictureLink
          )
        )
      }
    } else throw Exception("missing access token")
  }

  override suspend fun getUserCourseId(): Int? {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    if (accessToken != null) {
      val courseId = codeLingoApi.getUserCourseId(accessToken).courseId
      _cacheFlow.update {
        it.copy(
          selectedCourseId = courseId
        )
      }
      return courseId
    } else throw Exception("missing access token")
  }

  override suspend fun getUserCourseStats(): Int? {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    if (accessToken != null) {
      val courseId = codeLingoApi.getUserCourseId(accessToken).courseId
      _cacheFlow.update {
        it.copy(
          selectedCourseId = courseId
        )
      }
      return courseId
    } else throw Exception("missing access token")
  }

  override suspend fun readAccessToken(): AccessToken? {
    val accessToken = userDataBase.userDao().getToken()?.toAccessTokenDomain()
    _cacheFlow.update {
      it.copy(
        accessToken = accessToken
      )
    }
    return accessToken
  }

  override suspend fun writeAccessToken(accessToken: AccessToken) {
    userDataBase.userDao().insertToken(accessToken.toAccessTokenDb())
    _cacheFlow.update {
      it.copy(
        accessToken = accessToken
      )
    }
  }

  override suspend fun logout() {
    userDataBase.userDao().deleteToken()
    userDataBase.userDao().deleteCourseId()
    _cacheFlow.update { Cache() }
  }

  override suspend fun deleteAccount() {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    accessToken?.let {
      codeLingoApi.deleteUser(
        authToken = accessToken
      )
      userDataBase.userDao().deleteToken()
      userDataBase.userDao().deleteCourseId()
      _cacheFlow.update { Cache() }
    } ?: throw Exception("missing access token")
  }

  override suspend fun readCourseId(): Int? {
    val courseId = userDataBase.userDao().getCourseId()?.selectedCourseId
    _cacheFlow.update {
      it.copy(
        selectedCourseId = courseId
      )
    }
    return courseId
  }

  override suspend fun writeCourseId(courseId: Int) {
    userDataBase.userDao().insertCourseId(
      CourseDbModel(
        selectedCourseId = courseId
      )
    )
    _cacheFlow.update {
      it.copy(
        selectedCourseId = courseId
      )
    }
  }

  override suspend fun getCourses(): List<Course> {
    return codeLingoApi.getCourses().map {
      it.toDomainModel()
    }.also {
      _cacheFlow.update { cache ->
        cache.copy(
          courses = it
        )
      }
    }
  }

  override suspend fun startCourse(courseId: Int) {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    accessToken?.let {
      codeLingoApi.startCourse(
        authToken = accessToken,
        courseId = courseId
      )
      _cacheFlow.update {
        it.copy(
          selectedCourseId = courseId
        )
      }
    } ?: throw Exception("missing access token")
  }

  override suspend fun getCourseDetails(courseId: Int): CourseDetails {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    return accessToken?.let {
      val courseStats = codeLingoApi.getUserCourseStats(accessToken).first()
      codeLingoApi.getCourseDetails(courseId).toDomainModel(
        progress = courseStats.progress,
        isComplete = courseStats.isComplete,
        startedAt = courseStats.startedAt
      ).also {
        _cacheFlow.update { cache ->
          cache.copy(
            courseDetails = it
          )
        }
      }
    } ?: throw Exception("missing access token")
  }

  override suspend fun getLevelTheory(levelId: Int): String {
    return codeLingoApi.getLevelTheory(levelId).theory
  }

  override suspend fun getLevelTasks(levelId: Int): List<Task> {
    return codeLingoApi.getTasks(levelId).map { it.toDomainModel() }
  }

  override suspend fun completeTask(levelId: Int): Int {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    return accessToken?.let {
      val response = codeLingoApi.completeTask(
        authToken = accessToken,
        levelId = levelId
      )

      _cacheFlow.update { cache ->
        cache.copy(
          user = cache.user?.copy(
            streak = response.streak,
            xp = response.totalXp
          ),
          levels = cache.levels?.map { level ->
            if (levelId == level.id) {
              level.copy(
                isComplete = true
              )
            } else level
          },
          courseDetails = cache.courseDetails?.copy(
            progress = response.courseProgress
          )
        )
      }

      response.xpAdded
    } ?: throw Exception("missing access token")
  }

  override suspend fun submitTask(
    taskId: Int,
    answers: List<Any>
  ): SubmitAnswer {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    return accessToken?.let {
      codeLingoApi.submitTask(
        authToken = accessToken,
        taskId = taskId,
        submitRequest = SubmitRequest(answers = answers)
      ).toDomainModel()
    } ?: throw Exception("missing access token")
  }

  override suspend fun submitCodeTask(
    taskId: Int,
    answers: String
  ): SubmitAnswer {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    return accessToken?.let {
      codeLingoApi.submitCodeTask(
        authToken = accessToken,
        taskId = taskId,
        submitRequest = SubmitCodeRequest(answers = answers)
      ).toDomainModel()
    } ?: throw Exception("missing access token")
  }

  override suspend fun getUserCourseLevels(courseId: Int): List<Level> {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    return accessToken?.let {
      codeLingoApi.getLevels(
        authToken = accessToken,
        courseId = courseId
      ).map { it.toDomainModel() }.also {
        _cacheFlow.update { cache ->
          cache.copy(
            levels = it
          )
        }
      }
    } ?: throw Exception("missing access token")
  }

  override suspend fun getMyAchievments(): List<Achievment> {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    return accessToken?.let {
      codeLingoApi.getMyAchievments(
        authToken = accessToken
      ).map { it.toDomainModel() }.also {
        _cacheFlow.update { cache ->
          cache.copy(
            achievments = it
          )
        }
      }
    } ?: throw Exception("missing access token")
  }
}