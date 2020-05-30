package com.eroom.erooja.app.feature.search.search_detail_frame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eroom.erooja.R

class SearchJobGoalFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = SearchJobGoalFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_job_goal, container, false)
    }

}