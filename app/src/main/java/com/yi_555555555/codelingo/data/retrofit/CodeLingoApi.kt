package com.yi_555555555.codelingo.data.retrofit

import com.yi.myapplication.data.entity.codelingo.ForgotPasswordRequest
import com.yi.myapplication.data.entity.codelingo.LoginRequest
import com.yi.myapplication.data.entity.codelingo.RegisterRequest
import com.yi.myapplication.data.entity.codelingo.ResetPasswordRequest
import com.yi.myapplication.data.entity.codelingo.SubmitCodeRequest
import com.yi.myapplication.data.entity.codelingo.SubmitRequest
import com.yi.myapplication.data.entity.codelingo.VerifyCodeRequest
import com.yi_555555555.codelingo.data.retrofit.entity.AccessTokenData
import com.yi_555555555.codelingo.data.retrofit.entity.AchievmentResponse
import com.yi_555555555.codelingo.data.retrofit.entity.CompleteTaskResponse
import com.yi_555555555.codelingo.data.retrofit.entity.CourseDetailsResponse
import com.yi_555555555.codelingo.data.retrofit.entity.CourseResponse
import com.yi_555555555.codelingo.data.retrofit.entity.SubmitResponse
import com.yi_555555555.codelingo.data.retrofit.entity.TaskResponse
import com.yi_555555555.codelingo.data.retrofit.entity.TheoryResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserCourseResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserCourseStatsResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserLevelResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserUpdateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface CodeLingoApi {
  @POST("auth/register")
  suspend fun register(@Body registerRequest: RegisterRequest)

  @POST("auth/verify-email")
  suspend fun verifyEmail(@Body verifyCodeRequest: VerifyCodeRequest): AccessTokenData

  @POST("auth/login")
  suspend fun login(@Body loginRequest: LoginRequest): AccessTokenData

  @POST("auth/forgot-password")
  suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest)

  @POST("auth/verify-code")
  suspend fun verifyResetPasswordCode(@Body verifyCodeRequest: VerifyCodeRequest)

  @POST("auth/reset-password")
  suspend fun resetPassword(@Body resetRequest: ResetPasswordRequest)

  @GET("users/me")
  suspend fun getUser(@Header("Authorization") authToken: String): UserResponse

  @Multipart
  @PATCH("users/change-me")
  suspend fun changeUserData(
    @Header("Authorization") authToken: String,
    @Part("username") username: RequestBody?,
    @Part file: MultipartBody.Part?
  ): UserUpdateResponse


  @DELETE("users/delete-me")
  suspend fun deleteUser(
    @Header("Authorization") authToken: String
  )

  @GET("users/course")
  suspend fun getUserCourseId(@Header("Authorization") authToken: String): UserCourseResponse

  @GET("users/course-stat")
  suspend fun getUserCourseStats(@Header("Authorization") authToken: String): List<UserCourseStatsResponse>

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

  @GET("levels/{level_id}/theory")
  suspend fun getLevelTheory(
    @Path("level_id") levelId: Int
  ): TheoryResponse

  @GET("levels/{level_id}/tasks")
  suspend fun getTasks(
    @Path("level_id") levelId: Int
  ): List<TaskResponse>

  @POST("tasks/{task_id}/submit")
  suspend fun submitTask(
    @Header("Authorization") authToken: String,
    @Path("task_id") taskId: Int,
    @Body submitRequest: SubmitRequest
  ): SubmitResponse

  @POST("tasks/{task_id}/submit")
  suspend fun submitCodeTask(
    @Header("Authorization") authToken: String,
    @Path("task_id") taskId: Int,
    @Body submitRequest: SubmitCodeRequest
  ): SubmitResponse

  @POST("levels/{level_id}/complete")
  suspend fun completeTask(
    @Header("Authorization") authToken: String,
    @Path("level_id") levelId: Int
  ): CompleteTaskResponse

  @GET("achievments/my")
  suspend fun getMyAchievments(
    @Header("Authorization") authToken: String
  ): List<AchievmentResponse>
}