package app.kaisa.nekflix.ui.splash

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.kaisa.nekflix.ui.home.MainActivity
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        Timer().schedule(object : TimerTask(){
            override fun run() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }, SPLASH_DELAY)
    }

    companion object {
        private const val SPLASH_DELAY = 1_000L
    }
}