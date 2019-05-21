package app.kaisa.tmdb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.kaisa.tmdb.model.Movie

class MovieDetailViewModelFactory(private val application: Application, val movie: Movie) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(application, movie) as T
    }
}