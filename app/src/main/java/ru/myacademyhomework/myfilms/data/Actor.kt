package ru.myacademyhomework.myfilms.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String?,

    @SerialName("profile_path")
    val picture: String?
)