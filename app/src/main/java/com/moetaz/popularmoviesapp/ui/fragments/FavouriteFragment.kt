package com.moetaz.popularmoviesapp.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.moetaz.popularmoviesapp.R
import com.moetaz.popularmoviesapp.adapters.FavouriteMoviesAdapter
import com.moetaz.popularmoviesapp.data.MovieData
import com.moetaz.popularmoviesapp.models.Movie
import com.moetaz.popularmoviesapp.ui.activities.DetailActivity
import com.moetaz.popularmoviesapp.viewmodel.FavouriteMoviesViewModel
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_movies_list.*

/**
 * A simple [Fragment] subclass.
 */
class FavouriteFragment : Fragment() , FavouriteMoviesAdapter.OnMovieClicked {
    override fun onFavClick(movieData: MovieData , position: Int) {
        favouriteMoviesViewModel.deleteMovie(movieData.id);
        movies.removeAt(position)
        moviesAdapter.notifyItemRemoved(position)
        moviesAdapter.notifyItemRangeChanged(position , moviesAdapter.itemCount -1)
    }

    override fun onClick(
        movieData: MovieData
    ) {

        startActivity(
            Intent(activity, DetailActivity::class.java)
                .putExtra("movie", Movie(true,false,
                    "",ArrayList<Int>(),movieData.id.toInt(),movieData.language ,movieData.title,movieData.overview,
                    0.0,movieData.posterPath, movieData.date , movieData.title , false , movieData.voteAverage.toDouble(),
                    1)
                )
        )
    }

    private lateinit var favouriteMoviesViewModel: FavouriteMoviesViewModel
    var movies: ArrayList<MovieData> = ArrayList()
    var movies2: ArrayList<MovieData> = ArrayList()

    lateinit var moviesAdapter: FavouriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        app_bar_title.text = "Favourite Movies"
        moviesRC.layoutManager = LinearLayoutManager(context)
        moviesAdapter = FavouriteMoviesAdapter(movies, context!!)
        moviesRC.adapter = moviesAdapter
        favouriteMoviesViewModel = ViewModelProviders.of(this).get(FavouriteMoviesViewModel::class.java)

        favouriteMoviesViewModel.allWords.observe(viewLifecycleOwner, Observer {
            movies.clear()
            movies.addAll(it)
            movies2.clear()
            movies2.addAll(it)
            moviesAdapter.notifyDataSetChanged()
        })
        edittext_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                movies.clear()
                movies.addAll(movies2)
                moviesAdapter.setItems(getSerachResult(movies, "$s"))
                moviesAdapter.notifyDataSetChanged()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        moviesAdapter.onMovieClicked = this
        setButtonBackClick()
        setImageSearchClick()
    }

    private fun setButtonBackClick() {
        backBtn.setOnClickListener {
            backBtn.visibility = View.INVISIBLE
            app_bar_title.visibility = View.VISIBLE
            imageView_search.visibility = View.VISIBLE
            edittext_search.visibility = View.INVISIBLE
        }
    }

    private fun setImageSearchClick() {

        val slideLeft = AnimationUtils.loadAnimation(activity, R.anim.slide_left)
        imageView_search.setOnClickListener {
            backBtn.visibility = View.VISIBLE
            backBtn.startAnimation(slideLeft)
            app_bar_title.visibility = View.INVISIBLE
            imageView_search.visibility = View.INVISIBLE
            edittext_search.visibility = View.VISIBLE
        }
    }

    private fun getSerachResult(list: ArrayList<MovieData>, key: String): ArrayList<MovieData> {
        var temp: ArrayList<MovieData> = ArrayList()
        for (movie in list) {
            if (movie.title.toLowerCase().contains(key))
                temp.add(movie)
        }
        return temp
    }
}
