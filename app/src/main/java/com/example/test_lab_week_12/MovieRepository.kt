package com.example.test_lab_week_12

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test_lab_week_12.model.Movie

class MovieRepository(private val movieService: MovieService) {

    private val apiKey = "4ebb92bb9df6dd74c5c162ab29810a81"

    private val moviesLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = moviesLiveData

    private val errorLiveData = MutableLiveData<String>()
    val error: LiveData<String>
        get() = errorLiveData

    suspend fun fetchMovies() {
        try {
            val response = movieService.getPopularMovies(apiKey)
            moviesLiveData.postValue(response.results)
        } catch (e: Exception) {
            errorLiveData.postValue("Error: ${e.message}")
        }
    }
}
