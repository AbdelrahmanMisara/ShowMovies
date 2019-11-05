package com.moetaz.popularmoviesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moetaz.popularmoviesapp.R
import com.moetaz.popularmoviesapp.data.MovieData
import com.moetaz.popularmoviesapp.utilities.Utile
import kotlinx.android.synthetic.main.movie_row.view.*


class FavouriteMoviesAdapter(val movieData: ArrayList<MovieData>, val context: Context) :
    RecyclerView.Adapter<FavouriteMoviesAdapter.MyViewHolder>() {

    private val BaseUrl = "http://image.tmdb.org/t/p/w185/"
    var firstTime = false

    interface OnMovieClicked {
        fun onClick(movieData: MovieData)
        fun onFavClick(movieData: MovieData , position : Int)
    }

    lateinit var onMovieClicked: OnMovieClicked
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(com.moetaz.popularmoviesapp.R.layout.movie_row, parent, false)
        )
    }

    override fun getItemCount() = movieData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        setAnimation(holder , position)

        val movie = movieData.get(position)
        initializeUI(holder, movie)

        holder.itemView.setOnClickListener {
            onMovieClicked.onClick(movie)
        }

        holder.movieFav.setOnClickListener {
            onMovieClicked.onFavClick(movie , position)
        }

    }



    private fun setAnimation(holder: MyViewHolder, position: Int) {

            firstTime = true
            val slideLeft = AnimationUtils.loadAnimation(context, R.anim.slide_right)
            val slideright = AnimationUtils.loadAnimation(context, R.anim.slide_left)
            if (position % 2 == 0)
                holder.itemView.startAnimation(slideLeft)
            else
                holder.itemView.startAnimation(slideright)
    }

    private fun initializeUI(holder: MyViewHolder, movieData: MovieData) {

        holder.title.text = movieData.title
        holder.date.text = movieData.date
        if (movieData.overview.length < 100)
            holder.desc.text = movieData.overview
        else
            holder.desc.text = "${movieData.overview.substring(0, 100)} ..."
        holder.rate.text = movieData.voteAverage

        Glide.with(context).load("$BaseUrl${movieData.posterPath}").into(holder.posterImage)
        holder.lang.text = Utile.getLangiage(movieData.language)
        holder.movieFav.setImageResource(R.drawable.ic_fav)
    }

    fun setItems(list: ArrayList<MovieData>) {
        movieData.clear()
        this.movieData.addAll(list)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title = view.movieTitle
        val posterImage = view.posterImage
        val date = view.date
        val desc = view.movieDesc
        val lang = view.lang
        val rate = view.movieRating
        val movieFav = view.movieFav

    }
}