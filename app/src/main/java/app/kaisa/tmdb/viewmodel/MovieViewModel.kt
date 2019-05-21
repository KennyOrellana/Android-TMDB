package app.kaisa.tmdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import app.kaisa.tmdb.model.Movie
import app.kaisa.tmdb.model.MovieType
import app.kaisa.tmdb.repository.TmdbRepository

class MovieViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TmdbRepository = TmdbRepository(application)
    private val moviesPopular: LiveData<PagedList<Movie>>
    private val moviesTopRated: LiveData<PagedList<Movie>>
    private val moviesUpcoming: LiveData<PagedList<Movie>>

    init {
        moviesPopular = repository.movieListPopular
        moviesTopRated = repository.movieListTopRated
        moviesUpcoming = repository.movieListUpcoming
    }

    fun getMovieList(movieType: MovieType): LiveData<PagedList<Movie>> {
        return when(movieType) {
            MovieType.POPULAR -> moviesPopular
            MovieType.TOP_RATED -> moviesTopRated
            MovieType.UPCOMING -> moviesUpcoming
        }
    }
}