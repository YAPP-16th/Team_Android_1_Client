package com.eroom.erooja.feature.search.search_main_frame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eroom.erooja.R

class SearchNoGoalListFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = SearchNoGoalListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_no_goal_list, container, false)
    }

}