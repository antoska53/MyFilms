package ru.myacademyhomework.myfilms.data


import kotlinx.serialization.*

@Serializable
data class ActorsInfo(
    val id: Int,
    val cast: List<Actor>,
)



