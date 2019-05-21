package app.kaisa.tmdb.db

import androidx.room.TypeConverter
import app.kaisa.tmdb.model.MovieType

class MovieTypeConverter {
    @TypeConverter
    fun toMovieType(value: Int) = MovieType.get(value)

    @TypeConverter
    fun toInt(movieType: MovieType) = movieType.type
}