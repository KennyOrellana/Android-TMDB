package app.kaisa.nekflix.ui.landing

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.kaisa.nekflix.R
import app.kaisa.nekflix.model.Movie
import app.kaisa.nekflix.utils.NavigationManager.PARAM_CONTENT
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_landing_movie.*

class MovieLandingActivity : AppCompatActivity() {
    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_movie)
        loadData()
        setupToolbar()
        setupUI()
    }

    private fun loadData(){
        movie = intent.getSerializableExtra(PARAM_CONTENT) as Movie
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupUI(){
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
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}