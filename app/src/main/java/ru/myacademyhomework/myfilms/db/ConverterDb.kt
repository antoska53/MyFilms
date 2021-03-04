package ru.myacademyhomework.myfilms.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.data.Genre
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.movie.MovieInfo

object ConverterDb {
    suspend fun convertMovieListToDb(listMovie: List<Movie>): List<MovieDb> {
        return listMovie.map {
            MovieDb(
                id = it.id,
                title = it.title,
                overview = it.overview,
                poster = it.poster,
                backdrop = it.backdrop,
                ratings = it.ratings,
                numberOfRatings = it.numberOfRatings,
                minimumAge = it.minimumAge,
                runtime = it.runtime
            )
        }
    }

    suspend fun converterMovieListToMovieAndGenreList(listMovies: List<Movie>): List<MoviesAndGenres> {
        return listMovies.asFlow()
            .flatMapConcat {
                flow {
                    for (genre in it.genres) {
                        emit(
                            MoviesAndGenres(
                                movieId = it.id,
                                genreId = genre.id
                            )
                        )
                    }
                }
            }
            .toList()
    }

    suspend fun convertMovieListFromDb(listMovieDb: List<MovieDb>): List<Movie> {
        return listMovieDb.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                poster = it.poster,
                backdrop = it.backdrop,
                ratings = it.ratings,
                numberOfRatings = it.numberOfRatings,
                minimumAge = it.minimumAge,
                runtime = it.runtime,
                genres = emptyList(),
                actors = emptyList()
            )
        }
    }

    suspend fun convertMovieFromDb(movieDb: MovieDb, listGenres: List<GenreDb>): Movie {
        return Movie(
            id = movieDb.id,
            title = movieDb.title,
            overview = movieDb.overview,
            poster = movieDb.poster,
            backdrop = movieDb.backdrop,
            ratings = movieDb.ratings,
            numberOfRatings = movieDb.numberOfRatings,
            minimumAge = movieDb.minimumAge,
            runtime = movieDb.runtime,
            genres = convertGenreListFromDb(listGenres),
            actors = emptyList()
        )
    }

    suspend fun convertActorListFromDb(listActorDb: List<ActorDb>): List<Actor> {
        return listActorDb.map {
            Actor(
                id = it.id,
                name = it.name,
                picture = it.picture
            )
        }
    }

    suspend fun convertActorListToDb(listActor: List<Actor>, movieId: Int): List<ActorDb> {
        return listActor.map {
            ActorDb(
                id = it.id,
                name = it.name,
                picture = it.picture,
                movieId = movieId
            )
        }
    }

    suspend fun convertGenreListToDb(listGenre: List<Genre>): List<GenreDb> {
        return listGenre.map {
            GenreDb(
                id = it.id,
                name = it.name
            )
        }
    }

    suspend fun convertGenreListFromDb(listGenre: List<GenreDb>): List<Genre> {
        return listGenre.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }
    }

    suspend fun convertMovieWithActorsFromDb(
        movieDb: MovieDb,
        genres: List<GenreDb>,
        actors: List<ActorDb>
    ): Movie {
        return Movie(
            id = movieDb.id,
            title = movieDb.title,
            overview = movieDb.overview,
            poster = movieDb.poster,
            backdrop = movieDb.backdrop,
            ratings = movieDb.ratings,
            numberOfRatings = movieDb.numberOfRatings,
            minimumAge = movieDb.minimumAge,
            runtime = movieDb.runtime,
            genres = convertGenreListFromDb(genres),
            actors = convertActorListFromDb(actors)
        )
    }


}