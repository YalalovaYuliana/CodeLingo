package com.yi.myapplication.data.entity.codelingo


import com.google.gson.annotations.SerializedName

data class SubmitRequest(
  @SerializedName("answers")
  val answers: List<Any>
)