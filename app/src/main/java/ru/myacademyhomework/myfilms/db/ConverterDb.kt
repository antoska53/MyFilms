package ru.myacademyhomework.myfilms.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.myacademyhomework.myfilms.data.Actor
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

    suspend fun convertMovieFromDb(movieDb: MovieDb): Movie {
        return  Movie(
            id = movieDb.id,
            title = movieDb.title,
            overview = movieDb.overview,
            poster = movieDb.poster,
            backdrop = movieDb.backdrop,
            ratings = movieDb.ratings,
            numberOfRatings = movieDb.numberOfRatings,
            minimumAge = movieDb.minimumAge,
            runtime = movieDb.runtime,
            genres = emptyList(),
            actors = emptyList()
        )
    }

    suspend fun convertActorListFromDb(listActorDb: List<ActorDb>): List<Actor>{
        return listActorDb.asFlow()
            .flatMapMerge {
                flow {
                    emit(
                        Actor(id = it.id,
                        name = it.name,
                        picture = it.picture)
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .toList()
    }

    suspend fun convertActorListToDb(listActor: List<Actor>, movieId: Int): List<ActorDb>{
        return listActor.asFlow()
            .flatMapMerge {
                flow {
                    emit(
                        ActorDb(id = it.id,
                            name = it.name,
                            picture = it.picture,
                            movieId = movieId)
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .toList()
    }
}