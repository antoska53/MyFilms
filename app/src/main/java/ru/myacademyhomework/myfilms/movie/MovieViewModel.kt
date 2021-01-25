package ru.myacademyhomework.myfilms.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.network.RetrofitModule
import ru.myacademyhomework.myfilms.movie.MovieViewHolder.Companion.TAG
import ru.myacademyhomework.myfilms.network.MovieNetworkModel

class MovieViewModel : ViewModel() {
    private val mutableLiveData = MutableLiveData<List<MovieNetworkModel>>()
    val liveData: LiveData<List<MovieNetworkModel>> = mutableLiveData
    private val movieApi = RetrofitModule.movieApi

    fun loadData() {
        viewModelScope.launch {
            //mutableLiveData.value = loadMovies(MyApplication.getInstance())
            mutableLiveData.value = flowOf(movieApi.getMovies(BuildConfig.API_KEY))
                .flowOn(Dispatchers.IO)
                .flatMapMerge { it.movies.asFlow() }
//                .map { MovieNetworkModel(adult = it.adult,
//                backdropPath = it.backdropPath,
//                genreIDS = it.genreIDS,
//                movieId = it.movieId,
//                originalLanguage = it.originalLanguage) }
                .toList()
        }
    }

    fun getData(): LiveData<List<MovieNetworkModel>> {
        loadData()
        Log.d(TAG, "loadData: " + mutableLiveData.value)

        return liveData
    }

}