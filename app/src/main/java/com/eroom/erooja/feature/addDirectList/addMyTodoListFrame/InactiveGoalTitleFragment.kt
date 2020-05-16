package com.eroom.erooja.feature.addDirectList.addMyTodoListFrame

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.databinding.FragmentInactiveGoalTitleBinding


class InactiveGoalTitleFragment : Fragment() {
    private lateinit var binding: FragmentInactiveGoalTitleBinding
    val goalTitle: MutableLiveData<String> = MutableLiveData()

    companion object {
        @JvmStatic
        fun newInstance() = InactiveGoalTitleFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return binding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentInactiveGoalTitleBinding.inflate(inflater, container, false)
        binding.fragment = this
    }

    private fun initView() {
        val titleText = arguments?.getString(Consts.GOAL_TITLE)
        binding.goalTitle.setText(titleText)
        binding.goalTitleLength.text = "${binding.goalTitle.text.length}/50"
    }

    fun onLayoutClicked() {
        binding.goalTitle.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        context?.let {
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }
    }

}