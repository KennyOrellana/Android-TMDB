package app.kaisa.nekflix.db

import androidx.paging.DataSource
import androidx.room.*
import app.kaisa.nekflix.model.Movie
import app.kaisa.nekflix.model.MovieType

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