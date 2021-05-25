package ru.myacademyhomework.myfilms.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.data.Movie
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

    @Transaction
    suspend fun insertMovieGenresMoviesAndGenres(movies: List<MovieDb>, genres: List<GenreDb>, listMoviesAndGenres: List<MoviesAndGenres>){
        insertMovies(movies)
        insertGenres(genres)
        insertMoviesAndGenres(listMoviesAndGenres)
    }

    @Query("select * from movies")
    fun getAllMovies(): Flow<List<MovieDb>>

//    @Query("select movies.*, group_concat(genres.name, ',') as genres from movies join movies_and_genres on movies.id = movies_and_genres.movie_id join genres on genres.id = movies_and_genres.genre_id group by movies.id")
//    fun getAllMoviesWithGenres(): Flow<List<MovieWithGenresDb>>

//    @Query("select movies.*, group_concat(genres.name, ',') as genres from movies join movies_and_genres on movies.id = movies_and_genres.movie_id join genres on genres.id = movies_and_genres.genre_id group by movies.id")
//    fun getAllMoviesWithGenres(): Flow<List<Movie>>

    @Query("select * from movies where id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieDb?

    @Query("select * from actors where movie_id = :movieId")
    fun getActorsByMovieId(movieId: Int): Flow<List<ActorDb>>

    @Query("select genres.id, genres.name from genres join movies_and_genres on genres.id = movies_and_genres.genre_id where movies_and_genres.movie_id = :movieId ")
    suspend fun getGenresByMovieId(movieId: Int): List<GenreDb>

}