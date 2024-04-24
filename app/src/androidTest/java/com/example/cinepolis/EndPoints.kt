package com.example.cinepolis

import retrofit2.Response
import retrofit2.http.GET

interface EndPoints {

    @GET("/FranEspino/demo/db")
    suspend fun getDataMovies(): Response<MovieResponse>


}