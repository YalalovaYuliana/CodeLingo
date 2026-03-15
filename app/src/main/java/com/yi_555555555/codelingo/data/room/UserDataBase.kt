package com.yi_555555555.codelingo.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yi_555555555.codelingo.data.room.entity.AccessTokenDbModel
import com.yi_555555555.codelingo.data.room.entity.CourseDbModel

@Database(
  entities = [AccessTokenDbModel::class, CourseDbModel::class],
  version = 1,
  exportSchema = false
)
abstract class UserDataBase : RoomDatabase() {

  abstract fun userDao(): UserDao
}