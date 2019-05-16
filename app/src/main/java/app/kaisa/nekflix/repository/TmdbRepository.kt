package app.kaisa.nekflix.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.*
import app.kaisa.nekflix.api.ApiClient
import app.kaisa.nekflix.db.MovieDao
import app.kaisa.nekflix.db.TmDB
import app.kaisa.nekflix.model.Movie
import app.kaisa.nekflix.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class TmdbRepository(private val application: Application) {
    private val pagingConfig = Config(
        pageSize = 20,
        enablePlaceholders = true
    )

    private val movieList: LiveData<PagedList<Movie>>

    private val movieDao: MovieDao

    private val db by lazy {
        TmDB.getDB(application)
    }
    private val api by lazy {
        ApiClient.api
    }

    init {
        movieDao = db.moviesDao()

        movieList = LivePagedListBuilder(
            movieDao.loadMovies(),
            pagingConfig
        ).setBoundaryCallback(MovieBoundaryCallback(this))
        .build()
    }

    fun getMovies(): LiveData<PagedList<Movie>> {
        return movieList
    }

    fun requestMovies(page: Int) {

        api.getMoviesPopular(page).enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                //TODO handle errors
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        Executors.newSingleThreadExecutor().execute {
                            it.setPageNumber()
                            movieDao.insertMovies(it.results)
                        }
                    }
                }
            }
        })
    }
}