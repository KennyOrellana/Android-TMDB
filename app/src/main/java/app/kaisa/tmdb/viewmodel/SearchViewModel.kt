package app.kaisa.tmdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.kaisa.tmdb.model.Movie
import app.kaisa.tmdb.repository.TmdbRepository

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TmdbRepository = TmdbRepository(application)
    val searchResults: LiveData<ArrayList<Movie>>

    init {
        searchResults = repository.searchResults
    }

    fun search(query: String, page: Int = 1) {
        repository.searchMovie(query, page)
    }

    //TODO animar transiciones del search al landing, progress bar para loading, mensaje de error search, busqueda local cuando falla online
}