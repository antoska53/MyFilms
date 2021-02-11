package ru.myacademyhomework.myfilms.network

import kotlinx.serialization.Serializable
import ru.myacademyhomework.myfilms.data.Genre

@Serializable
data class GenreResponse (
    val genres: List<Genre>
)

