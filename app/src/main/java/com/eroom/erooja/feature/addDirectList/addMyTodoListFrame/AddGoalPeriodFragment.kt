package com.eroom.erooja.feature.addDirectList.addMyTodoListFrame

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentAddGoalPeriodBinding
import com.eroom.erooja.feature.addDirectList.addMyTodoListPage.AddMyListActivity
import com.eroom.erooja.feature.joinOtherList.joinTodoListFrame.JoinGoalPeriodFragment
import java.text.SimpleDateFormat
import java.util.*


class AddGoalPeriodFragment : Fragment() {
    private lateinit var binding: FragmentAddGoalPeriodBinding

    companion object {
        @JvmStatic
        fun newInstance() = JoinGoalPeriodFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_join_goal_period, container, false)
        setUpDataBinding(inflater, container)
        initView()
        return binding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentAddGoalPeriodBinding.inflate(inflater, container, false)
        binding.fragment = this
    }

    private fun initView() {
        setDefaultStartDate()
        setDefaultEndDate()
    }

    private fun setDefaultStartDate() {
        val currentTime: Date = Calendar.getInstance().time
        binding.startDateContent.text = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime)
    }

    private fun setDefaultEndDate() {
        val currentTime: Date = Calendar.getInstance().time
        val endDate = (SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime)).toString()

        val content = SpannableString(endDate)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        binding.endDateContent.text = content
    }



    fun onCalendarClicked() {
        (activity as AddMyListActivity).calendarCall()
    }

    fun setEndDate(endDate:String) {
        binding.endDateContent.text = endDate
        val content = SpannableString(endDate)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        binding.endDateContent.text = content
    }
}