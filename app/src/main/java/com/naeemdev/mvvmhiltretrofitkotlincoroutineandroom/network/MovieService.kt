package com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.network

import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.MovieDescModel
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.TrendingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API Service
 */
interface MovieService {

    @GET("/3/trending/movie/week")
    suspend fun getPopularMovies() : Response<TrendingMovieResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") id: Int) : Response<MovieDescModel>
}