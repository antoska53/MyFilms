package ru.myacademyhomework.myfilms.db

import androidx.room.*

@Entity(tableName = "genres")
data class GenreDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,
)