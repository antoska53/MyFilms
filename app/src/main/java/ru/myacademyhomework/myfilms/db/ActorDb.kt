package ru.myacademyhomework.myfilms.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import ru.myacademyhomework.myfilms.data.Movie

@Entity(tableName = "actors",
        foreignKeys = [ForeignKey(
            entity = MovieDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("movie_id"),
            onDelete = CASCADE
        )])
data class ActorDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "profile_path")
    val picture: String?,

    @ColumnInfo(name = "movie_id")
    val movieId: Int
)