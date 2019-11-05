package com.moetaz.popularmoviesapp.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.moetaz.popularmoviesapp.R
import com.moetaz.popularmoviesapp.adapters.MoviesAdapter
import com.moetaz.popularmoviesapp.data.MovieData
import com.moetaz.popularmoviesapp.models.Movie
import com.moetaz.popularmoviesapp.ui.activities.DetailActivity
import com.moetaz.popularmoviesapp.utilities.Constants
import com.moetaz.popularmoviesapp.viewmodel.FavouriteMoviesViewModel
import com.moetaz.popularmoviesapp.viewmodel.TopMoviesViewModel
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_movies_list.*

/**
 * A simple [Fragment] subclass.
 */
class TopMoviesFragment : Fragment() , MoviesAdapter.OnMovieClicked ,MoviesAdapter.OnMovieLoaded{

    lateinit var favouriteMoviesViewModel: FavouriteMoviesViewModel

    //every time onbindveiw called check id movie marked as fav or not
    override fun onLoaded(favIcon: ImageView, movie: Movie) {
        favouriteMoviesViewModel.getMovieById(movie.id.toString())
            .observe(viewLifecycleOwner, Observer {
                if (it.isNotEmpty()) {
                    movie.isFav = true
                    favIcon.setTag("fav")
                    favIcon.setImageResource(R.drawable.ic_fav)
                } else {
                    movie.isFav = false
                    favIcon.setTag("unfav")
                    favIcon.setImageResource(R.drawable.ic_unfav)
                }
            })
    }

    override fun onFavClick(favIcon : ImageView,movie: Movie) {
        if (favIcon.tag.equals("unfav")) {
            movie.isFav = true
            favIcon.tag = "fav"
            favouriteMoviesViewModel.insert(
                MovieData(
                    movie.id.toString(),
                    movie.original_language,
                    movie.overview,
                    movie.poster_path,
                    movie.release_date,
                    movie.original_title,
                    movie.vote_average.toString()
                )
            )
        } else {
            movie.isFav = false
            favIcon.tag = "unfav"
            favouriteMoviesViewModel.deleteMovie(movie.id.toString())
        }
    }


    override fun onClick(movie : Movie) {
        startActivity(
            Intent(activity , DetailActivity::class.java)
                .putExtra("movie" , movie))
    }

    lateinit var viewModel : TopMoviesViewModel
    var movies : ArrayList<Movie> = ArrayList()
    var movies2 : ArrayList<Movie> = ArrayList()
    lateinit var moviesAdapter : MoviesAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val myView = inflater.inflate(R.layout.fragment_movies_list, container, false)
        favouriteMoviesViewModel =
            ViewModelProviders.of(this).get(FavouriteMoviesViewModel::class.java)
        return myView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        moviesRC.layoutManager = LinearLayoutManager(context)
        viewModel = ViewModelProviders.of(this).get(TopMoviesViewModel::class.java)
        moviesAdapter = MoviesAdapter(movies , context!!)
        moviesRC.adapter = moviesAdapter
        viewModel.getTopMovies(Constants.API_KEY , 1)
            .observe(viewLifecycleOwner , Observer {
                movies.clear()
                movies.addAll(it.results)
                movies2.clear()
                movies2.addAll(it.results)
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

        setButtonBackClick()
        setImageSearchClick()
        moviesAdapter.onMovieClicked = this
        moviesAdapter.onMovieLoaded = this
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

    private fun getSerachResult(list: ArrayList<Movie>, key: String): ArrayList<Movie> {
        var temp: ArrayList<Movie> = ArrayList()
        for (movie in list) {
            if (movie.title.toLowerCase().contains(key))
                temp.add(movie)
        }
        return temp
    }


}
