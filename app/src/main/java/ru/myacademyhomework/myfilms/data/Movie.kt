package ru.myacademyhomework.myfilms.data


data class Movie(
    val id: Int,

    val title: String,

    val overview: String,

    val poster: String,

    val backdrop: String,

    val ratings: Float,

    val numberOfRatings: Long,

    val minimumAge: Int,

    val runtime: Int,

    val listGenres: List<Genre>,

    val genresString: String,

    val actors: List<Actor>
)