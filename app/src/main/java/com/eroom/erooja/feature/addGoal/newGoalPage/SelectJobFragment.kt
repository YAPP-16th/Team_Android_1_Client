package com.eroom.erooja.feature.addGoal.newGoalPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.eroom.erooja.R


class SelectJobFragment : Fragment() {
    //private lateinit var selectJobBinding
    //val job : Mu
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance() = SelectJobFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //setUpDataBinding(inflater, container)

        return inflater.inflate(R.layout.fragment_select_job, container, false)
    }


}
