package com.eroom.erooja.feature.mypage


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.JobClass
import com.eroom.data.entity.MinimalGoalDetailContent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.toRealDateFormat
import com.eroom.domain.utils.toastLong
import org.koin.android.ext.android.get

import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentMyPageBinding
import com.eroom.erooja.feature.main.AddGoalItem
import com.eroom.erooja.feature.main.ParticipatedItem
import com.eroom.erooja.feature.ongoingGoal.OngoingGoalActivity
import com.eroom.erooja.feature.setting.SettingFragment
import com.eroom.erooja.feature.tab.TabActivity
import kotlinx.android.synthetic.main.include_completed_goal_desc.view.*
import kotlinx.android.synthetic.main.item_main_new_goal.*
import org.koin.core.context.KoinContextHandler.get

/**
 * A simple [Fragment] subclass.
 */
class MyPageFragment : Fragment(), MyPageContract.View {

    private lateinit var myPageBinding: FragmentMyPageBinding
    private lateinit var presenter: MyPagePresenter

    private lateinit var mClassList: ArrayList<JobClass>

    val nicknameText: ObservableField<String> = ObservableField()

    private var uId: String = ""

    companion object {
        @JvmStatic
        fun newInstance() = MyPageFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = MyPagePresenter(this, get(), get(), get(), get())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return myPageBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        myPageBinding = FragmentMyPageBinding.inflate(inflater, container, false)
        myPageBinding.fragment = this
    }

    private fun initView() {
        if (presenter.isGuest()) {
            nicknameText.set("게스트 님")
        } else {
            presenter.getUserInfo()
            presenter.getMemberJobInterest()
        }

    }

    override fun saveUid(uid: String) {
        uId = uid
        presenter.getMyParticipatedList(uId)
    }

    override fun onResume() {
        super.onResume()
        presenter.getMyParticipatedList(uId)
    }

    override fun setNickname(nickname: String) {
        nicknameText.set("$nickname 님")
    }

    override fun setJobInterestInfo(classList: ArrayList<JobClass>) {
        mClassList = classList
        if(classList.size <= 4) {
            myPageBinding.jobClassRecycler.adapter = MyPageJobClassAdapter(classList)
        } else {
            val classListLimitedFour = ArrayList<JobClass>()
            for((index, jobClass) in classList.withIndex()) {
                if(index >=4)
                    break
                classListLimitedFour.add(jobClass)
            }
            myPageBinding.jobClassRecycler.adapter = MyPageJobClassAdapter(classListLimitedFour)
            myPageBinding.expandBtn.visibility = View.VISIBLE
        }
    }

    override fun setParticipatedList(list: ArrayList<MinimalGoalDetailContent>) {
        myPageBinding.myParticipatedOngoingRecyclerview.removeAllViews()
        myPageBinding.myParticipatedOngoingRecyclerview.adapter = MyPageParticipatedGoalAdapter(list, myGoalClicked)
        myPageBinding.myPageTabLayout.getTabAt(0)?.text = "참여중(${list.size})"
    }

    private val myGoalClicked = { goalId: Long ->
        startActivity(Intent(activity, OngoingGoalActivity::class.java).apply {
            putExtra(Consts.GOAL_ID, goalId)
        })
    }

    fun expandButtonClicked() {
        myPageBinding.jobClassRecycler.adapter = MyPageJobClassAdapter(mClassList)
        myPageBinding.expandBtn.visibility = View.GONE
    }

    fun settingClick(){
        (activity as TabActivity).replaceFragment(3)
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }

}
