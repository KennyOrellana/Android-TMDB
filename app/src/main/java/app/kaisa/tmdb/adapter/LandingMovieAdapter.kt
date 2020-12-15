package app.kaisa.tmdb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.kaisa.tmdb.R
import app.kaisa.tmdb.model.Video
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.tmdb_cell_landing_movie_video.view.*

class LandingMovieAdapter(val context: Context, val clickListener: (video: Video) -> Unit) : ListAdapter<Video, LandingMovieAdapter.VideoViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tmdb_cell_landing_movie_video, parent, false))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }


    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivPlay: ImageView = view.image_view_play
        private val ivThumbnail: ImageView = view.image_view_thumbnail
        private val tvType: TextView = view.text_view_type
        private val tvName: TextView = view.text_view_name

        fun bindTo(item: Video){
            tvType.text = item.type
            tvName.text = item.name

            if(item.urlThumbnail?.isNotEmpty() == true) {
                ivThumbnail.setImageDrawable(null)

                Glide.with(context)
                    .load(item.urlThumbnail)
                    .placeholder(android.R.color.black)
                    .into(ivThumbnail)
            } else {
                ivThumbnail.setImageResource(android.R.color.black)
            }

            if(item.urlPlayback?.isNotEmpty() == true){
                ivPlay.visibility = View.VISIBLE
                ivPlay.setOnClickListener { clickListener(item) }
            } else {
                ivPlay.visibility = View.INVISIBLE
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Video> (){
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem.urlThumbnail == newItem.urlThumbnail
            }
        }
    }
}