package com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.viewmodel



import androidx.lifecycle.*
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.data.MovieRepository
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.MovieDescModel
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * ViewModel for Movie details screen
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private var _id = MutableLiveData<Int>()
    private val _movie: LiveData<Result<MovieDescModel>> = _id.distinctUntilChanged().switchMap {
        liveData {
            movieRepository.fetchMovie(it).onStart {
                emit(Result.loading())
            }.collect {
                emit(it)
            }
        }
    }
    val movie = _movie

    fun getMovieDetail(id: Int) {
        _id.value = id
    }
}