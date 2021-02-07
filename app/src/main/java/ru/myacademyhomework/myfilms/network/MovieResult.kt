package ru.myacademyhomework.myfilms.network

import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.data.ActorsInfo
import ru.myacademyhomework.myfilms.data.Movie
import ru.myacademyhomework.myfilms.movie.MovieInfo

sealed class MovieResult
class SuccessResult(val listMovies: List<Movie>): MovieResult()
class SuccessDetailResult(val movieInfo: Movie): MovieResult()
class SuccessActorResult(val actors: List<Actor>): MovieResult()
class ErrorResult(val e: Throwable): MovieResult()
class TerminalError: MovieResult()
