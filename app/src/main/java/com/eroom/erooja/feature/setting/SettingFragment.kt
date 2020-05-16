package com.eroom.erooja.feature.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.JobClass
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.toastLong
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentSettingBinding
import com.eroom.erooja.feature.filter.FilterActivity
import com.eroom.erooja.feature.login.LoginActivity
import com.eroom.erooja.feature.setting.setting_detail.*
import com.eroom.erooja.feature.setting.setting_help.HelpActivity
import com.eroom.erooja.feature.setting.setting_nickname.NicknameChangeFragment
import com.eroom.erooja.feature.tab.TabActivity
import org.koin.android.ext.android.get

class SettingFragment :Fragment(), SettingContract.View{
    private lateinit var settingBinding: FragmentSettingBinding
    private lateinit var presenter : SettingPresenter
    private lateinit var settingList : Array<String>
    lateinit var bottomAlert: NicknameChangeFragment

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
        return settingBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        settingBinding = FragmentSettingBinding.inflate(inflater, container, false)
        settingBinding.setting = this
    }

    private fun initView(){
        settingList = resources.getStringArray(R.array.setting)
        presenter = SettingPresenter(this, get(), get(), get(), get())
        presenter.getSettingList(settingList)
        presenter.getAlignedJobInterest()
        setBottomSheetAlert()

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
        when(position){
            0-> startActivity(Intent(context, AlarmActivity::class.java))
            1-> bottomAlert.show(childFragmentManager, "test")
            2-> openSearchFilter()
            3-> startActivity(Intent(context, HelpActivity::class.java))
            4-> startActivity(Intent(context, OpensourceActivity::class.java))
            5-> startActivity(Intent(context, TOSActivity::class.java))
            6-> presenter.logout()
        }
    }

    private fun setBottomSheetAlert() {
        bottomAlert = NicknameChangeFragment.newInstance()
    }

    fun back(v: View){
        (activity as TabActivity).replaceFragment(2)

    }

    override fun logoutCompleted() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        requireContext().startActivity(intent)
    }

    fun openSearchFilter() {
        val intent = Intent(activity, FilterActivity::class.java)
        val number = ArrayList<Long>()

        repeat(selectedGroupClassesNum.size){
            number.add(selectedGroupClassesNum[it])
        }

        intent.putExtra(Consts.SEARCH,number)
        intent.putExtra(Consts.JOB_CLASS_CHANGE, resources.getString(R.string.job_class_change))
        intent.putExtra(Consts.SETTING_REQUEST, Consts.SETTING_REQUEST_NUM)
        startActivityForResult(intent, Consts.SETTING_REQUEST_NUM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Consts.SETTING_REQUEST_NUM && resultCode == 1000) {

            val result1 = data?.getSerializableExtra("selectedId") as ArrayList<Long>
            presenter.updateJobInterest(selectedGroupClassesNum, result1)
            selectedGroupClassesNum.clear()
            repeat(result1.size){
                selectedGroupClassesNum.add(result1[it])
            }
        }


    }

    fun dissBottomSheet(){
        bottomAlert.dismiss()
    }

    override fun InformUpdatedMsg() {
       context?.toastLong("직군 변경 완료")
    }
}