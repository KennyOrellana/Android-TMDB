package app.kaisa.tmdb.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import app.kaisa.tmdb.R
import app.kaisa.tmdb.adapter.HomeFragmentStateAdapter
import app.kaisa.tmdb.model.MovieType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupUI()
    }

    override fun onBackPressed() {
        if(viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
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
}