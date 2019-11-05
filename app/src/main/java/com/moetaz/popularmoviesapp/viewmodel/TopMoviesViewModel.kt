package com.moetaz.popularmoviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.moetaz.popularmoviesapp.api.ApiClient
import com.moetaz.popularmoviesapp.models.Response

class TopMoviesViewModel : ViewModel() {

    private  var response : LiveData<Response>? = null
    private var apiClient = ApiClient()

    fun getTopMovies(apikey : String , page : Int) =
        response ?: apiClient.getTopMovies(apikey ,page).also { response = it }
}