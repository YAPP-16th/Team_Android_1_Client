package com.eroom.erooja.feature.addDirectList.addMyTodoListFrame

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.databinding.FragmentInactiveGoalDetailBinding


class InactiveGoalDetailFragment : Fragment() {
    private lateinit var binding: FragmentInactiveGoalDetailBinding

    companion object {
        @JvmStatic
        fun newInstance() = InactiveGoalDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setUpDataBinding(inflater, container)
        initView()
        return binding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentInactiveGoalDetailBinding.inflate(inflater, container, false)
        binding.fragment = this@InactiveGoalDetailFragment
    }

    private fun initView() {
        binding.goalDetailContent.text = arguments?.getString(Consts.DESCRIPTION)
    }


    fun onLayoutClicked() {
        binding.goalDetailContent.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        context?.let {
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }

    }
}

