package app.kaisa.nekflix.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.kaisa.nekflix.model.Movie
import app.kaisa.nekflix.model.Video
import app.kaisa.nekflix.repository.TmdbRepository

class MovieDetailViewModel(application: Application, movie: Movie) : AndroidViewModel(application){
    private val repository: TmdbRepository = TmdbRepository(application)
    var movieDetail: LiveData<Movie>
    var movieVideos: LiveData<ArrayList<Video>>

    init {
        movieDetail = repository.requestMovieDetail(movie.id)
        movieVideos = repository.requestMovieVideos(movie.id)
    }
}