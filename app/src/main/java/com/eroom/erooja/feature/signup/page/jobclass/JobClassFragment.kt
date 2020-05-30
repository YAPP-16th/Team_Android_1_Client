package com.eroom.erooja.feature.signup.page.jobclass


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.eroom.data.entity.JobClass
import com.eroom.data.localclass.*

import com.eroom.erooja.databinding.FragmentJobClassBinding
import com.eroom.erooja.feature.signup.kakao.KakaoSignUpActivity
import org.koin.android.ext.android.get

class JobClassFragment : Fragment(), JobClassContract.View {
    private lateinit var jobClassBinding: FragmentJobClassBinding
    private lateinit var presenter: JobClassPresenter
    private lateinit var selectGroupClasses: ArrayList<JobClass>
    val classCheck: ObservableField<Boolean> = ObservableField(false)

    val mSelectedClassInfo: MutableLiveData<ArrayList<Long>> = MutableLiveData()
    val mGroupId: MutableLiveData<Long> = MutableLiveData()

    private lateinit var mAdapter: ClassAdapter

    companion object {
        @JvmStatic
        fun newInstance() =
            JobClassFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initPresenter()
        setUpDataBinding(inflater, container)
        return jobClassBinding.root
    }

    private fun initPresenter() {
        presenter = JobClassPresenter(this, get(), get())
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        jobClassBinding = FragmentJobClassBinding.inflate(inflater, container, false)
        jobClassBinding.fragment = this
    }

    fun settingSelectedId(selected: ArrayList<Long>) {
        mSelectedClassInfo.value = selected
    }

    fun settingGroup(jobGroup: JobGroup?) {
        jobGroup?.let {
            (activity as KakaoSignUpActivity).startBlockAnimation()
            presenter.getJobGroups(it)
        }
    }

    override fun settingGroupView(jobClasses: ArrayList<JobClass>) {
        selectGroupClasses = jobClasses
        context?.let {
            mSelectedClassInfo.value?.let { selectedList: ArrayList<Long> ->
                mAdapter = ClassAdapter(jobClasses, it, itemClicked, selectedList)
            }
        }
        jobClassBinding.classRecycler.adapter = mAdapter
        jobClassBinding.classRecycler.layoutManager = GridLayoutManager(context, 2)
    }

    private val itemClicked = { classId: Long, isSelected: Boolean ->
        if (isSelected)
            mSelectedClassInfo.value = mSelectedClassInfo.value.apply { this?.remove(classId) }
        else
            mSelectedClassInfo.value =
                (mSelectedClassInfo.value ?: ArrayList()).apply { add(classId) }
        mAdapter.notifyDataSetChanged()
        checkInfo()
    }

    private fun checkInfo() {
        var result = false
        mSelectedClassInfo.value?.let {
            for (selectInfo in it)
                for (classInfo in selectGroupClasses) {
                    if (selectInfo == classInfo.id) result = true
                }
        }
        classCheck.set(result)
    }

    fun nextButtonClicked() {
        classCheck.get()?.let {
            if (it) (activity as KakaoSignUpActivity).requestUserInfo()
        }
    }

    override fun settingGroupId(id: Long) {
        mGroupId.value = id
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }

    override fun stopAnimation() = (activity as KakaoSignUpActivity).stopAnimation()
}
