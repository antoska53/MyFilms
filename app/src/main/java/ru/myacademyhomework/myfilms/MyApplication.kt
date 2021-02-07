package ru.myacademyhomework.myfilms

import android.app.Application
import androidx.room.Room
import ru.myacademyhomework.myfilms.db.MovieDataBase

class MyApplication : Application()   {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun getInstance() : MyApplication {
            return instance!!
        }
    }
}