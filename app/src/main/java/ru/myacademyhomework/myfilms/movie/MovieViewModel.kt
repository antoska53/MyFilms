package ru.myacademyhomework.myfilms.movie

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.MyApplication
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.db.ConverterDb
import ru.myacademyhomework.myfilms.db.MovieDataBase
import ru.myacademyhomework.myfilms.movie.MovieViewHolder.Companion.TAG
import ru.myacademyhomework.myfilms.network.*


class MovieViewModel : ViewModel() {
    private val mutableLiveData = MutableLiveData<MovieResult>()
    val liveData: LiveData<MovieResult> = mutableLiveData
    private val movieApi = RetrofitModule.movieApi
    private val movieDataBase = MovieDataBase.movieDataBase

    private fun loadData() {
        viewModelScope.launch {
            mutableLiveData.value =
                try {
                    val listMovies = loadMovies()
                    movieDataBase.getMovieDao().insertMovies(ConverterDb.convertMovieListToDb(listMovies))
                    SuccessResult(listMovies)
                } catch (e: Throwable) {
                    if (e is CancellationException) {
                        TerminalError()
                    } else {
                        ErrorResult(e)
                    }
                }
        }
    }

    private fun loadDataFromDb() {
        viewModelScope.launch {
            mutableLiveData.value =
                SuccessResult(ConverterDb.convertMovieListFromDb(movieDataBase.getMovieDao().getAllMovies()))
        }
    }

    private suspend fun loadMovies(): List<Movie> {
        return flowOf(movieApi.getMoviesId(BuildConfig.API_KEY))
            .flatMapLatest { it.movies.asFlow() }
            .flatMapMerge { it ->
                flow {
                    val movie = movieApi.getMovieInfo(it.movieId, BuildConfig.API_KEY)
                    emit(
                        Movie(
                            id = movie.id,
                            title = movie.title,
                            overview = movie.overview,
                            poster = movie.posterPath,
                            backdrop = movie.backdropPath,
                            ratings = movie.voteAverage,
                            numberOfRatings = movie.voteCount,
                            minimumAge = if (movie.adult) 16 else 13,
                            runtime = movie.runtime,
                            genres = movie.genres,
                            actors = emptyList()
                        )
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .toList()
    }


    fun getData(): LiveData<MovieResult> {
        loadData()
        Log.d(TAG, "loadData: " + mutableLiveData.value)
        return liveData
    }

    fun getDataFromDb(): LiveData<MovieResult> {
        loadDataFromDb()
        return liveData
    }

}