package com.moetaz.popularmoviesapp.data

import androidx.lifecycle.LiveData

class MovieRepository(private val movieDao: MovieDao) {

    val allMovieData : LiveData<List<MovieData>> = movieDao.getAllMovies()

    suspend fun insert(movieData: MovieData){
        movieDao.insert(movieData)
    }

    suspend fun deleteMovie(id: String){
        movieDao.deleteMovie(id)
    }

    fun getMovieById(id : String): LiveData<List<MovieData>>{
        return movieDao.getMovieById(id)
    }

}