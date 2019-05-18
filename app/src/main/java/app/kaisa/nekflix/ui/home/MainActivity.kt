package app.kaisa.nekflix.ui.home

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import app.kaisa.nekflix.R
import app.kaisa.nekflix.adapter.HomeFragmentStateAdapter
import app.kaisa.nekflix.model.MovieType
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupUI()
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupUI(){
        val adapter = HomeFragmentStateAdapter(this)
        MovieType.values().forEach {
            adapter.addFragment(HomeFragmentSlide(it))
        }

        viewPager.adapter = adapter
        viewPager.setPageTransformer(ZoomOutPageTransformer())
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.setScrollPosition(position, 0F, true)
            }
        })

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = adapter.getFragmentTitle(position)
                tab.setIcon(adapter.getFragmentIcon(position))
        }.attach()
    }

    override fun onBackPressed() {
        if(viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }
}