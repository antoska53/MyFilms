package ru.myacademyhomework.myfilms.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ru.myacademyhomework.myfilms.data.Genre

@Entity(tableName = "movies")
data class MovieDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String = "title",

    @ColumnInfo(name = "overview")
    val overview: String = "overview",

    @ColumnInfo(name = "poster_path")
    val poster: String = "poster_path",

    @ColumnInfo(name = "backdrop_path")
    val backdrop: String = "backdrop_path",

    @ColumnInfo(name = "vote_average")
    val ratings: Float = 0F,

    @ColumnInfo(name = "vote_count")
    val numberOfRatings: Long = 0,

    @ColumnInfo(name = "minimum_age")
    val minimumAge: Int = 0,

    @ColumnInfo(name = "runtime")
    val runtime: Int = 0,
)