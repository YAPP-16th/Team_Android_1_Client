
package com.eroom.erooja.feature.addGoal.newGoalPage

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentGoalPeriodBinding
import com.eroom.erooja.feature.addGoal.newGoalFrame.NewGoalActivity
import java.text.SimpleDateFormat
import java.util.*


class GoalPeriodFragment : Fragment() {
    private lateinit var goalPeriodBinding: FragmentGoalPeriodBinding
    var isChangeable: MutableLiveData<Boolean> = MutableLiveData(true) //초기값 : 수정가능

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
        setDefaultStartDate()
        setDefaultEndDate()
        setRadioButtonListener()
    }

    private fun setDefaultStartDate() {
        val currentTime: Date = Calendar.getInstance().time
        goalPeriodBinding.startDateContent.text = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime)
    }

    private fun setDefaultEndDate() {
        val currentTime: Date = Calendar.getInstance().time
        val endDate = (SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime)).toString()

        val content = SpannableString(endDate)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        goalPeriodBinding.endDateContent.text = content
    }

    private fun setRadioButtonListener() {
        goalPeriodBinding.changeableBtn.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            isChangeable.value = isChecked
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.orgDefault))
            } else {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey4))
            }
        })

        goalPeriodBinding.unchangeableBtn.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            isChangeable.value = !isChecked
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.orgDefault))

                //초기 '수정가능'버튼 색 변경 위함
                goalPeriodBinding.changeableBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_default_background)
                goalPeriodBinding.changeableBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey4))

            } else {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey4))
            }
        })
    }



    fun onCalendarClicked() {
        (activity as NewGoalActivity).calendarCall()
    }

    fun setEndDate(endDate:String) {
        goalPeriodBinding.endDateContent.text = endDate
        val content = SpannableString(endDate)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        goalPeriodBinding.endDateContent.text = content
    }
}