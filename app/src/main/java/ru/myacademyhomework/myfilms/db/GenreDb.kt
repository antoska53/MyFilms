package ru.myacademyhomework.myfilms.db

import androidx.room.*

@Entity(tableName = "genres",
foreignKeys = [ForeignKey(
    entity = MovieDb::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("movie_id"),
    onDelete = ForeignKey.CASCADE
)])
data class GenreDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "movie_id")
    val movieId: Int

)