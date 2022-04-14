package com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.customadapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.DetailsActivity
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.R
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.model.MovieModel
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.util.Config
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.util.Config.MOVIE_ID
import com.naeemdev.mvvmhiltretrofitkotlincoroutineandroom.util.Genre

import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter(private val context: Context, private val list: ArrayList<MovieModel>) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: MovieModel) {
            itemView.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(MOVIE_ID, movie.id)
                context.startActivity(intent)
            }
            itemView.tvTitle.text = movie.title
            Glide.with(context).load(Config.IMAGE_URL + movie.poster_path)
                .apply(RequestOptions().override(400, 400)
                    .centerInside()
                    ).into(itemView.ivPoster)
            itemView.tvGenre.text = Genre.getGenre(movie.genre_ids)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(context, view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateData(newList: List<MovieModel>) {
        list.clear()
        val sortedList = newList.sortedBy { movie -> movie.popularity }
        list.addAll(sortedList)
        notifyDataSetChanged()
    }
}