package com.eroom.erooja.feature.signup.page.jobgroup


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.eroom.data.localclass.JobGroup

import com.eroom.erooja.databinding.FragmentJobGroupBinding
import com.eroom.erooja.feature.signup.kakao.KakaoSignUpActivity

/**
 * A simple [Fragment] subclass.
 */
class JobGroupFragment : Fragment() {
    private lateinit var jobGroupBinding: FragmentJobGroupBinding

    val groupCheck: ObservableField<Boolean> = ObservableField(false)
    val isDevelop: ObservableField<Boolean> = ObservableField(false)
    val isDesign: ObservableField<Boolean> = ObservableField(false)
    val jobGroup: MutableLiveData<JobGroup> = MutableLiveData()

    companion object {
        @JvmStatic
        fun newInstance() =
            JobGroupFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setUpDataBinding(inflater, container)
        initView()
        return jobGroupBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        jobGroupBinding = FragmentJobGroupBinding.inflate(inflater, container, false)
        jobGroupBinding.fragment = this
    }

    private fun initView() {
        jobGroupBinding.selectedDevelopmentImage.setOnClickListener {
            isDevelop.set(true)
            isDesign.set(false)
            groupCheck.set(true)
            jobGroup.value = JobGroup.DEVELOP
        }
        jobGroupBinding.unselectedDevelopmentImage.setOnClickListener {
            isDevelop.set(true)
            isDesign.set(false)
            groupCheck.set(true)
            jobGroup.value = JobGroup.DEVELOP
        }
        jobGroupBinding.selectedDesignImage.setOnClickListener {
            isDesign.set(true)
            isDevelop.set(false)
            groupCheck.set(true)
            jobGroup.value = JobGroup.DESIGN
        }
        jobGroupBinding.unselectedDesignImage.setOnClickListener {
            isDesign.set(true)
            isDevelop.set(false)
            groupCheck.set(true)
            jobGroup.value = JobGroup.DESIGN
        }
    }

    fun nextButtonClicked() {
        groupCheck.get()?.let {
            if (it) (activity as KakaoSignUpActivity).nextButtonClicked()
        }
    }

}
