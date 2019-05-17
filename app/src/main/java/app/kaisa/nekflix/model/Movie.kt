package app.kaisa.nekflix.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.kaisa.nekflix.api.ImageUrlBuilder
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
open class Movie(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("vote_count")
    val voteCount: Int?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("page")
    var page: Int = -1,
    @SerializedName("type")
    var type: MovieType?){

    fun getPosterUrl() = ImageUrlBuilder.getUrl(posterPath)
}