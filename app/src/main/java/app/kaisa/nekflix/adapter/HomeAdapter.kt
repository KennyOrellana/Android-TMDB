package app.kaisa.nekflix.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.kaisa.nekflix.R
import app.kaisa.nekflix.model.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_home_movie.view.*

class HomeAdapter(val context: Context, val onClickListener: (movie: Movie, view: View) -> Unit): PagedListAdapter<Movie, HomeAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_home_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindTo(holder, getItem(position))
    }

    inner class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val ivBackground: ImageView = view.imageViewBrackground

        fun bindTo(vh: MovieViewHolder, movie: Movie?){
            Glide.with(context)
                .load(movie?.getPosterUrl())
                .placeholder(android.R.color.black)
                .into(ivBackground)

            movie?.let { item -> vh.itemView.setOnClickListener {
                it.transitionName = "poster"
                onClickListener(item, it)
            } }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
        }
    }
}