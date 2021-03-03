package ru.myacademyhomework.myfilms.movieDetails

import android.app.Application
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
import ru.myacademyhomework.myfilms.repository.MovieDetailRepository

class MovieDetailViewModel(private val movieId: Int) : ViewModel() {
    private val mutableLiveMovie = MutableLiveData<MovieResult>()
    val liveMovie = mutableLiveMovie
    private val mutableLiveActors = MutableLiveData<MovieResult>()
    val liveActors = mutableLiveActors
    private val movieDetailRepository = MovieDetailRepository(movieId)
    val movieDetailLiveData: LiveData<Movie> = movieDetailRepository.movieFlow.asLiveData()

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            movieDetailRepository.refreshMovieDetails()
        }
    }

    class Factory(val movieId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailViewModel(movieId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}