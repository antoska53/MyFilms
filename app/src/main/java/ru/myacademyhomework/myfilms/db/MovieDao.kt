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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesAndGenres(listMoviesAndGenres: List<MoviesAndGenres>)

    @Query("select * from movies")
    suspend fun getAllMovies(): List<MovieDb>

    @Query("select * from movies where id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieDb

    @Query("select * from actors where movie_id = :movieId")
    suspend fun getActorsByMovieId(movieId: Int): List<ActorDb>

    @Query("select genres.id, genres.name from genres join movies_and_genres on genres.id = movies_and_genres.genre_id where movies_and_genres.movie_id = :movieId ")
    suspend fun getGenresByMovieId(movieId: Int): List<GenreDb>

}