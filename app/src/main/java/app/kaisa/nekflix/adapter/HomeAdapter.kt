package app.kaisa.nekflix.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.kaisa.nekflix.R
import app.kaisa.nekflix.model.Movie
import kotlinx.android.synthetic.main.cell_home_movie.view.*

class HomeAdapter(context: Context): PagedListAdapter<Movie, HomeAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_home_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindTo(getItem(position), position)
    }

    inner class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.textViewTitle

        fun bindTo(movie: Movie?, position: Int){
            tvTitle.text = "${movie?.page} ${movie?.title ?: "Place Holder"}"
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
        }
    }
}