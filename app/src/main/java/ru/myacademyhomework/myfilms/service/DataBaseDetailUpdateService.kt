package ru.myacademyhomework.myfilms.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.myacademyhomework.myfilms.repository.MovieDetailRepository

class DataBaseDetailUpdateService(context: Context, workerParams: WorkerParameters)
    : CoroutineWorker(context, workerParams) {

    private val movieId = inputData.getInt(MovieDetailRepository.MOVIE_ID, 0)
    private val movieDetailRepository = MovieDetailRepository(movieId)

    override suspend fun doWork(): Result {
        val listActors =  movieDetailRepository.loadActors()
        movieDetailRepository.writeActorsToDb(listActors)
        return Result.success()
    }

}