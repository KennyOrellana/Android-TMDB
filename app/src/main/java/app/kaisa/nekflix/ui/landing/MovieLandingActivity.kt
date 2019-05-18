package app.kaisa.nekflix.ui.landing

import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.kaisa.nekflix.R
import app.kaisa.nekflix.model.Movie
import app.kaisa.nekflix.model.Video
import app.kaisa.nekflix.utils.NavigationManager
import app.kaisa.nekflix.utils.NavigationManager.PARAM_CONTENT
import app.kaisa.nekflix.viewmodel.MovieDetailViewModel
import app.kaisa.nekflix.viewmodel.MovieDetailViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_landing_movie.*

class MovieLandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_movie)
        setupToolbar()
        loadData()
    }

    private fun loadData(){
        val movieTemp = intent.getSerializableExtra(PARAM_CONTENT) as Movie
        setupUI(movieTemp) //Temporal data
        val viewModel = ViewModelProviders.of(this, MovieDetailViewModelFactory(application, movieTemp)).get(MovieDetailViewModel::class.java)
        viewModel.movieDetail.observe(this, Observer { setupUI(it) })
        viewModel.movieVideos.observe(this, Observer { setupVideos(it) })
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
            .placeholder(R.color.colorPrimary)
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
            .placeholder(android.R.color.black)
            .into(imageViewPoster)

        textViewTitle.text = movie.title
        textViewVotes.text = movie.getLikes()
        textViewStars.text = movie.getStars()
        textViewDate.text = movie.getYear()
        textViewDescription.text = movie.overview

        if(movie.tagline?.isNotEmpty() == true){
            textViewGenres.text = movie.tagline
            textViewGenres.visibility = View.VISIBLE
        } else {
            textViewGenres.visibility = View.GONE
        }
    }

    private fun setupVideos(videos: ArrayList<Video>){
        if(videos.isNotEmpty()){
            imageViewPlay.visibility = View.VISIBLE

            var video = videos.find { it.isTrailer() }

            if (video == null) {
                video = videos[0]
            }
            imageViewBanner?.setOnClickListener { NavigationManager.handle(this, video) }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}