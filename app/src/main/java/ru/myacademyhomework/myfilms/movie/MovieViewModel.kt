package ru.myacademyhomework.myfilms.movie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.myacademyhomework.myfilms.MyApplication
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.data.loadMovies

class MovieViewModel : ViewModel() {
    private val mutableLiveData = MutableLiveData<List<Movie>>()
    val liveData: LiveData<List<Movie>> = mutableLiveData

    fun loadData() {
        viewModelScope.launch {
            mutableLiveData.value = loadMovies(MyApplication.getInstance())
        }
    }

    fun getData(): LiveData<List<Movie>> {
        loadData()
        return liveData
    }

}