package com.yi_555555555.codelingo.data.mappers

import com.yi_555555555.codelingo.data.retrofit.entity.AccessTokenData
import com.yi_555555555.codelingo.data.retrofit.entity.CourseDetailsResponse
import com.yi_555555555.codelingo.data.retrofit.entity.CourseResponse
import com.yi_555555555.codelingo.data.retrofit.entity.LevelTasksResponse
import com.yi_555555555.codelingo.data.retrofit.entity.SubmitResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserLevelResponse
import com.yi_555555555.codelingo.data.retrofit.entity.UserResponse
import com.yi_555555555.codelingo.data.room.entity.AccessTokenDbModel
import com.yi_555555555.codelingo.domain.model.AccessToken
import com.yi_555555555.codelingo.domain.model.Course
import com.yi_555555555.codelingo.domain.model.CourseDetails
import com.yi_555555555.codelingo.domain.model.Level
import com.yi_555555555.codelingo.domain.model.SubmitAnswer
import com.yi_555555555.codelingo.domain.model.TaskType
import com.yi_555555555.codelingo.domain.model.User
import com.yi_555555555.codelingo.domain.model.UserLevel

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

fun UserLevelResponse.toDomainModel(): UserLevel {
  return UserLevel(
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

fun LevelTasksResponse.toDomainModel(): Level {
  return Level(
    levelId = levelId,
    tasks = tasks.map { it.toDomainModel() }
  )
}

fun LevelTasksResponse.TaskResponse.toDomainModel(): Level.Task {
  return Level.Task(
    id = id,
    title = title,
    description = description,
    type = TaskType.fromValue(type) ?: error("missing task type"),
    numInOrder = numInOrder,
    options = options?.map { it.toDomainModel() },
    gaps = gaps?.map { it.toDomainModel() },
    hint = hint
  )
}

private fun LevelTasksResponse.TaskResponse.OptionResponse.toDomainModel(): Level.Task.Option {
  return Level.Task.Option(
    id = id,
    text = text,
    isCorrect = isCorrect
  )
}

private fun LevelTasksResponse.TaskResponse.GapResponse.toDomainModel(): Level.Task.Gap {
  return Level.Task.Gap(
    template = template,
    answer = answer
  )
}

fun SubmitResponse.toDomainModel(): SubmitAnswer {
  return SubmitAnswer(
    isCorrect = isCorrect,
    correctOptions = correctOptions,
    correctAnswers = correctAnswers
  )
}

