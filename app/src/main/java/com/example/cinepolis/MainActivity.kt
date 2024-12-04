package com.example.cinepolis

import Movie
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
    private lateinit var adapterAction: AdapterMovie
    private lateinit var adapterFantasy: AdapterMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        fetchData()

        enableFullscreenWithButtons()
    }

    @SuppressLint("InlinedApi")
    fun enableFullscreenWithButtons() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) // Mantiene la barra de navegación visible
    }

    private fun setupRecyclerView() {
        adapterPremiere = AdapterMovie(emptyList(), this)
        adapterHorror = AdapterMovie(emptyList(), this)
        adapterAction = AdapterMovie(emptyList(), this) // Adaptador para la categoría Acción
        adapterFantasy = AdapterMovie(emptyList(), this) // Adaptador para la categoría Fantasía

        binding.rvPremiere.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPremiere.adapter = adapterPremiere

        binding.rvHorror.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHorror.adapter = adapterHorror

        binding.rvFantasia.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)  // Cambié rvFantasy por rvFantasia
        binding.rvFantasia.adapter = adapterFantasy // Cambié rvFantasy por rvFantasia

        binding.rvAccion.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)  // Cambié rvAction por rvAccion
        binding.rvAccion.adapter = adapterAction // Cambié rvAction por rvAccion
    }

    private fun fetchData() {
        val call = Connection.responseEngine().create(EndPoints::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = call.getDataMovies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { movies ->
                        // Filtrar las películas por cada categoría
                        val premiereMovies = movies.filter { it.category == "estreno" }
                        val horrorMovies = movies.filter { it.category == "horror" }
                        val actionMovies = movies.filter { it.category == "accion" } // Nuevas películas de Acción
                        val fantasyMovies = movies.filter { it.category == "fantasia" } // Nuevas películas de Fantasía

                        // Actualizar los adaptadores con las películas filtradas
                        adapterPremiere.updateData(premiereMovies)
                        adapterHorror.updateData(horrorMovies)
                        adapterAction.updateData(actionMovies) // Actualizar el adaptador de Acción
                        adapterFantasy.updateData(fantasyMovies) // Actualizar el adaptador de Fantasía
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
            putExtra("url", movie.url)
        }
        startActivity(intent)
    }
}
