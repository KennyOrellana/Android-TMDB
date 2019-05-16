package app.kaisa.nekflix.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("results")
    val results: ArrayList<Movie>
){
    fun setPagingData(type: MovieType){
        results.forEach {
            it.page = page
            it.type = type
        }
    }
}