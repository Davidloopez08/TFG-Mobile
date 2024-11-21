package com.example.cinepolis

import Movie
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinepolis.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterPremiere: AdapterMovie
    private lateinit var adapterHorror: AdapterMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        fetchData()
    }

    private fun setupRecyclerView() {
        adapterPremiere = AdapterMovie(emptyList(), this)
        adapterHorror = AdapterMovie(emptyList(), this)

        binding.rvPremiere.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPremiere.adapter = adapterPremiere

        binding.rvHorror.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHorror.adapter = adapterHorror
    }

    private fun fetchData() {
        val call = Connection.Servicio.responseEngine().create(EndPoints::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = call.getDataMovies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { movies ->
                        val premiereMovies = movies.filter { it.category == "estreno" }
                        val horrorMovies = movies.filter { it.category == "horror" }

                        adapterPremiere.updateData(premiereMovies)
                        adapterHorror.updateData(horrorMovies)
                    } ?: run {
                        Toast.makeText(this@MainActivity, "No data available", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun OnClick(movie: Movie) {
        val intent = Intent(this, PlayMovieActivity::class.java).apply {
            putExtra("url", movie.photo)
        }
        startActivity(intent)
    }
}
