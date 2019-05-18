package app.kaisa.nekflix.model

import com.google.gson.annotations.SerializedName

class MovieVideosResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val videos: ArrayList<Video>
)