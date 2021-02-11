package ru.myacademyhomework.myfilms.movie

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.MyApplication
import ru.myacademyhomework.myfilms.data.Genre
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.db.ConverterDb
import ru.myacademyhomework.myfilms.db.MovieDataBase
import ru.myacademyhomework.myfilms.db.MoviesAndGenres
import ru.myacademyhomework.myfilms.movie.MovieViewHolder.Companion.TAG
import ru.myacademyhomework.myfilms.network.*


class MovieViewModel : ViewModel() {
    private val mutableLiveData = MutableLiveData<MovieResult>()
    val liveData: LiveData<MovieResult> = mutableLiveData
    private val movieApi = RetrofitModule.movieApi
    private val movieDao = MovieDataBase.movieDataBase.getMovieDao()

    private fun loadData() {
        viewModelScope.launch {
            mutableLiveData.value =
                try {
                    val listMovies = loadMovies()
                    val listGenre = loadGenres()
                    writeDataToDb(listMovies, listGenre)
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
            val listMovies = movieDao.getAllMovies()
            mutableLiveData.value = SuccessResult(
                listMovies = listMovies.asFlow()
                    .flatMapMerge {
                        val genres = movieDao.getGenresByMovieId(it.id)
                        val movie = ConverterDb.convertMovieFromDb(it, genres)
                        flow {
                            emit(
                                movie
                            )
                        }
                    }.toList()
            )
        }
    }

    private suspend fun writeDataToDb(listMovies: List<Movie>, listGenres: List<Genre>) {
        movieDao.insertMovies(ConverterDb.convertMovieListToDb(listMovies))
        movieDao.insertGenres(ConverterDb.convertGenreListToDb(listGenres))
        listMovies.asFlow()
            .map {
                for (genre in it.genres) {
                    movieDao.insertMoviesAndGenres(
                        MoviesAndGenres(
                            movieId = it.id,
                            genreId = genre.id
                        )
                    )
                }
            }.collect()

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
                            ratings = movie.voteAverage / 2,
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

    private suspend fun loadGenres(): List<Genre> {
        return movieApi.getGenres(BuildConfig.API_KEY).genres
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