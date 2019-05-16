package app.kaisa.nekflix.db

import androidx.paging.DataSource
import androidx.room.*
import app.kaisa.nekflix.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY page ASC")
    fun loadMovies(): DataSource.Factory<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Insert
    fun insertMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Movie)
}