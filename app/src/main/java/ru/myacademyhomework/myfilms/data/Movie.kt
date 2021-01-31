package ru.myacademyhomework.myfilms.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val poster: String,
    @SerialName("backdrop_path")
    val backdrop: String,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val numberOfRatings: Long,

    val minimumAge: Int,

    val runtime: Int,

    val genres: List<Genre>,
    val actors: List<Actor>
)