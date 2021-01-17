package ru.myacademyhomework.myfilms.data

import kotlinx.serialization.Serializable

@Serializable
data class Genre(val id: Int, val name: String)