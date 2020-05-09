package com.eroom.erooja.feature.addGoal.newGoalPage

import com.eroom.erooja.databinding.FragmentGoalPeriodOriginBinding
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eroom.erooja.feature.addGoal.newGoalFrame.NewGoalActivity
import com.eroom.erooja.feature.addMyGoalJoin.AddMyListActivity
import java.text.SimpleDateFormat
import java.util.*


class JoinGoalPeroidFragment : Fragment() {
    private lateinit var JoingoalPeriodBinding: FragmentGoalPeriodOriginBinding

    //val
    companion object {
        @JvmStatic
        fun newInstance() = JoinGoalPeroidFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setUpDataBinding(inflater, container)
        initView()
        return JoingoalPeriodBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        JoingoalPeriodBinding = FragmentGoalPeriodOriginBinding.inflate(inflater, container, false)
        JoingoalPeriodBinding.fragment = this
    }

    private fun initView() {
        setDefaultStartDate()
        setDefaultEndDate()
    }

    private fun setDefaultStartDate() {
        val currentTime: Date = Calendar.getInstance().time
        JoingoalPeriodBinding.startDateContent.text = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime)
    }

    private fun setDefaultEndDate() {
        val currentTime: Date = Calendar.getInstance().time
        val endDate = (SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime)).toString()

        val content = SpannableString(endDate)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        JoingoalPeriodBinding.endDateContent.text = content
    }


    fun onCalendarClicked() {
        (activity as AddMyListActivity).calendarCall()
    }

    fun setEndDate(endDate:String) {
        JoingoalPeriodBinding.endDateContent.text = endDate
        val content = SpannableString(endDate)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        JoingoalPeriodBinding.endDateContent.text = content
    }
}