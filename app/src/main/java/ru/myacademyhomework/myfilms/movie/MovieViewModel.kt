package ru.myacademyhomework.myfilms.movie

import android.util.Log
import androidx.lifecycle.*
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.launch
import ru.myacademyhomework.myfilms.*
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.movie.MovieViewHolder.Companion.TAG
import ru.myacademyhomework.myfilms.network.*
import ru.myacademyhomework.myfilms.repository.MovieRepository
import ru.myacademyhomework.myfilms.service.WorkMovieHelper


class MovieViewModel : ViewModel() {
    private val _resultLiveData = MutableLiveData<MovieResult>()
    val resultLiveData: LiveData<MovieResult> = _resultLiveData
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: LiveData<LoadState> = _loadState
    private val workRepository = WorkMovieHelper()
    val statusLoad: LiveData<WorkInfo> = WorkManager.getInstance(MyApplication.getInstance())
        .getWorkInfoByIdLiveData(workRepository.simpleRequest.id)
    private val movieRepository: MovieRepository = MovieRepository()
    val allMovies: LiveData<List<Movie>> = movieRepository.allMovies.asLiveData()


    init {
        refreshData()
        refreshDataPeriodic()
    }

    private fun refreshData() {
        viewModelScope.launch {
            Log.d("REFRESH", "refreshData: REFRESH")
            workRepository.startUpdateService()
        }
    }


    private fun refreshDataPeriodic() {
        viewModelScope.launch {
            workRepository.startPeriodicUpdateService()
        }
    }

}