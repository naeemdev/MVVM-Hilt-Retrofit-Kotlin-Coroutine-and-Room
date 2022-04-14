package com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.MovieDescModel
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.util.Config.MOVIE_ID
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.Result
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.util.Config

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        intent?.getIntExtra(MOVIE_ID, 0)?.let { id ->
            viewModel.getMovieDetail(id)
            registerUi()
        } ?: showError("Unknown Movie")
    }


    private fun registerUi() {
        viewModel.movie.observe(this, Observer { result ->

            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let {
                        updateUi(it)
                    }
                    loading.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    result.message?.let {
                        showError(it)
                    }
                    loading.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    loading.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showError(msg: String) {
        Snackbar.make(clParent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }

    private fun updateUi(movie: MovieDescModel) {
        title = movie.title
        tvTitle.text = movie.title
        tvDescription.text = movie.overview
        Glide.with(this).load(Config.IMAGE_URL + movie.poster_path)
            .apply(RequestOptions().override(400, 400).centerInside()).into(ivCover)

        val genreNames = mutableListOf<String>()
        movie.genres.map {
            genreNames.add(it.name)
        }
        tvGenre.text = genreNames.joinToString(separator = ", ")
    }


}