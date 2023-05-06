package com.example.tp_06_movieapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tp_06_movieapp.database.dao.MovieDao
import com.example.tp_06_movieapp.database.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
)
abstract class MoviesRoomDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
