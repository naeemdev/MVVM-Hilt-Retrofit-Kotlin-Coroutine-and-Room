package com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.data.local


import androidx.room.*
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.MovieModel

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieModel order by popularity DESC")
    fun getAll(): List<MovieModel>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieModel>)

    @Delete
    fun delete(movie: MovieModel)

    @Delete
    fun deleteAll(movie: List<MovieModel>)
}