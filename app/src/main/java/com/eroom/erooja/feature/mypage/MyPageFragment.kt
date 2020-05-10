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
import org.koin.android.ext.android.get

import com.eroom.erooja.databinding.FragmentMyPageBinding
import com.eroom.erooja.feature.endedGoal.EndedGoalActivity
import com.eroom.erooja.feature.ongoingGoal.OngoingGoalActivity
import com.eroom.erooja.feature.tab.TabActivity
import com.google.android.material.tabs.TabLayout


class MyPageFragment : Fragment(), MyPageContract.View {

    private lateinit var myPageBinding: FragmentMyPageBinding
    private lateinit var presenter: MyPagePresenter
    private lateinit var mClassList: ArrayList<JobClass>

    private lateinit var ongoingGoalAdapter: MyPageOngoingGoalAdapter
    private var ongoingGoalPage = 0
    private var ongoingGoalContentSize = 0
    private var ongoingGoalIsEnd = false

    private lateinit var endedGoalAdapter: MyPageEndedGoalAdapter
    private var endedGoalPage = 0
    private var endedGoalContentSize = 0
    private var endedGoalIsEnd = false
    
    val nicknameText: ObservableField<String> = ObservableField()
    private var uId: String = ""
    private var isOnGoingGoalListEmpty: Boolean = false
    private var isEndedGoalListEmpty: Boolean = false

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

        myPageBinding.myPageTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        if(isOnGoingGoalListEmpty) {
                            myPageBinding.thereAreNoOngoingGoals.visibility = View.VISIBLE
                        }
                        myPageBinding.thereAreNoEndedGoals.visibility = View.INVISIBLE
                        myPageBinding.myParticipatedOngoingRecyclerview.visibility = View.VISIBLE
                        myPageBinding.myParticipatedEndedRecyclerview.visibility = View.INVISIBLE

                    }
                    1 -> {
                        if(isEndedGoalListEmpty) {
                            myPageBinding.thereAreNoEndedGoals.visibility = View.VISIBLE
                        }
                        myPageBinding.thereAreNoOngoingGoals.visibility = View.INVISIBLE
                        myPageBinding.myParticipatedOngoingRecyclerview.visibility = View.INVISIBLE
                        myPageBinding.myParticipatedEndedRecyclerview.visibility = View.VISIBLE
                    }
                }
            }

        })

    }

    override fun saveUid(uid: String) {
        uId = uid
        presenter.getOngoingGoalList(uId, ongoingGoalPage)
        presenter.getEndedGoalList(uId, endedGoalPage)
    }

    override fun onResume() {
        super.onResume()
        ongoingGoalPage = 0
        ongoingGoalContentSize = 0
        ongoingGoalIsEnd = false
        endedGoalPage = 0
        endedGoalContentSize = 0
        endedGoalIsEnd = false

        if (uId != "") {
            presenter.getOngoingGoalList(uId, ongoingGoalPage)
            presenter.getEndedGoalList(uId, endedGoalPage)
        }
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

    override fun setOngoingGoalListSizeOnTabLayout(totalElements: Int) {
        if(totalElements == 0) {
            isOnGoingGoalListEmpty = true
            myPageBinding.thereAreNoOngoingGoals.visibility = View.VISIBLE
        }
        else {
            isOnGoingGoalListEmpty = false
            myPageBinding.thereAreNoOngoingGoals.visibility = View.GONE
        }
        myPageBinding.myPageTabLayout.getTabAt(0)?.text = "참여중(${totalElements})"
    }

    override fun setEndedGoalListSizeOnTabLayout(totalElements: Int) {
        if(totalElements == 0) {
            isEndedGoalListEmpty = true
        }
        else {
            isEndedGoalListEmpty = false
            myPageBinding.thereAreNoEndedGoals.visibility = View.GONE
        }
        myPageBinding.myPageTabLayout.getTabAt(1)?.text = "종료(${totalElements})"
    }

    override fun setOngoingGoalList(list: ArrayList<MinimalGoalDetailContent>) {
        ongoingGoalContentSize += list.size
        if (ongoingGoalContentSize != 0) {
            if (ongoingGoalPage < 1) {
               //Timber.e(list.map { it.minimalGoalDetail.title }.join())
                ongoingGoalAdapter = MyPageOngoingGoalAdapter(list, ongoingGoalClicked)
                myPageBinding.myParticipatedOngoingRecyclerview.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ongoingGoalAdapter
                }
            } else {
                ongoingGoalAdapter.submitList(list)
            }
        }
        ongoingGoalPage++
    }

    override fun setEndedGoalList(list: ArrayList<MinimalGoalDetailContent>) {
        endedGoalContentSize += list.size
        if(endedGoalContentSize != 0) {
            if(endedGoalPage < 1) {
                endedGoalAdapter = MyPageEndedGoalAdapter(list, endedGoalClicked)
                myPageBinding.myParticipatedEndedRecyclerview.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = endedGoalAdapter
                }
            } else {
                endedGoalAdapter.submitList(list)
            }
        }
        endedGoalPage++
    }

    override fun setOnGoingGoalPageIsEnd(isEnd: Boolean) {
        this.ongoingGoalIsEnd = isEnd
    }

    override fun setEndedGoalPageIsEnd(isEnd: Boolean) {
        this.endedGoalIsEnd = isEnd
    }

    private val ongoingGoalClicked = { goalId: Long ->
        startActivity(Intent(activity, OngoingGoalActivity::class.java).apply {
            putExtra(Consts.GOAL_ID, goalId)
            putExtra(Consts.UID, uId)
            putExtra(Consts.IS_FROM_MYPAGE_ONGOING_GOAL, true)
        })
    }

    private val endedGoalClicked = { goalId: Long ->
        startActivity(Intent(activity, EndedGoalActivity::class.java).apply {
            putExtra(Consts.GOAL_ID, goalId)
            putExtra(Consts.UID, uId)
            putExtra(Consts.IS_FROM_MYPAGE_ENDED_GOAL, true)
        })
    }

    fun expandButtonClicked() {
        myPageBinding.jobClassRecycler.adapter = MyPageJobClassAdapter(mClassList)
        myPageBinding.expandBtn.visibility = View.GONE
    }

    fun settingClick(){
        (activity as TabActivity).replaceFragment(3)
    }

    val ongoingRecyclerViewScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if(dy >= 0 && ongoingGoalContentSize > 0) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() >= ongoingGoalContentSize - 1  && !ongoingGoalIsEnd) {
                    presenter.getOngoingGoalList(uId, ongoingGoalPage)
                }
            }
        }
    }

    val endedRecyclerViewScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if(dy >= 0 && endedGoalContentSize > 0) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() >= endedGoalContentSize - 1  && !endedGoalIsEnd) {
                    presenter.getEndedGoalList(uId, endedGoalPage)
                }
            }
        }
    }

    fun navigateToAddGoal() = (activity as TabActivity).navigateToNewGoal(uId)

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }

}
