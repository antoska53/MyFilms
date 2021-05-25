package ru.myacademyhomework.myfilms.repository

import android.util.Log
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.MyApplication
import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.data.Genre
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.db.ConverterDb
import ru.myacademyhomework.myfilms.db.MovieDao
import ru.myacademyhomework.myfilms.db.MovieDataBase
import ru.myacademyhomework.myfilms.db.MovieDb
import ru.myacademyhomework.myfilms.network.RetrofitModule
import ru.myacademyhomework.myfilms.service.WorkMovieDetailHelper

class MovieDetailRepository(private val movieId: Int) {
    private val workRepository = WorkMovieDetailHelper(movieId)
    private val movieDao: MovieDao = MovieDataBase.movieDataBase.getMovieDao()
    private val movieApi = RetrofitModule.movieApi

    val movieFlow: Flow<Movie> = movieDao.getActorsByMovieId(movieId = movieId).map {
        val genres = movieDao.getGenresByMovieId(movieId)
        val movie = movieDao.getMovieById(movieId) ?: MovieDb()
        Log.d("MOVIE_ID", ": $movie")
        ConverterDb.convertMovieWithActorsFromDb(movie, genres, it)
    }

    suspend fun refreshMovieDetails() {
        withContext(Dispatchers.IO) {
            WorkManager.getInstance(MyApplication.getInstance())
                .enqueue(workRepository.simpleDetailRequest)
        }
    }

    suspend fun loadActors(): List<Actor> {
        Log.d("MOVIE_ID", "loadActors: ")
        return movieApi.getActors(movieId, BuildConfig.API_KEY).cast
    }

    suspend fun writeActorsToDb(listActors: List<Actor>) {
        movieDao.insertActors(ConverterDb.convertActorListToDb(listActors, movieId = movieId))
    }

    suspend fun isMovieContains(movieId: Int): Boolean{
        return movieDao.getMovieById(movieId) != null
    }

    suspend fun loadMovie(movieId: Int): Movie {
        val movieInfo = movieApi.getMovieInfo(movieId, BuildConfig.API_KEY)
        return Movie(
            id = movieInfo.id,
            title = movieInfo.title,
            overview = movieInfo.overview,
            poster = movieInfo.posterPath,
            backdrop = movieInfo.backdropPath,
            ratings = movieInfo.voteAverage / 2,
            numberOfRatings = movieInfo.voteCount,
            minimumAge = if (movieInfo.adult) 16 else 13,
            runtime = movieInfo.runtime,
            listGenres = movieInfo.genres,
            genresString = movieInfo.genres.joinToString { genre -> genre.name },
            actors = emptyList()
        )
    }

    suspend fun writeDataToDb(listMovies: List<Movie>, listGenres: List<Genre>) {
        movieDao.insertMovieGenresMoviesAndGenres(
            ConverterDb.convertMovieListToDb(listMovies),
            ConverterDb.convertGenreListToDb(listGenres),
            ConverterDb.converterMovieListToMovieAndGenreList(listMovies)
        )
    }

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
    }
}