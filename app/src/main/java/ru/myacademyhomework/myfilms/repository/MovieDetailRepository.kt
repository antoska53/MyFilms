package ru.myacademyhomework.myfilms.repository

import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.MyApplication
import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.db.ConverterDb
import ru.myacademyhomework.myfilms.db.MovieDao
import ru.myacademyhomework.myfilms.db.MovieDataBase
import ru.myacademyhomework.myfilms.network.RetrofitModule
import ru.myacademyhomework.myfilms.service.WorkMovieDetailHelper

class MovieDetailRepository(private val movieId: Int) {
    val workRepository = WorkMovieDetailHelper(movieId)
    private val movieDao: MovieDao = MovieDataBase.movieDataBase.getMovieDao()
    private val movieApi = RetrofitModule.movieApi

    val movieFlow: Flow<Movie> = movieDao.getActorsByMovieId(movieId = movieId).map{
        val genres = movieDao.getGenresByMovieId(movieId)
        val movie = movieDao.getMovieById(movieId)
        ConverterDb.convertMovieWithActorsFromDb(movie, genres, it)
    }

    suspend fun refreshMovieDetails() {
        withContext(Dispatchers.IO) {
            WorkManager.getInstance(MyApplication.getInstance()).enqueue(workRepository.simpleDetailRequest)
        }
    }

    suspend fun loadActors(): List<Actor> {
        return movieApi.getActors(movieId, BuildConfig.API_KEY).cast
    }

    suspend fun writeActorsToDb(listActors: List<Actor>){
        movieDao.insertActors(ConverterDb.convertActorListToDb(listActors, movieId = movieId))
    }


    companion object{
        const val MOVIE_ID = "MOVIE_ID"
    }
}