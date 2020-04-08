package com.eroom.erooja.feature.addGoal.newGoalPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.eroom.erooja.R

/**
 * A simple [Fragment] subclass.
 */
class GoalPeriodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal_period, container, false)
    }

}
