package app.kaisa.tmdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.kaisa.tmdb.model.Movie
import app.kaisa.tmdb.model.Video
import app.kaisa.tmdb.repository.TmdbRepository

class MovieDetailViewModel(application: Application, movie: Movie) : AndroidViewModel(application){
    private val repository: TmdbRepository = TmdbRepository(application)
    var movieDetail: LiveData<Movie>
    var movieVideos: LiveData<ArrayList<Video>>

    init {
        movieDetail = repository.requestMovieDetail(movie.id)
        movieVideos = repository.requestMovieVideos(movie.id)
    }
}