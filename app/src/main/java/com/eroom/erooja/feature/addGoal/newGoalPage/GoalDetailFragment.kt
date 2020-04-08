package com.eroom.erooja.feature.addGoal.newGoalPage

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData

import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentGoalDetailBinding


class GoalDetailFragment : Fragment() {
    private lateinit var goalDetailBinding: FragmentGoalDetailBinding
    val goalDetailContent: MutableLiveData<String> = MutableLiveData()
    //var goalDetailContentCheck: MutableLiveData<Boolean> = MutableLiveData(false)

    companion object {
        @JvmStatic
        fun newInstance() = GoalDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setUpDataBinding(inflater, container)
        initView()
        return goalDetailBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        goalDetailBinding = FragmentGoalDetailBinding.inflate(inflater, container, false)
        goalDetailBinding.fragment = this
    }

    private fun initView() {
        goalDetailBinding.goalDetailContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                goalDetailContent.value = s.toString()  //activity에서 계속 보고있게 해도 되나?

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }


    fun onLayoutClicked() {
        goalDetailBinding.goalDetailContent.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        context?.let { imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY) }
    }
}
