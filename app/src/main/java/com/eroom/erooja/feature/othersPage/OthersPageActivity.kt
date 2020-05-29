package com.eroom.erooja.feature.othersPage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.JobClass
import com.eroom.data.entity.MinimalGoalDetailContent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.loadUrl
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityOthersPageBinding
import com.eroom.erooja.databinding.FragmentMyPageBinding
import com.eroom.erooja.feature.endedGoal.EndedGoalActivity
import com.eroom.erooja.feature.mypage.*
import com.eroom.erooja.feature.ongoingGoal.OngoingGoalActivity
import com.eroom.erooja.feature.othersEndedGoal.OthersEndedGoalActivity
import com.eroom.erooja.feature.othersOngoingGoal.OthersOngoingGoalActivity
import com.eroom.erooja.feature.tab.TabActivity
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.get

class OthersPageActivity : AppCompatActivity(), OthersPageContract.View {

    private lateinit var othersPageBinding: ActivityOthersPageBinding
    private lateinit var presenter: OthersPagePresenter
    //private lateinit var mClassList: ArrayList<JobClass>
    private lateinit var mClassList: ArrayList<String>

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
    }

    private fun initPresenter() {
        presenter = OthersPagePresenter(this, get())
    }

    private fun setUpDataBinding() {
        othersPageBinding = DataBindingUtil.setContentView(this, R.layout.activity_others_page)
        othersPageBinding.activity = this
    }

    private fun initView() {
        //presenter.getMemberJobInterest() //추후 삭제

        val intent = intent
        saveUid(intent.getStringExtra(Consts.UID) ?: "")
        setNickname(intent.getStringExtra(Consts.NICKNAME) ?: "anonymous")
        othersPageBinding.profileImage.loadUrl(intent.getStringExtra(Consts.IMAGE_PATH))

        mClassList = intent.getStringArrayListExtra(Consts.JOB_INTEREST) ?: ArrayList()
        setJobInterestInfo(mClassList)
        //val othersJobInterestedList: ArrayList<JobClass>//타계정의 관심직무&직군

        othersPageBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        if(isOnGoingGoalListEmpty) {
                            othersPageBinding.thereAreNoOngoingGoals.visibility = View.VISIBLE
                        }
                        othersPageBinding.thereAreNoEndedGoals.visibility = View.GONE
                        othersPageBinding.myParticipatedOngoingRecyclerview.visibility = View.VISIBLE
                        othersPageBinding.myParticipatedEndedRecyclerview.visibility = View.GONE

                    }
                    1 -> {
                        if(isEndedGoalListEmpty) {
                            othersPageBinding.thereAreNoEndedGoals.visibility = View.VISIBLE
                        }
                        othersPageBinding.thereAreNoOngoingGoals.visibility = View.GONE
                        othersPageBinding.myParticipatedOngoingRecyclerview.visibility = View.GONE
                        othersPageBinding.myParticipatedEndedRecyclerview.visibility = View.VISIBLE
                    }
                }
            }

        })
    }

    override fun saveUid(uid: String) {
        uId = uid
    }

    override fun onResume() {
        super.onResume()

        othersPageBinding.myParticipatedOngoingRecyclerview.adapter = null
        othersPageBinding.myParticipatedEndedRecyclerview.adapter = null

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

/*    override fun setJobInterestInfo(classList: ArrayList<JobClass>) {
        mClassList = classList
        if (classList.size <= 4) {
            othersPageBinding.jobClassRecycler.adapter =
                MyPageJobClassAdapter(classList, expandButtonClicked, false)
        } else {
            val classListLimitedFour = ArrayList<JobClass>()
            for ((index, jobClass) in classList.withIndex()) {
                if (index >= 4)
                    break
                classListLimitedFour.add(jobClass)
            }
            othersPageBinding.jobClassRecycler.adapter =
                MyPageJobClassAdapter(classListLimitedFour, expandButtonClicked, true)
        }
    }*/

    override fun setJobInterestInfo(jobInterestList: ArrayList<String>) {
        if(jobInterestList.size <= 4) {
            othersPageBinding.jobClassRecycler.adapter =
                OthersPageJobClassAdapter(jobInterestList, this, isMoreInfo = false, isExpaned = false)
        } else {
            val classListLimitedFour = ArrayList<String>()
            for((index, jobClass) in jobInterestList.withIndex()) {
                if(index >= 4)
                    break
                classListLimitedFour.add(jobClass)
            }
            othersPageBinding.jobClassRecycler.adapter =
                OthersPageJobClassAdapter(classListLimitedFour, this, isMoreInfo = true, isExpaned = false)
        }
    }

    override fun setOngoingGoalListSizeOnTabLayout(totalElements: Int) {
        if(totalElements == 0) {
            isOnGoingGoalListEmpty = true
            othersPageBinding.thereAreNoOngoingGoals.visibility = View.VISIBLE
        }
        else {
            isOnGoingGoalListEmpty = false
            othersPageBinding.thereAreNoOngoingGoals.visibility = View.GONE
        }
        othersPageBinding.tabLayout.getTabAt(0)?.text = "참여중(${totalElements})"
    }

    override fun setEndedGoalListSizeOnTabLayout(totalElements: Int) {
        if(totalElements == 0) {
            isEndedGoalListEmpty = true
        }
        else {
            isEndedGoalListEmpty = false
            othersPageBinding.thereAreNoEndedGoals.visibility = View.GONE
        }
        othersPageBinding.tabLayout.getTabAt(1)?.text = "종료(${totalElements})"
    }

    override fun setOngoingGoalList(list: ArrayList<MinimalGoalDetailContent>) {
        ongoingGoalContentSize += list.size
        if (ongoingGoalContentSize != 0) {
            if (ongoingGoalPage < 1) {
                //Timber.e(list.map { it.minimalGoalDetail.title }.join())
                ongoingGoalAdapter = MyPageOngoingGoalAdapter(list, ongoingGoalClicked)
                othersPageBinding.myParticipatedOngoingRecyclerview.apply {
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
                othersPageBinding.myParticipatedEndedRecyclerview.apply {
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
        startActivity(Intent(this, OthersOngoingGoalActivity::class.java).apply {
            putExtra(Consts.GOAL_ID, goalId)
            putExtra(Consts.UID, uId)
        })
    }

    private val endedGoalClicked = { goalId: Long ->
        startActivity(Intent(this, OthersEndedGoalActivity::class.java).apply {
            putExtra(Consts.GOAL_ID, goalId)
            putExtra(Consts.UID, uId)
        })
    }

    val expandButtonClicked =  { isExpanded: Boolean ->
        if (isExpanded) {
            val classListLimitedFour = ArrayList<String>()
            for((index, jobClass) in mClassList.withIndex()) {
                if(index >=4)
                    break
                classListLimitedFour.add(jobClass)
            }
            othersPageBinding.jobClassRecycler.adapter = OthersPageJobClassAdapter(classListLimitedFour, this, isMoreInfo = true, isExpaned = false)
        }
        else
            othersPageBinding.jobClassRecycler.adapter = OthersPageJobClassAdapter(mClassList, this, isMoreInfo = true, isExpaned = true)
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

    fun backClick() {
        finish()
    }

    override fun onBackPressed() {
        backClick()
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }

    fun startBlockAnimation() {
        othersPageBinding.colorLoading.visibility = View.GONE
        othersPageBinding.blockView.visibility = View.VISIBLE
        othersPageBinding.whiteLoading.visibility = View.VISIBLE
        othersPageBinding.colorLoading.cancelAnimation()
        othersPageBinding.whiteLoading.playAnimation()
    }

    override fun startAnimation() {
        othersPageBinding.blockView.visibility = View.GONE
        othersPageBinding.whiteLoading.visibility = View.GONE
        othersPageBinding.colorLoading.visibility = View.VISIBLE
        othersPageBinding.whiteLoading.cancelAnimation()
        othersPageBinding.colorLoading.playAnimation()
    }

    override fun stopAnimation() {
        othersPageBinding.blockView.visibility = View.GONE
        othersPageBinding.whiteLoading.visibility = View.GONE
        othersPageBinding.colorLoading.visibility = View.GONE
        othersPageBinding.whiteLoading.cancelAnimation()
        othersPageBinding.colorLoading.cancelAnimation()
    }
}
