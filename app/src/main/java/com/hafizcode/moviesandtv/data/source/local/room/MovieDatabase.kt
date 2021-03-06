package com.hafizcode.moviesandtv.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import java.util.concurrent.Executors

@Database(
    entities = [MovieEntity::class, TVEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movies.db"
                ).setQueryCallback(
                    { sqlQuery, bindArgs ->
                        println("SQL QUERY : ${sqlQuery}, BIND ARGS : $bindArgs")
                    },
                    Executors.newSingleThreadExecutor(),
                )
                    .build().apply {
                        INSTANCE = this
                    }
            }
    }
}