package com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.data.remote

import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.MovieDescModel
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.TrendingMovieResponse
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.network.MovieService
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import  com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.Result
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.util.ErrorUtils

/**
 * fetches data from remote source
 */
class MovieRemoteDataSource @Inject constructor(private val retrofit: Retrofit) {

    suspend fun fetchTrendingMovies(): Result<TrendingMovieResponse> {
        val movieService = retrofit.create(MovieService::class.java);
        return getResponse(
            request = { movieService.getPopularMovies() },
            defaultErrorMessage = "Error fetching Movie list")

    }

    suspend fun fetchMovie(id: Int): Result<MovieDescModel> {
        val movieService = retrofit.create(MovieService::class.java);
        return getResponse(
            request = { movieService.getMovie(id) },
            defaultErrorMessage = "Error fetching Movie Description")
    }

    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}