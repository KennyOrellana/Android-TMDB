package app.kaisa.nekflix.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import app.kaisa.nekflix.api.TmdbNetwork
import app.kaisa.nekflix.db.TmdbDatabase
import app.kaisa.nekflix.model.*
import app.kaisa.nekflix.utils.PlayerUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class TmdbRepository(private val application: Application) {

    private val api by lazy { TmdbNetwork.api }
    private val db by lazy { TmdbDatabase.getDB(application) }
    private val movieDao by lazy { db.moviesDao() }

    val movieListPopular: LiveData<PagedList<Movie>> by lazy {
        movieLiveBuilder(this, MovieType.POPULAR)
    }

    val movieListTopRated: LiveData<PagedList<Movie>> by lazy {
        movieLiveBuilder(this, MovieType.TOP_RATED)
    }

    val movieListUpcoming: LiveData<PagedList<Movie>> by lazy {
        movieLiveBuilder(this, MovieType.UPCOMING)
    }

    val searchResults: MutableLiveData<ArrayList<Movie>> by lazy {
        MutableLiveData<ArrayList<Movie>>()
    }

    fun requestMovies(page: Int, movieType: MovieType) {
        val request = when(movieType) {
            MovieType.POPULAR -> api.getMoviesPopular(page)
            MovieType.TOP_RATED -> api.getMoviesTopRated(page)
            MovieType.UPCOMING -> api.getMoviesUpcoming(page)
        }

        request.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                //TODO handle errors
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        Executors.newSingleThreadExecutor().execute {
                            it.setPagingData(movieType)
                            movieDao.insertMovies(it.results)
                        }
                    }
                }
            }
        })
    }

    fun requestMovieDetail(id: Int): LiveData<Movie> {
        val mutableData = MutableLiveData<Movie>()

        api.getMovieDetail(id).enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                //TODO handle errors
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        mutableData.value = it
                    }
                }
            }
        })

        return mutableData
    }

    fun requestMovieVideos(id: Int): LiveData<ArrayList<Video>> {
        val mutableData = MutableLiveData<ArrayList<Video>>()

        api.getMovieVideos(id).enqueue(object : Callback<MovieVideosResponse> {
            override fun onFailure(call: Call<MovieVideosResponse>, t: Throwable) {
                //TODO handle errors
            }

            override fun onResponse(call: Call<MovieVideosResponse>, response: Response<MovieVideosResponse>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        mutableData.value = it.videos
                        Executors.newSingleThreadExecutor().execute {
                            PlayerUtils.requestVideoPlaybackData(application, mutableData)
                        }
                    }
                }
            }
        })

        return mutableData
    }

    fun searchMovie(query: String, page: Int) {
        api.searchMovie(query, page).enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                //TODO handle errors
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        searchResults.value = it.results
                    }
                }
            }
        })
    }

    companion object {
        private val pagingConfig = Config(
            pageSize = 20,
            enablePlaceholders = true
        )

        fun movieLiveBuilder(repository: TmdbRepository, movieType: MovieType): LiveData<PagedList<Movie>> {
            return LivePagedListBuilder(
                repository.movieDao.loadMovies(movieType),
                pagingConfig
            ).setBoundaryCallback(MovieBoundaryCallback(repository, movieType))
            .build()
        }
    }
}