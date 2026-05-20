package com.yi_555555555.codelingo.data.retrofit.entity


import com.google.gson.annotations.SerializedName

data class HandleGoogleCallbackRequest(
  @SerializedName("code")
  val googleResponseCode: String
)