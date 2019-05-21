package app.kaisa.nekflix.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import app.kaisa.nekflix.R
import app.kaisa.nekflix.adapter.SearchAdapter
import app.kaisa.nekflix.ui.home.HomeFragmentSlide.Companion.GRID_COLUMNS
import app.kaisa.nekflix.utils.NavigationManager
import app.kaisa.nekflix.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.recyclerView
import kotlinx.android.synthetic.main.toolbar.*

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
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
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
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
        adapter = SearchAdapter(this) { NavigationManager.handle(this, it) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, GRID_COLUMNS)
    }

    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.searchResults.observe(this, Observer { adapter.submitList(it) })
    }

    private fun loadQuery(intentData: Intent?){
        if(intentData?.action == Intent.ACTION_SEARCH) {
            intentData.getStringExtra(SearchManager.QUERY)?.also {
                viewModel.search(it)
            }
        }
    }
}