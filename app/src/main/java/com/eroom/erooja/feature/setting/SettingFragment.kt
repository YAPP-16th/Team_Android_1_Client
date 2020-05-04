package com.eroom.erooja.feature.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentSettingBinding
import com.eroom.erooja.feature.goalDetail.GoalDetailActivity
import com.eroom.erooja.feature.setting.setting_detail.*

class SettingFragment :Fragment(), SettingContract.View{
    private lateinit var settingBinding: FragmentSettingBinding
    private lateinit var presenter : SettingPresenter

    private lateinit var settingList : Array<String>
    private lateinit var activity : ArrayList<Activity>

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        initActivity()
        return settingBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        settingBinding = FragmentSettingBinding.inflate(inflater, container, false)
        settingBinding.setting = this
    }

    private fun initView(){
        settingList = resources.getStringArray(R.array.setting)
        presenter = SettingPresenter(this)
        presenter.getSettingList(settingList)

    }

    private fun initActivity(){
        activity = ArrayList()
        activity.apply{
            addAll(listOf(
                AlarmActivity(),
                ProfileActivity(),
                JobInterestActivity(),
                HelpActivity(),
                OpensourceActivity()
            ))
        }
    }

    override fun setList(list: Array<String>) {
        settingBinding.settingRecycler.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = SettingAdapter(context, list, click)
        }
    }

    private var click = { position: Int ->
        startActivity(Intent(context, activity[position]::class.java))
    }
}