package com.yi_555555555.codelingo.domain.model


data class Achievment(
  val id: Int,
  val title: String,
  val description: String?,
  val iconUrl: String,
  val received: Boolean
)