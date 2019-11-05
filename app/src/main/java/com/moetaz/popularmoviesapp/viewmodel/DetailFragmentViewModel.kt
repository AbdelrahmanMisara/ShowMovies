package com.moetaz.popularmoviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.moetaz.popularmoviesapp.api.ApiClient
import com.moetaz.popularmoviesapp.models.TrailersResponse

class DetailFragmentViewModel : ViewModel() {

    private  var response : LiveData<TrailersResponse>? = null
    private var apiClient = ApiClient()


    fun getTrailers(id : String ,apikey : String ) =
        response ?: apiClient.getTrailers(id,apikey ).also { response = it }

}