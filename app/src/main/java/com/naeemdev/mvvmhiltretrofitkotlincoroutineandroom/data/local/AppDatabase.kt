package com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.data.GenreConverters
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.MovieModel


@Database(entities = [MovieModel::class], version = 1)
@TypeConverters(GenreConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}