package ru.myacademyhomework.myfilms.network

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/top_rated")
    suspend fun getMovies(@Query("api_key") api_key: String): MovieTopResponse
}