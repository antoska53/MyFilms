package ru.myacademyhomework.myfilms.movieDetails

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.data.ActorsInfo
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.db.ConverterDb
import ru.myacademyhomework.myfilms.db.MovieDataBase
import ru.myacademyhomework.myfilms.movie.MovieInfo
import ru.myacademyhomework.myfilms.movie.MovieViewHolder.Companion.TAG
import ru.myacademyhomework.myfilms.network.*

class MovieDetailViewModel : ViewModel() {
    private val mutableLiveMovie = MutableLiveData<MovieResult>()
    val liveMovie = mutableLiveMovie
    private val mutableLiveActors = MutableLiveData<MovieResult>()
    val liveActors = mutableLiveActors
    val movieDao = MovieDataBase.movieDataBase.getMovieDao()


    fun loadMovie(id: Int) {
        viewModelScope.launch {
            try {
                val movieInfo = RetrofitModule.movieApi.getMovieInfo(id, BuildConfig.API_KEY)
                liveMovie.value = SuccessDetailResult(
                    Movie(
                        id = movieInfo.id,
                        title = movieInfo.title,
                        overview = movieInfo.overview,
                        poster = movieInfo.posterPath,
                        backdrop = movieInfo.backdropPath,
                        ratings = movieInfo.voteAverage / 2,
                        numberOfRatings = movieInfo.voteCount,
                        minimumAge = if (movieInfo.adult) 16 else 13,
                        runtime = movieInfo.runtime,
                        genres = movieInfo.genres,
                        actors = emptyList()
                    )
                )
            } catch (e: Throwable) {
                liveMovie.value = ErrorResult(e)
            }
            try {
                val actorsInfo = RetrofitModule.movieApi.getActors(id, BuildConfig.API_KEY)
                movieDao
                    .insertActors(ConverterDb.convertActorListToDb(actorsInfo.cast, movieId = id))
                liveActors.value = SuccessActorResult(actorsInfo.cast)
            } catch (e: Throwable) {
                liveActors.value = ErrorResult(e)
            }
        }
    }

    fun loadMovieFromDb(movieId: Int) {
        viewModelScope.launch {
            liveMovie.value =
                SuccessDetailResult(
                    ConverterDb.convertMovieFromDb(
                        movieDao.getMovieById(movieId = movieId),
                        movieDao.getGenresByMovieId(movieId = movieId)
                    )
                )
            liveActors.value = SuccessActorResult(
                ConverterDb.convertActorListFromDb(
                    movieDao.getActorsByMovieId(movieId = movieId)
                )
            )
        }
    }
}