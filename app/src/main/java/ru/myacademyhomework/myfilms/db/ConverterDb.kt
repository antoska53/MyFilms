package ru.myacademyhomework.myfilms.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.data.Genre
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.movie.MovieInfo

object ConverterDb {
    suspend fun convertMovieListToDb(listMovie: List<Movie>): List<MovieDb> {
        return listMovie.asFlow()
            .flatMapMerge {
                flow {
                    emit(
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
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .toList()
    }

    suspend fun convertMovieListFromDb(listMovieDb: List<MovieDb>): List<Movie> {
        return listMovieDb.asFlow()
            .flatMapMerge {
                flow {
                    emit(
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
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .toList()
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
        return listActorDb.asFlow()
            .flatMapMerge {
                flow {
                    emit(
                        Actor(
                            id = it.id,
                            name = it.name,
                            picture = it.picture
                        )
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .toList()
    }

    suspend fun convertActorListToDb(listActor: List<Actor>, movieId: Int): List<ActorDb> {
        return listActor.asFlow()
            .flatMapMerge {
                flow {
                    emit(
                        ActorDb(
                            id = it.id,
                            name = it.name,
                            picture = it.picture,
                            movieId = movieId
                        )
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .toList()
    }

    suspend fun convertGenreListToDb(listGenre: List<Genre>): List<GenreDb> {
        return listGenre.asFlow()
            .flatMapMerge {
                flow {
                    emit(
                        GenreDb(
                            id = it.id,
                            name = it.name
                        )
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .toList()
    }

    suspend fun convertGenreListFromDb(listGenre: List<GenreDb>): List<Genre> {
        return listGenre.asFlow()
            .map {
                Genre(
                    id = it.id,
                    name = it.name
                )
            }
            .flowOn(Dispatchers.IO)
            .toList()
    }
}