package app.kaisa.tmdb.repository

import androidx.paging.PagedList
import app.kaisa.tmdb.model.Movie
import app.kaisa.tmdb.model.MovieType

class MovieBoundaryCallback(private val repository: TmdbRepository, private val movieType: MovieType) : PagedList.BoundaryCallback<Movie>() {

    override fun onZeroItemsLoaded() {
        repository.requestMovies(1, movieType)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        repository.requestMovies(itemAtEnd.page + 1, movieType)
    }
}