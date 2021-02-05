package ru.myacademyhomework.myfilms.movie

import android.util.Log
import android.util.TimingLogger
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.movie.MovieViewHolder.Companion.TAG
import ru.myacademyhomework.myfilms.network.RetrofitModule
import kotlin.system.measureTimeMillis


class MovieViewModel : ViewModel() {
    private val mutableLiveData = MutableLiveData<List<Movie>>()
    val liveData: LiveData<List<Movie>> = mutableLiveData
    private val movieApi = RetrofitModule.movieApi

    fun loadData() {
        viewModelScope.launch {
            mutableLiveData.value = flowOf(movieApi.getMovies(BuildConfig.API_KEY))
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
                                actors = listOf()
                            )
                        )
                    }
                }
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d(TAG, "loadData catch: $it")
                    emit(
                        Movie(
                            id = 0,
                            title = "error",
                            overview = "error",
                            poster = "error",
                            backdrop = "error",
                            ratings = 0f,
                            numberOfRatings = 0,
                            minimumAge = 0,
                            runtime = 0,
                            genres = listOf(),
                            actors = listOf()
                        )
                    )
                }
                .toList()

//            val movieTopResponse = movieApi.getMovies(BuildConfig.API_KEY)
//            val time = measureTimeMillis {
//                mutableLiveData.value = flow {
//                    for (m in movieTopResponse.movies) {
//                        val movie = movieApi.getMovieInfo(m.movieId, BuildConfig.API_KEY)
//                        emit(
//                            Movie(
//                                id = movie.id,
//                                title = movie.title,
//                                overview = movie.overview,
//                                poster = movie.posterPath,
//                                backdrop = movie.backdropPath,
//                                ratings = movie.voteAverage,
//                                numberOfRatings = movie.voteCount,
//                                minimumAge = if (movie.adult) 16 else 13,
//                                runtime = movie.runtime,
//                                genres = movie.genres,
//                                actors = listOf()
//                            )
//                        )
//                    }
//                }
//                    //.flowOn(Dispatchers.IO)
//                    .buffer()
//                    .toList()
//            }
//            println(" time - $time ms")
        }
    }

    fun getData(): LiveData<List<Movie>> {
        loadData()
        Log.d(TAG, "loadData: " + mutableLiveData.value)

        return liveData
    }

}