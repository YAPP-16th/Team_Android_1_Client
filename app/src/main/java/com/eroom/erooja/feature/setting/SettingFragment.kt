package com.eroom.erooja.feature.setting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.JobClass
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentSettingBinding
import com.eroom.erooja.feature.filter.FilterActivity
import com.eroom.erooja.feature.search.search_main.SearchFragment
import com.eroom.erooja.feature.setting.setting_detail.*
import com.eroom.erooja.feature.setting.setting_detail.setting_help.HelpActivity
import com.eroom.erooja.feature.tab.TabActivity
import org.koin.android.ext.android.get

class SettingFragment :Fragment(), SettingContract.View{
    private lateinit var settingBinding: FragmentSettingBinding
    private lateinit var presenter : SettingPresenter

    private lateinit var settingList : Array<String>
    private lateinit var activitys : ArrayList<Activity>

    private var selectedGroupClassesNum = ArrayList<Long>()


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
        presenter = SettingPresenter(this, get())
        presenter.getSettingList(settingList)
        presenter.getAlignedJobInterest()

    }

    private fun initActivity(){
        activitys = ArrayList()
        activitys.apply{
            addAll(listOf(
                AlarmActivity(),
                ProfileActivity(),
                FilterActivity(),
                HelpActivity(),
                OpensourceActivity(),
                TOSActivity()
            ))
        }
    }

    override fun setUserJobInterest(interest: MutableSet<JobClass>) {
        interest.map{
            selectedGroupClassesNum.add(it.id)
        }
    }

    override fun setList(list: Array<String>) {
        settingBinding.settingRecycler.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = SettingAdapter(context, list, click)
        }
    }

    private var click = { position: Int ->
        if(position == 2){
            openSearchFilter()
        }
        else{
            startActivity(Intent(context, activitys[position]::class.java))
        }
    }

    fun back(v: View){
        (activity as TabActivity).replaceFragment(2)

    }

    fun openSearchFilter() {
        val intent = Intent(activity, FilterActivity::class.java)
        var number = ArrayList<Long>()

        repeat(selectedGroupClassesNum.size){
            number.add(selectedGroupClassesNum[it])
        }

        intent.putExtra("search",number)
        startActivityForResult(intent, 1010)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1010 && resultCode == 1000){

            val result1 = data?.getSerializableExtra("selectedId") as ArrayList<Long>
            val result2 = data?.getSerializableExtra("HashMap") as HashMap<Long, String>


        }
    }
}