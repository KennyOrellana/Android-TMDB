package app.kaisa.tmdb.model

enum class MovieType(val type: Int) {
    POPULAR(1),
    TOP_RATED(2),
    UPCOMING(3);

    companion object {
        fun get(value: Int) = values().find { it.type == value }
    }
}