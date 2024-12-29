package com.example.cinepolis

import Movie
import retrofit2.Response
import retrofit2.http.GET

interface EndPoints {
    @GET("/peliculas")
    suspend fun getDataMovies(): Response<List<Movie>>
}
