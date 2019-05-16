package app.kaisa.nekflix.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.kaisa.nekflix.R
import app.kaisa.nekflix.adapter.HomeAdapter
import app.kaisa.nekflix.api.ApiClient
import app.kaisa.nekflix.db.TmDB
import app.kaisa.nekflix.model.MovieResponse
import app.kaisa.nekflix.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        val viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        adapter = HomeAdapter(this)
        viewModel.getMovies().observe(this, Observer { adapter.submitList(it) })
        recyclerView.adapter = adapter
    }

}