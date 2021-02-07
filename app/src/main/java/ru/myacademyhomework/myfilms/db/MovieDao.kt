package ru.myacademyhomework.myfilms.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.movie.MovieInfo

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actors: List<ActorDb>)

    @Query("select * from movies")
    suspend fun getAllMovies(): List<MovieDb>

    @Query("select * from movies where id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieDb

    @Query("select * from actors where movie_id = :movieId")
    suspend fun getActorsByMovieId(movieId: Int): List<ActorDb>


}