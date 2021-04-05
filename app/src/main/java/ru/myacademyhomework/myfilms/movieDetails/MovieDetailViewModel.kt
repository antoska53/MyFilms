package ru.myacademyhomework.myfilms.movieDetails

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.myacademyhomework.myfilms.data.Movie
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