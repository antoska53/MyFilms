package ru.myacademyhomework.myfilms

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.myacademyhomework.myfilms.data.Movie

interface MovieApi {
    @GET("movie/top_rated")
    suspend fun getMovies(@Query("api_key") api_key: String): List<Movie>
}