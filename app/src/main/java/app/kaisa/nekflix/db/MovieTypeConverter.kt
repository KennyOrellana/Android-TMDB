package app.kaisa.nekflix.db

import androidx.room.TypeConverter
import app.kaisa.nekflix.model.MovieType

class MovieTypeConverter {
    @TypeConverter
    fun toMovieType(value: Int) = MovieType.get(value)

    @TypeConverter
    fun toInt(movieType: MovieType) = movieType.type
}