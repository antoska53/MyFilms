package ru.myacademyhomework.myfilms.network

import ru.myacademyhomework.myfilms.data.Movie

sealed class MovieResult
class SuccessResult(val listMovies: List<Movie>): MovieResult()
class ErrorResult(val e: Throwable): MovieResult()
class TerminalError: MovieResult()
