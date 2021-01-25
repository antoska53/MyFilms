package ru.myacademyhomework.myfilms.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieTopResponse(
    @SerialName("page")
    val page: Long,

    @SerialName("results")
    val movies: List<MovieNetworkModel>,
    )