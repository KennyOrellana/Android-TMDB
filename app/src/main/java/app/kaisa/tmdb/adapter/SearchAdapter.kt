package app.kaisa.tmdb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.kaisa.tmdb.R
import app.kaisa.tmdb.adapter.HomeAdapter.Companion.DIFF_CALLBACK
import app.kaisa.tmdb.model.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.tmdb_cell_home_movie.view.*

class SearchAdapter(private val context: Context, private val clickListener: (item: Movie, view: View) -> Unit) : ListAdapter<Movie, SearchAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tmdb_cell_home_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivThumbnail: ImageView = view.imageViewBrackground

        fun bindTo(item: Movie){
            if(item.getPosterUrl().isNotEmpty()) {
                ivThumbnail.setImageDrawable(null)

                Glide.with(context)
                    .load(item.getPosterUrl())
                    .placeholder(android.R.color.black)
                    .into(ivThumbnail)
            } else {
                ivThumbnail.setImageResource(android.R.color.black)
            }

            ivThumbnail.setOnClickListener { view ->
                clickListener(item, view)
            }
        }
    }
}