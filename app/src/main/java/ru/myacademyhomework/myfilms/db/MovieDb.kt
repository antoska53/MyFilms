package ru.myacademyhomework.myfilms.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "poster_path")
    val poster: String,

    @ColumnInfo(name = "backdrop_path")
    val backdrop: String,

    @ColumnInfo(name = "vote_average")
    val ratings: Float,

    @ColumnInfo(name = "vote_count")
    val numberOfRatings: Long,

    @ColumnInfo(name = "minimum_age")
    val minimumAge: Int,

    @ColumnInfo(name = "runtime")
    val runtime: Int,
)