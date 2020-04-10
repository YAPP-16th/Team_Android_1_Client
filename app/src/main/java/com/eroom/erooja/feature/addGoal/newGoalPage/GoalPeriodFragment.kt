package com.eroom.erooja.feature.addGoal.newGoalPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentGoalPeriodBinding
import com.eroom.erooja.feature.addGoal.newGoalFrame.NewGoalActivity
import java.text.SimpleDateFormat
import java.util.*


class GoalPeriodFragment : Fragment() {
    private lateinit var goalPeriodBinding: FragmentGoalPeriodBinding
    //val
    companion object {
        @JvmStatic
        fun newInstance() = GoalPeriodFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setUpDataBinding(inflater, container)
        initView()
        return goalPeriodBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        goalPeriodBinding = FragmentGoalPeriodBinding.inflate(inflater, container, false)
        goalPeriodBinding.fragment = this
    }

    private fun initView() {
        setStartDate()
    }

    private fun setStartDate() {
        val currentTime: Date = Calendar.getInstance().time
        goalPeriodBinding.startDateContent.text = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime)
    }

    fun onCalendarClicked() {
        (activity as NewGoalActivity).calendarCall()
    }

    fun setEndDate(endDate:String) {
        goalPeriodBinding.endDateContent.text = endDate

    }
}
