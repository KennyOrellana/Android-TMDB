package app.kaisa.nekflix.ui.home

import android.app.ActivityOptions
import android.os.Bundle
import android.util.Pair as UtilPair // Rename the Pair class from the Android framework to avoid a name clash
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import app.kaisa.nekflix.R
import app.kaisa.nekflix.adapter.HomeAdapter
import app.kaisa.nekflix.model.MovieType
import app.kaisa.nekflix.utils.NavigationManager
import app.kaisa.nekflix.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.fragment_home_slide.*

class HomeFragmentSlide(private val movieType: MovieType) : Fragment() {
    lateinit var adapter: HomeAdapter

    constructor() : this(MovieType.POPULAR) //Default constructor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home_slide, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        recyclerView.layoutManager = GridLayoutManager(context, GRID_COLUMNS)
        adapter = HomeAdapter(requireContext()) { movie, view ->
            val options = ActivityOptions.makeSceneTransitionAnimation(
                activity,
                UtilPair.create(view, "poster"),
                UtilPair.create(activity?.toolbar as? View, "toolbar")
            )
            NavigationManager.handle(activity, movie, options)
        }

        val viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.getMovieList(movieType).observe(this, Observer { adapter.submitList(it) })
        recyclerView.adapter = adapter
    }

    fun getTitle(): String {
        return when(movieType){
            MovieType.POPULAR -> "Popular"
            MovieType.TOP_RATED -> "Top Rated"
            MovieType.UPCOMING -> "Up Coming"
        }
    }

    fun getIcon(): Int {
        return when(movieType){
            MovieType.POPULAR -> R.drawable.ic_whatshot_accent_24dp
            MovieType.TOP_RATED -> R.drawable.ic_favorite_accent_24dp
            MovieType.UPCOMING -> R.drawable.ic_flight_land_accent_24dp
        }
    }

    companion object {
        const val GRID_COLUMNS = 3
    }
}