package com.moetaz.popularmoviesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moetaz.popularmoviesapp.R
import com.moetaz.popularmoviesapp.models.Trailer
import kotlinx.android.synthetic.main.image_row.view.*


class ImagesAdapter (val trailers : List<Trailer>, val context : Context) :
    RecyclerView.Adapter<ImagesAdapter.MyViewHolder>()
{
    private val BaseUrl = "http://image.tmdb.org/t/p/w185/"
    interface OnImageClicked{
        fun onClick(trailer : Trailer)
    }
    lateinit var onImageClicked: OnImageClicked
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.image_row, parent, false))    }

    override fun getItemCount() = trailers.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val trailer = trailers.get(position)
        val url = "https://img.youtube.com/vi/${trailer.key}/0.jpg";
        Glide.with(context).load(url).into(holder.image);

     }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val image = view.image_list_item

    }
}