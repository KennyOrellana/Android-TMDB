package app.kaisa.nekflix.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.kaisa.nekflix.model.Movie

@Database(
    entities = [Movie::class],
    version = 1
)
abstract class TmDB: RoomDatabase() {
    abstract fun moviesDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: TmDB? = null

        fun getDB(context: Context): TmDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    TmDB::class.java,
                "tmdb"
                ).fallbackToDestructiveMigration()
                .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}