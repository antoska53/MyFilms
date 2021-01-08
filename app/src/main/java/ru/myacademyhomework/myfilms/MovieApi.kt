package ru.myacademyhomework.myfilms

import retrofit2.Call
import retrofit2.http.GET
import ru.myacademyhomework.myfilms.data.Movie

interface MovieApi {
    @GET("")
    fun getMovies(): Call<List<Movie>>
}