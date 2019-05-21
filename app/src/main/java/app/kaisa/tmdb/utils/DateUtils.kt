package app.kaisa.tmdb.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val DATE_FORMAT_YEAR = SimpleDateFormat("yyyy", Locale.US)

    private fun toDate(string: String): Date? {
        return DATE_FORMAT.parse(string)
    }

    fun getYear(string: String?): String {
        if(string == null || string.isEmpty()) return ""

        return try {
            DATE_FORMAT_YEAR.format(toDate(string))
        } catch (e: Exception) {
            ""
        }
    }
}