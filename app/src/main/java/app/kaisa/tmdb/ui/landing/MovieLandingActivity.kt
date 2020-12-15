package app.kaisa.tmdb.ui.landing

import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import app.kaisa.tmdb.R
import app.kaisa.tmdb.adapter.LandingMovieAdapter
import app.kaisa.tmdb.model.Movie
import app.kaisa.tmdb.model.Video
import app.kaisa.tmdb.utils.NavigationManager
import app.kaisa.tmdb.utils.NavigationManager.PARAM_CONTENT
import app.kaisa.tmdb.viewmodel.MovieDetailViewModel
import app.kaisa.tmdb.viewmodel.MovieDetailViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.tmdb_activity_landing_movie.*
import kotlinx.android.synthetic.main.tmdb_toolbar.*

class MovieLandingActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tmdb_activity_landing_movie)
        setupToolbar()
        loadData()
        setupVideos()
    }

    private fun loadData(){
        val movieTemp = intent.getSerializableExtra(PARAM_CONTENT) as Movie
        setupUI(movieTemp) //Temporal data
        viewModel = ViewModelProviders.of(this, MovieDetailViewModelFactory(application, movieTemp)).get(MovieDetailViewModel::class.java)
        viewModel.movieDetail.observe(this, Observer { setupUI(it) })
        viewModel.movieVideos.observe(this, Observer { setupBanner(it) })
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupUI(movie: Movie){
        Glide.with(this)
            .load(movie.getBackdropUrl())
            .placeholder(R.color.tmdbColorPrimary)
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return false }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    gradientBanner.visibility = View.VISIBLE
                    return false
                }
            })
            .into(imageViewBanner)

        Glide.with(this)
            .load(movie.getPosterUrl())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(android.R.color.black)
            .into(imageViewPoster)

        textViewTitle.text = movie.title
        textViewVotes.text = movie.getLikes()
        textViewStars.text = movie.getStars()
        textViewDate.text = movie.getYear()
        textViewDescription.text = movie.overview

        if(movie.tagline?.isNotEmpty() == true){
            textViewTagline.text = movie.tagline
            textViewTagline.visibility = View.VISIBLE
        } else {
            textViewTagline.visibility = View.GONE
        }
    }

    private fun setupBanner(videos: ArrayList<Video>){
        if(videos.isNotEmpty() && !isDestroyed && !isFinishing){

            var video = videos.find { it.isTrailer() && it.urlPlayback != null}
            if (video == null) {
                video = videos.find { it.urlPlayback?.isNotBlank() == true}
            }

            video?.let { item ->
                image_view_play.visibility = View.VISIBLE
                imageViewBanner.setOnClickListener {
                    NavigationManager.handle(this, item)
                }
            }
        }
    }

    private fun setupVideos(){
        val adapter = LandingMovieAdapter(this) {
            NavigationManager.handle(this, it)
        }

        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.movieVideos.observe(this, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}