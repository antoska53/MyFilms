package ru.myacademyhomework.myfilms.service

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import ru.myacademyhomework.myfilms.repository.MovieDetailRepository

class WorkMovieDetailHelper(private val movieId: Int) {

    val simpleDetailRequest = OneTimeWorkRequest.Builder(DataBaseDetailUpdateService::class.java)
        .setInputData(Data.Builder().putInt(MovieDetailRepository.MOVIE_ID, movieId).build())
        .build()
}