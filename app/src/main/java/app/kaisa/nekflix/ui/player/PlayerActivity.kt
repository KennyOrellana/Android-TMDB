package app.kaisa.nekflix.ui.player

import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import android.net.Uri
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import app.kaisa.nekflix.R
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import kotlinx.android.synthetic.main.activity_player.*




class PlayerActivity : AppCompatActivity() {
    private lateinit var player: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOrientation()
        setContentView(R.layout.activity_player)
        setupPlayer()
        setupMediaSource()
    }

    private fun setupOrientation(){
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION )
    }

    private fun setupPlayer(){
        player = ExoPlayerFactory.newSimpleInstance(this)
        player.playWhenReady = true
        player_view.player = player

        player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)

                player_view.keepScreenOn = !(playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED || !playWhenReady)
                player_view.resizeMode
            }
        })
    }

    private fun setupMediaSource(){
        val url = intent.getStringExtra(PARAM_URL)
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "tmdb"))

        val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(url))

        player.prepare(videoSource)
    }

    override fun onPause() {
        super.onPause()
        player_view?.onPause()
    }

    override fun onResume() {
        super.onResume()
        player_view?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }

    companion object {
        const val PARAM_URL = "param_url"
    }
}
