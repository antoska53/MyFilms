package ru.myacademyhomework.myfilms.data

import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    val id: Int,
    val name: String,
    val picture: String
)