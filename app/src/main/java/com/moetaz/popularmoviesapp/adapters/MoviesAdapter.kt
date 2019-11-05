package com.moetaz.popularmoviesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moetaz.popularmoviesapp.R
import com.moetaz.popularmoviesapp.models.Movie
import com.moetaz.popularmoviesapp.utilities.Utile
import kotlinx.android.synthetic.main.movie_row.view.*


class MoviesAdapter(val movies: ArrayList<Movie>, val context: Context) :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    private val BaseUrl = "http://image.tmdb.org/t/p/w185/"

    interface OnMovieClicked {
        fun onClick(movie: Movie)
        fun onFavClick(favIcon : ImageView , movie: Movie)
    }

    interface OnMovieLoaded{
        fun onLoaded(favIcon : ImageView , movie : Movie)
    }

    //listners
    lateinit var onMovieClicked: OnMovieClicked
    lateinit var onMovieLoaded: OnMovieLoaded;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(com.moetaz.popularmoviesapp.R.layout.movie_row, parent, false)
        )
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val slideLeft = AnimationUtils.loadAnimation(context, R.anim.slide_right)
        val slideright = AnimationUtils.loadAnimation(context, R.anim.slide_left)
        if (position % 2 == 0)
        holder.itemView.startAnimation(slideLeft)
        else
            holder.itemView.startAnimation(slideright)
        val movie = movies.get(position)
        initializeUI(holder, movie)
        onMovieLoaded.onLoaded(holder.MovieFav , movie)
        holder.itemView.setOnClickListener {
            onMovieClicked.onClick(movie)
        }

        holder.MovieFav.setOnClickListener {
            onMovieClicked.onFavClick(holder.MovieFav ,movie)
        }

    }

    private fun initializeUI(holder: MyViewHolder, movie: Movie) {

        holder.title.text = movie.original_title
        holder.date.text = movie.release_date
        if (movie.overview.length < 100)
            holder.desc.text = movie.overview
        else
            holder.desc.text = "${movie.overview.substring(0, 100)} ..."
        holder.rate.text = movie.vote_average.toString()

        Glide.with(context).load("$BaseUrl${movie.poster_path}").into(holder.posterImage)
        holder.lang.text = Utile.getLangiage(movie.original_language)
    }

    fun setItems(list: ArrayList<Movie>) {
        movies.clear()
        this.movies.addAll(list)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title = view.movieTitle
        val posterImage = view.posterImage
        val date = view.date
        val desc = view.movieDesc
        val lang = view.lang
        val rate = view.movieRating
        val MovieFav = view.movieFav

    }
}