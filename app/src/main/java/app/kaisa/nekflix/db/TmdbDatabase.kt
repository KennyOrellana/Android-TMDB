package app.kaisa.nekflix.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.kaisa.nekflix.model.Movie

@Database(
    entities = [Movie::class],
    version = 1
)
@TypeConverters(MovieTypeConverter::class)
abstract class TmdbDatabase : RoomDatabase() {
    abstract fun moviesDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: TmdbDatabase? = null

        fun getDB(context: Context): TmdbDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    TmdbDatabase::class.java,
                "tmdb"
                ).fallbackToDestructiveMigration()
                .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}