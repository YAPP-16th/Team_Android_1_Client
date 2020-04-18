package com.eroom.erooja.feature.signup.page.jobclass


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.eroom.data.localclass.*

import com.eroom.erooja.databinding.FragmentJobClassBinding
import com.eroom.erooja.feature.signup.kakao.KakaoSignUpActivity

/**
 * A simple [Fragment] subclass.
 */
class JobClassFragment : Fragment() {
    private lateinit var jobClassBinding: FragmentJobClassBinding
    val classCheck: ObservableField<Boolean> = ObservableField(false)

    val mDevelopClassInfo: MutableLiveData<DevelopSelected> = MutableLiveData()
    val mDesignClassInfo: MutableLiveData<DesignSelected> = MutableLiveData()

    private lateinit var mDevelopAdapter: DevelopClassAdapter
    private lateinit var mDesignAdapter: DesignClassAdapter

    companion object {
        @JvmStatic
        fun newInstance() =
            JobClassFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setUpDataBinding(inflater, container)
        initView()
        return jobClassBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        jobClassBinding = FragmentJobClassBinding.inflate(inflater, container, false)
        jobClassBinding.fragment = this
    }

    private fun initView() {

    }

    fun settingGroup(jobGroup: JobGroup?) = jobGroup?.let {
        when (it) {
            JobGroup.DEVELOP -> {
                val list = DevelopClass.getArray()
                mDevelopClassInfo.value = DevelopSelected(list, ArrayList<Boolean>().apply { repeat(list.size){add(false)} })
                mDevelopClassInfo.value?.let {info -> context?.let { ct -> mDevelopAdapter =
                    DevelopClassAdapter(
                        info,
                        ct,
                        itemDevClicked
                    )
                }}
                jobClassBinding.classRecycler.adapter = mDevelopAdapter
                jobClassBinding.classRecycler.layoutManager = GridLayoutManager(context, 2)
            }
            JobGroup.DESIGN -> {
                val list: ArrayList<DesignClass> = DesignClass.getArray()
                mDesignClassInfo.value = DesignSelected(list, ArrayList<Boolean>().apply { repeat(list.size){add(false)} })
                mDesignClassInfo.value?.let {info -> context?.let { ct -> mDesignAdapter =
                    DesignClassAdapter(
                        info,
                        ct,
                        itemDesignClicked
                    )
                }}
                jobClassBinding.classRecycler.adapter = mDesignAdapter
                jobClassBinding.classRecycler.layoutManager = GridLayoutManager(context, 2)
            }
        }
    }

    private val itemDevClicked = { position: Int ->
        mDevelopClassInfo.value?.let { it.isSelected[position] = !it.isSelected[position] }
        mDevelopAdapter.notifyDataSetChanged()
        checkInfo()
    }

    private val itemDesignClicked = { position: Int ->
        mDesignClassInfo.value?.let { it.isSelected[position] = !it.isSelected[position] }
        mDesignAdapter.notifyDataSetChanged()
        checkInfo()
    }

    private fun checkInfo() {
        var result = false
        mDevelopClassInfo.value?.let {
            it.isSelected.forEach { boolean ->
                result = result || boolean
            }
        }
        mDesignClassInfo.value?.let {
            it.isSelected.forEach { boolean ->
                result = result || boolean
            }
        }
        classCheck.set(result)
    }

    fun nextButtonClicked() {
        classCheck.get()?.let {
            if (it) (activity as KakaoSignUpActivity).requestUserInfo()
        }
    }

}
