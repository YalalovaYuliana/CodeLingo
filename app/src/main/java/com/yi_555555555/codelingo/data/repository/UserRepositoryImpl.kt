package com.yi_555555555.codelingo.data.repository

import com.yi.myapplication.data.entity.codelingo.LoginRequest
import com.yi.myapplication.data.entity.codelingo.RegisterRequest
import com.yi_555555555.codelingo.data.mappers.toAccessTokenDb
import com.yi_555555555.codelingo.data.mappers.toAccessTokenDomain
import com.yi_555555555.codelingo.data.mappers.toCourseDomain
import com.yi_555555555.codelingo.data.mappers.toUser
import com.yi_555555555.codelingo.data.retrofit.UserApi
import com.yi_555555555.codelingo.data.room.UserDataBase
import com.yi_555555555.codelingo.data.room.entity.CourseDbModel
import com.yi_555555555.codelingo.domain.model.AccessToken
import com.yi_555555555.codelingo.domain.model.Course
import com.yi_555555555.codelingo.domain.model.LoginCredentials
import com.yi_555555555.codelingo.domain.model.RegisterCredentials
import com.yi_555555555.codelingo.domain.model.User
import com.yi_555555555.codelingo.domain.repository.Cache
import com.yi_555555555.codelingo.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserRepositoryImpl(
  private val userApi: UserApi,
  private val userDataBase: UserDataBase,
) : UserRepository {

  private val _cacheFlow = MutableStateFlow(Cache())
  override val cacheFlow = _cacheFlow.asStateFlow()

  override suspend fun register(registerCredentials: RegisterCredentials): AccessToken {
    return userApi.register(
      RegisterRequest(
        username = registerCredentials.username,
        email = registerCredentials.email,
        password = registerCredentials.password
      )
    ).toAccessTokenDomain()
  }

  override suspend fun login(loginCredentials: LoginCredentials): AccessToken {
    return userApi.login(
      LoginRequest(
        email = loginCredentials.email,
        password = loginCredentials.password
      )
    ).toAccessTokenDomain()
  }

  override suspend fun getUser(): User {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    return accessToken?.let {
      val user = userApi.getUser(accessToken).toUser()
      _cacheFlow.update {
        it.copy(
          user = user
        )
      }
      user
    } ?: throw Exception("missing access token")
  }

  override suspend fun getUserCourseId(): Int? {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    if (accessToken != null) {
      val courseId = userApi.getUserCourseId(accessToken).courseId
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
    val courses = userApi.getCourses().map {
      it.toCourseDomain()
    }
    _cacheFlow.update {
      it.copy(
        courses = courses
      )
    }
    return courses
  }

  override suspend fun startCourse(courseId: Int) {
    val accessToken = _cacheFlow.value.accessToken?.accessTokenWithType
    return accessToken?.let {
      userApi.startCourse(
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
}