package com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.di

import android.content.Context
import androidx.room.Room
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.data.local.AppDatabase
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.data.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        ).build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }
}