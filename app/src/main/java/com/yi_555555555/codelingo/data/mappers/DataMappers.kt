package com.yi_555555555.codelingo.data.mappers

import com.yi_555555555.codelingo.data.retrofit.entity.AccessTokenData
import com.yi_555555555.codelingo.data.retrofit.entity.CoursersResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserResponse
import com.yi_555555555.codelingo.data.room.entity.AccessTokenDbModel
import com.yi_555555555.codelingo.domain.model.AccessToken
import com.yi_555555555.codelingo.domain.model.Course
import com.yi_555555555.codelingo.domain.model.User

fun UserResponse.toUser(): User {
  return User(
    email = email,
    id = id,
    pictureLink = pictureLink,
    streak = streak,
    username = username,
    xp = xp
  )
}

fun AccessTokenDbModel.toAccessTokenDomain(): AccessToken {
  return AccessToken(
    accessToken = this.accessToken,
    tokenType = this.tokenType
  )
}

fun AccessTokenData.toAccessTokenDomain(): AccessToken {
  return AccessToken(
    accessToken = this.accessToken,
    tokenType = this.tokenType
  )
}

fun AccessToken.toAccessTokenDb(): AccessTokenDbModel {
  return AccessTokenDbModel(
    accessToken = this.accessToken,
    tokenType = this.tokenType
  )
}

fun CoursersResponse.CourseData.toCourseDomain(): Course {
  return Course(
    id = id,
    title = title,
    description = description
  )
}
