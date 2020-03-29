package com.eroom.erooja.feature.exam.fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.databinding.FragmentExamBinding

import com.eroom.erooja.databinding.FragmentMainBinding

class ExamFragment : Fragment(),
    ExamFragmentContract.View {
    lateinit var mainBinding: FragmentExamBinding
    lateinit var presenter: ExamFragmentPresenter

    companion object {
        @JvmStatic
        fun newInstance() = ExamFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter =
            ExamFragmentPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return mainBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mainBinding = FragmentExamBinding.inflate(inflater, container, false)
        mainBinding.fragment = this
    }

    private fun initView() {
        arguments?.let { this.context?.toastShort(it.getString("key") ?: "") }
    }

}
