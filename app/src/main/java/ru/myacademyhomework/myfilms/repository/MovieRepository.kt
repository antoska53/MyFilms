package ru.myacademyhomework.myfilms.repository

import kotlinx.coroutines.flow.*
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.data.Genre
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.db.ConverterDb
import ru.myacademyhomework.myfilms.db.MovieDao
import ru.myacademyhomework.myfilms.db.MovieDataBase
import ru.myacademyhomework.myfilms.network.RetrofitModule

class MovieRepository() {
  //  val workRepository = WorkMovieRepository()
    private val movieDao: MovieDao = MovieDataBase.movieDataBase.getMovieDao()
    private val movieApi = RetrofitModule.movieApi

    val allMovies: Flow<List<Movie>> = movieDao.getAllMovies().map {
        it.map { it ->
            val genres = movieDao.getGenresByMovieId(it.id)
            ConverterDb.convertMovieFromDb(it, genres)
        }
    }

//    suspend fun refreshMovies() {
//        withContext(Dispatchers.IO) {
//            WorkManager.getInstance(MyApplication.getInstance()).enqueue(workRepository.simpleRequest)
//        }
//    }
//
//    suspend fun periodicUpdateMovies(){
//        withContext(Dispatchers.IO){
//            WorkManager.getInstance(MyApplication.getInstance()).enqueue(workRepository.periodicRequest)
//        }
//    }

//    suspend fun loadMovies(): List<Movie> {
//        return flowOf(movieApi.getMoviesId(BuildConfig.API_KEY))
//            .flatMapLatest { it.movies.asFlow() }
//            .flatMapConcat { it ->
//                flow {
//                    val movie = movieApi.getMovieInfo(it.movieId, BuildConfig.API_KEY)
//                    emit(
//                        Movie(
//                            id = movie.id,
//                            title = movie.title,
//                            overview = movie.overview,
//                            poster = movie.posterPath,
//                            backdrop = movie.backdropPath,
//                            ratings = movie.voteAverage / 2,
//                            numberOfRatings = movie.voteCount,
//                            minimumAge = if (movie.adult) 16 else 13,
//                            runtime = movie.runtime,
//                            genres = movie.genres,
//                            actors = emptyList()
//                        )
//                    )
//                }
//            }
//            .toList()
//    }

    suspend fun loadMovies(): List<Movie> {
        return movieApi.getMoviesId(BuildConfig.API_KEY).movies.map {
            val movie = movieApi.getMovieInfo(it.movieId, BuildConfig.API_KEY)
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
        }

//            .flatMapLatest { it.movies.asFlow() }
//            .flatMapConcat { it ->
//                flow {
//                    val movie = movieApi.getMovieInfo(it.movieId, BuildConfig.API_KEY)
//                    emit(
//                        Movie(
//                            id = movie.id,
//                            title = movie.title,
//                            overview = movie.overview,
//                            poster = movie.posterPath,
//                            backdrop = movie.backdropPath,
//                            ratings = movie.voteAverage / 2,
//                            numberOfRatings = movie.voteCount,
//                            minimumAge = if (movie.adult) 16 else 13,
//                            runtime = movie.runtime,
//                            genres = movie.genres,
//                            actors = emptyList()
//                        )
//                    )
//                }
//            }
//            .toList()
    }

    suspend fun loadGenres(): List<Genre> {
        return movieApi.getGenres(BuildConfig.API_KEY).genres
    }

    suspend fun writeDataToDb(listMovies: List<Movie>, listGenres: List<Genre>) {
        movieDao.insertMovieGenresMoviesAndGenres(
            ConverterDb.convertMovieListToDb(listMovies),
            ConverterDb.convertGenreListToDb(listGenres),
            ConverterDb.converterMovieListToMovieAndGenreList(listMovies)
        )
    }
}