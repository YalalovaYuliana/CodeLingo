package com.yi_555555555.codelingo.data.retrofit

import com.yi.myapplication.data.entity.codelingo.LoginRequest
import com.yi.myapplication.data.entity.codelingo.RegisterRequest
import com.yi_555555555.codelingo.data.retrofit.entity.AccessTokenData
import com.yi_555555555.codelingo.data.retrofit.entity.CourseDetailsResponse
import com.yi_555555555.codelingo.data.retrofit.entity.CourseResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserCourseResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserLevelResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
  @POST("auth/register")
  suspend fun register(@Body registerRequest: RegisterRequest): AccessTokenData

  @POST("auth/login")
  suspend fun login(@Body loginRequest: LoginRequest): AccessTokenData

  @GET("users/me")
  suspend fun getUser(@Header("Authorization") authToken: String): UserResponse

  @GET("users/course")
  suspend fun getUserCourseId(@Header("Authorization") authToken: String): UserCourseResponse

  @GET("courses")
  suspend fun getCourses(): List<CourseResponse>

  @GET("courses/{course_id}")
  suspend fun getCourseDetails(
    @Path("course_id") courseId: Int
  ): CourseDetailsResponse

  @POST("courses/{course_id}/start")
  suspend fun startCourse(
    @Header("Authorization") authToken: String,
    @Path("course_id") courseId: Int
  )

  @GET("courses/{course_id}/levels")
  suspend fun getLevels(
    @Header("Authorization") authToken: String,
    @Path("course_id") courseId: Int
  ): List<UserLevelResponse>
}