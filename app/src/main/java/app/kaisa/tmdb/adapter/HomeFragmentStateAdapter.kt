package app.kaisa.tmdb.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.kaisa.tmdb.ui.home.HomeFragmentSlide

class HomeFragmentStateAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragmentList = ArrayList<HomeFragmentSlide>()

//    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getItemCount(): Int = fragmentList.size

    fun addFragment(fragment: HomeFragmentSlide){
        fragmentList.add(fragment)
    }

    fun getFragmentTitle(position: Int) = fragmentList[position].getTitle()
    fun getFragmentIcon(position: Int) = fragmentList[position].getIcon()

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}