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
    private val movieDao: MovieDao = MovieDataBase.movieDataBase.getMovieDao()
    private val movieApi = RetrofitModule.movieApi

    val allMovies: Flow<List<Movie>> = movieDao.getAllMovies().map {
        it.map { it ->
            val genres = movieDao.getGenresByMovieId(it.id)
            ConverterDb.convertMovieFromDb(it, genres)
        }
    }


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
                listGenres = movie.genres,
                genresString = movie.genres.joinToString { genre -> genre.name },
                actors = emptyList()
            )
        }
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