package com.moetaz.popularmoviesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_movies")
data class MovieData(@PrimaryKey val id : String, val language : String
                     , val overview : String, val posterPath : String,
                     val date : String, val title : String,
                     val voteAverage : String) {
}