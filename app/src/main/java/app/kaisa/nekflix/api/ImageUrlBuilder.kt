package app.kaisa.nekflix.api

import app.kaisa.nekflix.BuildConfig

object ImageUrlBuilder {
    fun getUrl(path: String?, size: String = "w500"): String {
        if(path==null || path.isEmpty()) return ""

        return "${BuildConfig.IMAGE_URL}$size$path"
    }
}