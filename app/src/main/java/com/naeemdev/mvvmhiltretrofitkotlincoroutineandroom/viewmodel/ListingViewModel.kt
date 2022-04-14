package com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.data.MovieRepository
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.TrendingMovieResponse
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for MainActivity
 */
@HiltViewModel
class ListingViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _movieList = MutableLiveData<Result<TrendingMovieResponse>>()
    val movieList = _movieList

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            movieRepository.fetchTrendingMovies().collect {
                _movieList.value = it
            }
        }
    }
}