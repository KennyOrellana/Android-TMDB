package app.kaisa.nekflix.repository

import androidx.paging.PagedList
import app.kaisa.nekflix.model.Movie
import app.kaisa.nekflix.model.MovieType

class MovieBoundaryCallback(private val repository: TmdbRepository, private val movieType: MovieType) : PagedList.BoundaryCallback<Movie>() {

    override fun onZeroItemsLoaded() {
        repository.requestMovies(1, movieType)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        repository.requestMovies(itemAtEnd.page + 1, movieType)
    }
}