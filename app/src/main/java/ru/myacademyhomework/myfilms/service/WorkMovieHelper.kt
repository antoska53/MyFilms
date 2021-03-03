package ru.myacademyhomework.myfilms.service

import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.myacademyhomework.myfilms.MyApplication
import java.util.concurrent.TimeUnit

class WorkMovieHelper {

    val simpleRequest: OneTimeWorkRequest =
        OneTimeWorkRequest.Builder(DataBaseUpdateService::class.java).build()

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        //.setRequiresCharging(true)
        .build()
    val periodicRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
        DataBaseUpdateService::class.java,
        8, TimeUnit.HOURS,
        1, TimeUnit.HOURS
    )
        .setConstraints(constraints)
        .build()

    fun startUpdateService() {
        WorkManager.getInstance(MyApplication.getInstance()).enqueue(simpleRequest)
    }

    fun startPeriodicUpdateService() {
        WorkManager.getInstance(MyApplication.getInstance()).enqueue(periodicRequest)
    }
}