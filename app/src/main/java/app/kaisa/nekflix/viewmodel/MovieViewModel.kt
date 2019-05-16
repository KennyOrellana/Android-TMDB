package app.kaisa.nekflix.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import app.kaisa.nekflix.model.Movie
import app.kaisa.nekflix.repository.TmdbRepository

class MovieViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TmdbRepository = TmdbRepository(application)
    val moviesPopular: LiveData<PagedList<Movie>>
    val moviesTopRated: LiveData<PagedList<Movie>>
    val moviesUpcoming: LiveData<PagedList<Movie>>

    init {
        moviesPopular = repository.movieListPopular
        moviesTopRated = repository.movieListTopRated
        moviesUpcoming = repository.movieListUpcoming
    }
}