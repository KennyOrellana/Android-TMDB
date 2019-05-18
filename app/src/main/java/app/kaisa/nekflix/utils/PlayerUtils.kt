package app.kaisa.nekflix.utils

import android.content.Context
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YtFile
import android.util.SparseArray



object PlayerUtils {
    fun getYouTubeUrl(context: Context, url: String, callback: (url: String) -> Unit) {
        object : YouTubeExtractor(context) {
            public override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta) {
                if (ytFiles != null) {
                    val itag = 22
                    callback(ytFiles.get(itag).url)
                }
            }
        }.extract(url, true, true)
    }

}