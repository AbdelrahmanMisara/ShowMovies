package com.moetaz.popularmoviesapp.api

import com.moetaz.popularmoviesapp.models.Response
import com.moetaz.popularmoviesapp.models.TrailersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("popular")
    fun getPopularMovies(@Query("api_key") apikey : String ,
                         @Query("page") page: Int) : Call<Response>

    @GET("top_rated")
    fun getTopMovies(@Query("api_key") apikey : String ,
                         @Query("page") page: Int) : Call<Response>

    @GET("{movie_id}/videos")
    fun getTrailers(@Path(value = "movie_id") id :String, @Query("api_key") apikey : String
                     ) : Call<TrailersResponse>
}