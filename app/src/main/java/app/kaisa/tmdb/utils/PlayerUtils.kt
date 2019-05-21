package app.kaisa.tmdb.utils

import android.content.Context
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YtFile
import android.util.SparseArray
import androidx.lifecycle.MutableLiveData
import app.kaisa.tmdb.model.Video


object PlayerUtils {
    private var requestTotal: Int = 0
    private var requestCount: Int = 0

    fun getYouTubeUrl(context: Context, url: String, callback: (urlVideo: String?, urlThumbnail: String?) -> Unit) {
        object : YouTubeExtractor(context) {
            public override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta) {
                if (ytFiles != null) {
                    val itag = 22
                    callback(ytFiles.get(itag)?.url, vMeta.mqImageUrl)
                } else {
                    callback("", "")
                }
            }

        }.extract(url, true, true)
    }

    fun requestVideoPlaybackData(context: Context, videos: MutableLiveData<ArrayList<Video>>){
        videos.value?.let { list ->
            requestTotal = list.size
            requestCount = 0

            list.forEach {
                getYouTubeUrl(context, it.getYouTubeUrl()){ video, thumbnail ->
                    it.urlPlayback = video
                    it.urlThumbnail = thumbnail?.replace("http://", "https://")
                    checkDataComplete(videos)
                }
            }
        }
    }

    private fun checkDataComplete(videos: MutableLiveData<ArrayList<Video>>){
        requestCount++

        if(requestCount % 3 == 0 || requestCount >= requestTotal){
            videos.notifyObserver()
        }
    }

    //Used to notify liveData's observers
    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}