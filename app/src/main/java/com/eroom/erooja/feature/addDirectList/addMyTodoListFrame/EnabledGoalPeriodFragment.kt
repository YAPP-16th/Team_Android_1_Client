package com.eroom.erooja.feature.addDirectList.addMyTodoListFrame

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.add
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentEnabledGoalPariodBinding
import java.text.SimpleDateFormat
import java.util.*


class EnabledGoalPeriodFragment : Fragment() {
    private lateinit var binding: FragmentEnabledGoalPariodBinding

    companion object {
        @JvmStatic
        fun newInstance() = EnabledGoalPeriodFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_enabled_goal_pariod, container, false)
        setUpDataBinding(inflater, container)
        initView()
        return binding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentEnabledGoalPariodBinding.inflate(inflater, container, false)
        binding.fragment = this@EnabledGoalPeriodFragment
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
        val lastTime = arguments?.getString(Consts.END_DATE)
        val endDateList = lastTime!!.split(".")
        binding.endDateContent.text = "20" add endDateList[0] add "년 " add endDateList[1] add "월 " add endDateList[2] add "일"

    }
}