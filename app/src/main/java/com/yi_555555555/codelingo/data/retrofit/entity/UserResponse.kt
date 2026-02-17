package com.yi.myapplication.data.entity.codelingo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("email")
    val email: String,
    @SerialName("id")
    val id: Int,
    @SerialName("picture_link")
    val pictureLink: String,
    @SerialName("streak")
    val streak: Int,
    @SerialName("username")
    val username: String,
    @SerialName("xp")
    val xp: Int
)