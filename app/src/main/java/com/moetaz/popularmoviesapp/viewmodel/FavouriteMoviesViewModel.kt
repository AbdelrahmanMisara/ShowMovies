package com.moetaz.popularmoviesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.moetaz.popularmoviesapp.data.MovieData
import com.moetaz.popularmoviesapp.data.MovieRepository
import com.moetaz.popularmoviesapp.data.MovieRoomDatabase
import kotlinx.coroutines.launch

class FavouriteMoviesViewModel (application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: MovieRepository
    // LiveData gives us updated words when they change.
    val allWords: LiveData<List<MovieData>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val wordsDao = MovieRoomDatabase.getDatabase(application).wordDao()
        repository = MovieRepository(wordsDao)
        allWords = repository.allMovieData
    }

    // The implementation of insert() is completely hidden from the UI.
    // We don't want insert to block the main thread, so we're launching a new
    // coroutine. ViewModels have a coroutine scope based on their lifecycle called
    // viewModelScope which we can use here.
    fun insert(movieData: MovieData) = viewModelScope.launch {
        repository.insert(movieData)
    }

    fun deleteMovie(id : String) = viewModelScope.launch {
        repository.deleteMovie(id)
    }

    fun getMovieById (id : String) :LiveData<List<MovieData>>{
        return repository.getMovieById(id)
    }
}