package ru.myacademyhomework.myfilms

import android.app.Application
import androidx.room.Room
import ru.myacademyhomework.myfilms.db.MovieDataBase

class MyApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: MyApplication

        fun getInstance(): MyApplication {
            return instance
        }
    }
}