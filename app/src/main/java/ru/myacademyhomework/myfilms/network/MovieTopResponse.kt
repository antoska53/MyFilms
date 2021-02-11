package ru.myacademyhomework.myfilms.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.myacademyhomework.myfilms.data.Movie

@Serializable
class MovieTopResponse(
    @SerialName("page")
    val page: Long,

//    @SerialName("results")
//    val movies: List<MovieNetworkModel>,

    @SerialName("results")
    val movies: List<MovieNetworkModel>,
    )