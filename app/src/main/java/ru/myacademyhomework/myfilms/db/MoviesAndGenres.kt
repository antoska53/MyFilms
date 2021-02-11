package ru.myacademyhomework.myfilms.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "movies_and_genres",
    foreignKeys = [
        ForeignKey(
            entity = MovieDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("movie_id"))
        ,
        ForeignKey(
            entity = GenreDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("genre_id")
        )
    ])
data class MoviesAndGenres(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "movie_id")
    val movieId: Int,

    @ColumnInfo(name = "genre_id")
    val genreId: Int
)