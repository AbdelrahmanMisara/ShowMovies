package com.moetaz.popularmoviesapp.ui.fragments


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.moetaz.popularmoviesapp.R
import com.moetaz.popularmoviesapp.adapters.ImagesAdapter
import com.moetaz.popularmoviesapp.models.Movie
import com.moetaz.popularmoviesapp.models.Trailer
import com.moetaz.popularmoviesapp.utilities.Constants
import com.moetaz.popularmoviesapp.utilities.Utile
import com.moetaz.popularmoviesapp.viewmodel.DetailFragmentViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() ,ImagesAdapter.OnImageClicked {

    var trailerId:  String = ""
    override fun onClick(trailer: Trailer) {
     }

    lateinit var movie : Movie
    private val BaseUrl = "http://image.tmdb.org/t/p/w185/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = activity!!.intent.getParcelableExtra<Movie>("movie")
    }

    lateinit var viewModel : DetailFragmentViewModel
    var trailers : ArrayList<Trailer> = ArrayList()
    lateinit var imagesAdapter: ImagesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.moetaz.popularmoviesapp.R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()

        rcMovieImages.layoutManager = LinearLayoutManager(context ,LinearLayoutManager.HORIZONTAL , true)
        viewModel = ViewModelProviders.of(this).get(DetailFragmentViewModel::class.java)
        imagesAdapter = ImagesAdapter(trailers , context!!)
        rcMovieImages.adapter = imagesAdapter

        viewModel.getTrailers("${movie.id}",Constants.API_KEY)
            .observe(viewLifecycleOwner , Observer {
                if (it.results.size > 0) {
                    trailers.addAll(it.results)
                    trailerId = it.results.get(0).key
                    imagesAdapter.notifyDataSetChanged()
                 }
            })
        imagesAdapter.onImageClicked = this

        fabPlay.setOnClickListener {
            if (trailerId.isNotBlank()){
                playTrailer(trailerId)
            }else{
                Toast.makeText(activity,"Something went wrong" , Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun initializeUI() {

        movieDesc.text = movie.overview
        Glide.with(this)
            .load("${BaseUrl}${movie.poster_path}").centerCrop().into(moviethumbnail)
        Glide.with(this)
            .load("${BaseUrl}${movie.poster_path}").into(moviePoster)
        ratingNum.text = movie.vote_average.toString()
        ratingBar.rating = movie.vote_average.toFloat() / 2
        movieDesc.text = movie.overview
        movieTitle.text = movie.title
        date.text = movie.release_date
        lang.text = Utile.getLangiage(movie.original_language)
        if (movie.isFav)
            fav.setImageResource(R.drawable.ic_fav)
        else
            fav.setImageResource(R.drawable.ic_unfav)
    }

    private fun playTrailer(id: String) {
        val appintent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$id"))
        try {
            startActivity(appintent)
        } catch (e: ActivityNotFoundException) {
            startActivity(webintent)
        }

    }


}
