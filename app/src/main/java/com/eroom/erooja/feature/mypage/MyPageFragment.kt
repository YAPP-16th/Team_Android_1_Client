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
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.JobClass
import com.eroom.data.entity.MinimalGoalDetailContent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.join
import org.koin.android.ext.android.get

import com.eroom.erooja.databinding.FragmentMyPageBinding
import com.eroom.erooja.feature.ongoingGoal.OngoingGoalActivity
import com.eroom.erooja.feature.tab.TabActivity
import timber.log.Timber


class MyPageFragment : Fragment(), MyPageContract.View {

    private lateinit var myPageBinding: FragmentMyPageBinding
    private lateinit var presenter: MyPagePresenter
    lateinit var mAdapter: MyPageParticipatedGoalAdapter
    private lateinit var mClassList: ArrayList<JobClass>

    private var mPage = 0
    private var mContentSize = 0
    private var isEnd = false

    var nList: ArrayList<MinimalGoalDetailContent> = ArrayList()

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
        presenter.getMyParticipatedList(uId, mPage)
    }

    override fun onResume() {
        super.onResume()
        mPage = 0 //mPage 0으로 해야돼?
            mContentSize = 0
        if (uId != "") presenter.getMyParticipatedList(uId, mPage)
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
        //myPageBinding.myParticipatedOngoingRecyclerview.removeAllViews()
        mContentSize += list.size
        if (mContentSize != 0) {
            if (mPage < 1) {
                Timber.e(list.map { it.minimalGoalDetail.title }.join())
                mAdapter = MyPageParticipatedGoalAdapter(
                    list,
                    myGoalClicked,
                    presenter.getMinimalGoalDetailContentCallback()
                )
                myPageBinding.myParticipatedOngoingRecyclerview.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = mAdapter
                }
            } else {
//                var a = list[0].minimalGoalDetail.title
//                var b = list[1].minimalGoalDetail.title
                var check = mContentSize
                var s = ""
                mAdapter.submitList(list.toMutableList())
                var a = nList
                mAdapter.notifyDataSetChanged() //있어야되나?
              //  mAdapter.notifyItemRangeChanged(0, mContentSize  )

            }
        }
//        myPageBinding.myParticipatedOngoingRecyclerview.adapter = MyPageParticipatedGoalAdapter(list, myGoalClicked)
//        myPageBinding.myPageTabLayout.getTabAt(0)?.text = "참여중(${list.size})"
       mPage++
    }

    override fun setIsEnd(isEnd: Boolean) {
        this.isEnd = isEnd
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

    val recyclerViewScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if(dy >= 0 && mContentSize > 0) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() >= mContentSize - 1  && !isEnd) {
                    var a = mPage
                    presenter.getMyParticipatedList(uId, mPage)
                    //mContentSize 0으로?
                }
            }
        }
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }

}
