package ru.myacademyhomework.myfilms

import android.app.Application

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