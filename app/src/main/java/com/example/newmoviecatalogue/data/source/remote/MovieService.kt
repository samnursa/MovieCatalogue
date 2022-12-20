package com.example.newmoviecatalogue.data.source.remote

import com.example.newmoviecatalogue.BuildConfig.API_KEY
import com.example.newmoviecatalogue.data.model.Movie
import com.example.newmoviecatalogue.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    suspend fun getMovie(
        @Query("page") page: String = "1",
        @Query("api_key") api_key: String = API_KEY
    ): Response<Movie>
}