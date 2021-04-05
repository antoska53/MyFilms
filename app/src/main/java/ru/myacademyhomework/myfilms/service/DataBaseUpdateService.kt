package ru.myacademyhomework.myfilms.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import ru.myacademyhomework.myfilms.repository.MovieRepository

class DataBaseUpdateService(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    private val movieRepository = MovieRepository()

    override suspend fun doWork(): Result {
        return try {
            val listMovies = movieRepository.loadMovies()
            val listGenre = movieRepository.loadGenres()
            movieRepository.writeDataToDb(listMovies, listGenre)
            Result.success()
        } catch (e: Throwable) {
            Result.failure(workDataOf(UPDATE_ERROR to e.message))
        }
    }

    companion object {
        const val UPDATE_ERROR: String = "update_error"
    }
}