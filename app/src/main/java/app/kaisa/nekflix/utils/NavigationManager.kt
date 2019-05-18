package app.kaisa.nekflix.utils

import android.content.Context
import android.content.Intent
import app.kaisa.nekflix.model.Movie
import app.kaisa.nekflix.ui.landing.MovieLandingActivity

object NavigationManager {
    fun handle(context: Context?, movie: Movie){
        val intent = Intent(context, MovieLandingActivity::class.java)
        intent.putExtra(PARAM_CONTENT, movie)
        context?.startActivity(intent)
    }

    const val PARAM_CONTENT = "param_content"
}