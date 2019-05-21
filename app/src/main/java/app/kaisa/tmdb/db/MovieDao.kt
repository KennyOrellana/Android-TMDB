package app.kaisa.tmdb.db

import androidx.paging.DataSource
import androidx.room.*
import app.kaisa.tmdb.model.Movie
import app.kaisa.tmdb.model.MovieType

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE type =:movieType ORDER BY page ASC")
    fun loadMovies(movieType: MovieType): DataSource.Factory<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Insert
    fun insertMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Movie)
}