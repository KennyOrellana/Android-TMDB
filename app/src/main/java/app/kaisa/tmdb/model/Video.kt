package app.kaisa.tmdb.model

import com.google.gson.annotations.SerializedName

class Video (
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("site")
    val site: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("type")
    val type: String,
    var urlPlayback: String?,
    var urlThumbnail: String?
) {
    fun getYouTubeUrl(): String {
        return "https://www.youtube.com/watch?v=$key"
    }

    fun isTrailer(): Boolean {
        return TRAILER.equals(type, true)
    }

    companion object {
        private val TRAILER = "Trailer"
    }
}