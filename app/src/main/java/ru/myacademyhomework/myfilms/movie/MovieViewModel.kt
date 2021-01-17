package ru.myacademyhomework.myfilms.movie

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.MyApplication
import ru.myacademyhomework.myfilms.RetrofitModule
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.data.loadMovies
import ru.myacademyhomework.myfilms.movie.MovieViewHolder.Companion.TAG

class MovieViewModel : ViewModel() {
    private val mutableLiveData = MutableLiveData<List<Movie>>()
    val liveData: LiveData<List<Movie>> = mutableLiveData
    private val movieApi = RetrofitModule.movieApi

    fun loadData() {
        viewModelScope.launch {
            //mutableLiveData.value = loadMovies(MyApplication.getInstance())
            mutableLiveData.value = movieApi.getMovies(BuildConfig.API_KEY)
        }
    }

    fun getData(): LiveData<List<Movie>> {
        loadData()
        Log.d(TAG, "loadData: " + mutableLiveData.value)

        return liveData
    }

}