package app.kaisa.nekflix.repository

import androidx.paging.PagedList
import app.kaisa.nekflix.model.Movie

class MovieBoundaryCallback(private val repository: TmdbRepository) : PagedList.BoundaryCallback<Movie>() {

    override fun onZeroItemsLoaded() {
        repository.requestMovies(1)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        repository.requestMovies(itemAtEnd.page + 1)
    }
}