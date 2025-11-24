package com.example.test_lab_week_12

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.test_lab_week_12.model.Movie
import com.google.android.material.snackbar.Snackbar

class MainActivity : ComponentActivity(), MovieAdapter.MovieClickListener {

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.movie_list)

        movieAdapter = MovieAdapter(this)
        recyclerView.adapter = movieAdapter

        val movieRepository = (application as MovieApplication).movieRepository

        val movieViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MovieViewModel(movieRepository) as T
                }
            }
        )[MovieViewModel::class.java]

        // 🔹 SEMENTARA: tampilkan semua movie tanpa filter
        movieViewModel.popularMovies.observe(this) { movies ->
            movieAdapter.addMovies(movies)
        }

        movieViewModel.error.observe(this) { error ->
            if (error.isNotEmpty()) {
                Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.EXTRA_TITLE, movie.title ?: "")
            putExtra(DetailsActivity.EXTRA_RELEASE, movie.releaseDate ?: "")
            putExtra(DetailsActivity.EXTRA_OVERVIEW, movie.overview ?: "")
            putExtra(DetailsActivity.EXTRA_POSTER, movie.posterPath ?: "")
        }
        startActivity(intent)
    }

}
