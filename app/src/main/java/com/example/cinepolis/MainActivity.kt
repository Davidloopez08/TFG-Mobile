package com.example.cinepolis

import Connection
import Movie
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinepolis.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bson.Document

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

        // Llamar a la función que trae los datos de MongoDB
        fetchDataFromMongoDB()

        enableFullscreenWithButtons()
    }

    @SuppressLint("InlinedApi")
    fun enableFullscreenWithButtons() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun setupRecyclerView() {
        adapterPremiere = AdapterMovie(emptyList(), this)
        adapterHorror = AdapterMovie(emptyList(), this)
        adapterAction = AdapterMovie(emptyList(), this)
        adapterFantasy = AdapterMovie(emptyList(), this)

        binding.rvPremiere.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPremiere.adapter = adapterPremiere

        binding.rvHorror.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHorror.adapter = adapterHorror

        binding.rvFantasia.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFantasia.adapter = adapterFantasy

        binding.rvAccion.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvAccion.adapter = adapterAction
    }

    private fun fetchDataFromMongoDB() {
        val collection = Connection().getPeliculasCollection()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movies = collection.find().flatMap { document ->
                    // Acceder al array "peliculas"
                    val peliculasArray = document.getList("peliculas", Document::class.java)

                    // Mapeo de los datos a objetos Movie
                    peliculasArray.map { pelicula ->
                        Movie(
                            id = pelicula.getInteger("id"),
                            title = pelicula.getString("title"),
                            year = pelicula.getInteger("year"),
                            description = pelicula.getString("description"),
                            photo = pelicula.getString("photo"),
                            url = pelicula.getString("url"),
                            category = pelicula.getString("category")
                        )
                    }
                }.toList()

                // Debug: Imprimir películas obtenidas
                println("Películas obtenidas: $movies")

                withContext(Dispatchers.Main) {
                    // Filtrar por categorías
                    val premiereMovies = movies.filter { it.category == "estreno" }
                    val horrorMovies = movies.filter { it.category == "horror" }
                    val actionMovies = movies.filter { it.category == "accion" }
                    val fantasyMovies = movies.filter { it.category == "fantasia" }

                    // Actualizar adaptadores
                    adapterPremiere.updateData(premiereMovies)
                    adapterHorror.updateData(horrorMovies)
                    adapterAction.updateData(actionMovies)
                    adapterFantasy.updateData(fantasyMovies)
                }
            } catch (e: Exception) {
                println("Error al obtener datos: ${e.message}")
            }
        }
    }



    override fun OnClick(movie: Movie) {
        // Manejar el clic en una película (esto ya lo tienes)
        val intent = Intent(this, PlayMovieActivity::class.java).apply {
            putExtra("url", movie.url)
        }
        startActivity(intent)
    }
}
