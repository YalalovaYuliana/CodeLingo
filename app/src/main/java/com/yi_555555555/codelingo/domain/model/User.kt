package com.yi_555555555.codelingo.domain.model

data class User(
  val email: String,
  val id: Int,
  val pictureLink: String?,
  val streak: Int,
  val username: String,
  val xp: Int
)
