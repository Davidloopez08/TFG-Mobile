package com.example.cinepolis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cinepolis.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var adapterPremier: AdapterMovie
    private var listPremierMovie = mutableListOf<Movie>()
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        GlobalScope.launch (Dispatchers.IO){
            val service: EndPoints = Connection.responseEngine().create(EndPoints::class.java)
            val response: Response<MovieResponse> = service.getDataMovies()
            if (response.isSuccessful){
                for (pelicula in response.body()!!.estreno){
                    listPremierMovie.add(pelicula)
                    Log.d("peliculas", listPremierMovie.toString())
                }
            }
        }
    }
}