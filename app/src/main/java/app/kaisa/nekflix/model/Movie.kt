package app.kaisa.nekflix.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.kaisa.nekflix.api.ImageUrlBuilder
import app.kaisa.nekflix.utils.DateUtils
import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
    var type: MovieType?
) : Serializable {

    fun getPosterUrl() = ImageUrlBuilder.getUrl(posterPath)
    fun getBackdropUrl() = ImageUrlBuilder.getUrl(backdropPath)
    fun getYear() = DateUtils.getYear(releaseDate)

    fun getLikes(): String {
        return when {
            voteCount == 1 -> "1 Like"
            voteCount != null && voteCount > 1 -> "$voteCount Likes"
            else -> "No Votes"
        }
    }

    fun getStars(): String {
        return when {
            voteAverage != null && voteAverage > 0 -> "$voteAverage / 10"
            else -> "No Reviews"
        }
    }
}