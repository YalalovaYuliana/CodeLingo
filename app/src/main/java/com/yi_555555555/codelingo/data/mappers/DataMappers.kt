package com.yi_555555555.codelingo.data.mappers

import com.yi_555555555.codelingo.data.retrofit.entity.AccessTokenData
import com.yi_555555555.codelingo.data.retrofit.entity.CourseDetailsResponse
import com.yi_555555555.codelingo.data.retrofit.entity.CourseResponse
import com.yi_555555555.codelingo.data.retrofit.entity.SubmitResponse
import com.yi_555555555.codelingo.data.retrofit.entity.TaskResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserLevelResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserResponse
import com.yi_555555555.codelingo.data.room.entity.AccessTokenDbModel
import com.yi_555555555.codelingo.domain.model.AccessToken
import com.yi_555555555.codelingo.domain.model.Course
import com.yi_555555555.codelingo.domain.model.CourseDetails
import com.yi_555555555.codelingo.domain.model.Level
import com.yi_555555555.codelingo.domain.model.SubmitAnswer
import com.yi_555555555.codelingo.domain.model.Task
import com.yi_555555555.codelingo.domain.model.TaskType
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

fun CourseResponse.toDomainModel(): Course {
  return Course(
    id = id,
    title = title,
    description = description
  )
}

fun UserLevelResponse.toDomainModel(): Level {
  return Level(
    id = id,
    title = title,
    isComplete = isComplete
  )
}

private fun CourseDetailsResponse.CourseLevelResponse.toDomainModel(): CourseDetails.CourseLevel {
  return CourseDetails.CourseLevel(
    id = id,
    title = title,
    xp = xp
  )
}

fun CourseDetailsResponse.toDomainModel(): CourseDetails {
  return CourseDetails(
    course = Course(
      id = id,
      title = title,
      description = description
    ),
    levels = levels.map { it.toDomainModel() }
  )
}

fun TaskResponse.toDomainModel(): Task {
  return Task(
    id = id,
    title = title,
    description = description,
    type = TaskType.fromValue(taskType) ?: error("missing task type"),
    numInOrder = numInOrder,
    options = options?.map { it.toDomainModel() },
    gaps = gaps?.map { it.toDomainModel() },
    code = code?.firstOrNull()?.toDomainModel(),
    hint = hint
  )
}

private fun TaskResponse.OptionResponse.toDomainModel(): Task.Option {
  return Task.Option(
    id = id,
    text = text
  )
}

private fun TaskResponse.CodeResponse.toDomainModel(): Task.Code {
  return Task.Code(
    id = id,
    language = language
  )
}

private fun TaskResponse.GapResponse.toDomainModel(): Task.Gap {
  return Task.Gap(
    template = template
  )
}

fun SubmitResponse.toDomainModel(): SubmitAnswer {
  return SubmitAnswer(
    isCorrect = isCorrect,
    correctOptions = correctOptions,
    correctAnswers = correctAnswers
  )
}

