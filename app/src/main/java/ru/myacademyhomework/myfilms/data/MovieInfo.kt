package ru.myacademyhomework.myfilms.movie


import kotlinx.serialization.*
import ru.myacademyhomework.myfilms.data.Genre

@Serializable
data class MovieInfo (
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("genres")
    val genres: List<Genre>,

    @SerialName("id")
    val id: Int,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    @SerialName("overview")
    val overview: String,

    @SerialName("popularity")
    val popularity: Double,

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany>,

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>,

    @SerialName("runtime")
    val runtime: Int,

    @SerialName("status")
    val status: String,

    @SerialName("tagline")
    val tagline: String,

    @SerialName("title")
    val title: String,

    @SerialName("video")
    val video: Boolean,

    @SerialName("vote_average")
    val voteAverage: Float,

    @SerialName("vote_count")
    val voteCount: Long
)


@Serializable
data class ProductionCompany (
    val id: Long,

    val name: String,

    @SerialName("origin_country")
    val originCountry: String
)

@Serializable
data class ProductionCountry (
    @SerialName("iso_3166_1")
    val iso3166_1: String,

    val name: String
)

@Serializable
data class SpokenLanguage (
    @SerialName("iso_639_1")
    val iso639_1: String,

    val name: String
)
