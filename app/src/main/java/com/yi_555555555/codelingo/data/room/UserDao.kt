package com.yi_555555555.codelingo.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.yi_555555555.codelingo.data.room.entity.AccessTokenDbModel
import com.yi_555555555.codelingo.data.room.entity.CourseDbModel

@Dao
interface UserDao {

  @Insert(onConflict = REPLACE)
  suspend fun insertToken(accessToken: AccessTokenDbModel)

  @Query("SELECT * FROM access_token WHERE id = 1")
  suspend fun getToken(): AccessTokenDbModel?

  @Query("DELETE FROM access_token")
  suspend fun deleteToken()

  @Insert(onConflict = REPLACE)
  suspend fun insertCourseId(courseId: CourseDbModel)

  @Query("SELECT * FROM course WHERE id = 1")
  suspend fun getCourseId(): CourseDbModel?

  @Query("DELETE FROM access_token")
  suspend fun deleteCourseId()
}