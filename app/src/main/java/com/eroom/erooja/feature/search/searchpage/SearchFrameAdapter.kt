package com.eroom.erooja.feature.search.searchpage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eroom.erooja.feature.search.searchframe.SearchJobClassFragment
import com.eroom.erooja.feature.search.searchframe.SearchJobGoalFragment

class SearchFrameAdapter (fa: FragmentActivity): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SearchJobClassFragment.newInstance()
            else -> SearchJobGoalFragment.newInstance()
        }
    }
    override fun getItemCount():Int = 2

//    SearchNoContentFragment.newInstance()

}