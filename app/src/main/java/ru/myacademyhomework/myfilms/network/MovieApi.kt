package ru.myacademyhomework.myfilms.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.myacademyhomework.myfilms.movie.MovieInfo
import ru.myacademyhomework.myfilms.data.ActorsInfo
import ru.myacademyhomework.myfilms.data.Genre

interface MovieApi {
    @GET("movie/top_rated")
    suspend fun getMoviesId(@Query("api_key") api_key: String): MovieTopResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieInfo(
        @Path("movie_id") id: Int,
        @Query("api_key") api_key: String
    ): MovieInfo

    @GET("movie/{movie_id}/credits")
    suspend fun getActors(@Path("movie_id") id: Int, @Query("api_key") api_key: String): ActorsInfo

    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") api_key: String): GenreResponse
}