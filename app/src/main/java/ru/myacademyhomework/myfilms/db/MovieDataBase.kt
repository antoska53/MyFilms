package ru.myacademyhomework.myfilms.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.myacademyhomework.myfilms.MyApplication

@Database(entities = [ActorDb::class, MovieDb::class, GenreDb::class], version = 1)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

    companion object{
        private val DATABASE_NAME = "Movies.db"
        val movieDataBase: MovieDataBase by lazy{
            Room.databaseBuilder(
                MyApplication.getInstance(),
                MovieDataBase::class.java, DATABASE_NAME)
                .build()
        }


    }
}