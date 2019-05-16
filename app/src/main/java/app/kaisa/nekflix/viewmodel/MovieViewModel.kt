package app.kaisa.nekflix.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import app.kaisa.nekflix.model.Movie
import app.kaisa.nekflix.repository.TmdbRepository

class MovieViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TmdbRepository = TmdbRepository(application)
    val allMovies: LiveData<PagedList<Movie>>

    init {
        allMovies = repository.getMovies()
    }

    fun getMovies(): LiveData<PagedList<Movie>> {
        return allMovies
    }
}