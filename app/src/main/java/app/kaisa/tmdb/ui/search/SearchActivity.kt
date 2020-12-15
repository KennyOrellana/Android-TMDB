package app.kaisa.tmdb.ui.search

import android.app.ActivityOptions
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import app.kaisa.tmdb.R
import app.kaisa.tmdb.adapter.SearchAdapter
import app.kaisa.tmdb.ui.home.HomeFragmentSlide.Companion.GRID_COLUMNS
import app.kaisa.tmdb.utils.NavigationManager
import app.kaisa.tmdb.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.tmdb_activity_search.*
import kotlinx.android.synthetic.main.tmdb_toolbar.*

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tmdb_activity_search)
        setupToolbar()
        setupUI()
        setupViewModel()
        loadQuery(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        loadQuery(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.tmdb_menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
            maxWidth = Integer.MAX_VALUE
            intent?.getStringExtra(SearchManager.QUERY)?.also { setQuery(it, false) }
        }

        return true
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        tvToolbarTitle.visibility = View.GONE
    }

    private fun setupUI(){
        adapter = SearchAdapter(this) { item, view ->
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair.create(view, "poster"),
                Pair.create(toolbar as? View, "toolbar")
            )
            NavigationManager.handle(this, item, options)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, GRID_COLUMNS)
    }

    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.searchResults.observe(this, Observer {
            progressBar?.visibility = View.GONE
            adapter.submitList(it)
            if(it?.isNotEmpty() == true){
                textViewError?.visibility = View.GONE
            } else {
                textViewError?.visibility = View.VISIBLE
            }
        })
    }

    private fun loadQuery(intentData: Intent?){
        if(intentData?.action == Intent.ACTION_SEARCH) {
            intentData.getStringExtra(SearchManager.QUERY)?.also {
                textViewError?.visibility = View.GONE
                progressBar?.visibility = View.VISIBLE
                viewModel.search(it)
            }
        }
    }
}