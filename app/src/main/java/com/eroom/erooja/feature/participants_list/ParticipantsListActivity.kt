package com.eroom.erooja.feature.participants_list

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.Member
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityParticipantsListBinding
import com.eroom.erooja.feature.ongoingGoal.OngoingGoalActivity
import com.eroom.erooja.feature.othersPage.OthersPageActivity
import org.koin.android.ext.android.get

class ParticipantsListActivity : AppCompatActivity(), ParticipantsListContract.View {
    private lateinit var particiBinding: ActivityParticipantsListBinding
    private lateinit var presenter: ParticipantsListPresenter

    private lateinit var uId: String
    private lateinit var mAdapter: ParticipantListAdapter
    private var goalId: Long = -1
    private var mPage = 0
    private var isEnd = false
    private var mContentSize = 0
    private var mContentList: ArrayList<Member> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
    }

    fun initPresenter() {
        presenter = ParticipantsListPresenter(this, get())
    }

    fun setUpDataBinding() {
        particiBinding = DataBindingUtil.setContentView(this, R.layout.activity_participants_list)
        particiBinding.activity = this
    }

    fun initView() {
        val intent = intent
        goalId = intent.getLongExtra(Consts.GOAL_ID, -1)
        presenter.getParticipantedList(goalId, mPage)
        uId = intent.getStringExtra(Consts.UID) ?: ""
    }

    @SuppressLint("SetTextI18n")
    override fun updateList(list: ArrayList<Member>, totalElement: Int) {
        mContentList.addAll(list)
        if (mPage == 0) {
            mAdapter = ParticipantListAdapter(presenter.mParticipantDiffCallback, mContentList, uId, memberItemClicked)
            particiBinding.profileRecycler.adapter = mAdapter
            particiBinding.profileRecycler.layoutManager = LinearLayoutManager(this)
        } else {
            mAdapter.submitList(mContentList)
            mAdapter.notifyDataSetChanged()
        }
        particiBinding.participantCount.text = "총 ${totalElement}명"
        mContentSize += list.size
        mPage += 1
    }

    val recyclerViewScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy >= 0 && mContentSize > 0) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == mContentSize - 1 && !isEnd) {
                    presenter.getParticipantedList(goalId, mPage)
                }
            }
        }
    }

    fun backButtonClicked() = finish()

    override fun updateIsEnd(boolean: Boolean) {
        isEnd = boolean
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }

    private val memberItemClicked = { member: Member ->
        startActivity(Intent(this@ParticipantsListActivity, OthersPageActivity::class.java).apply {
            val jobInterestList: ArrayList<String> = ArrayList()
            putExtra(Consts.UID, member.uid)
            putExtra(Consts.NICKNAME, member.nickname)
            putExtra(Consts.IMAGE_PATH, member.imagePath)
            member.jobInterests.map {
                jobInterestList.add(it.name)
            }
            putStringArrayListExtra(Consts.JOB_INTEREST, jobInterestList)
            //putParcelableArrayListExtra(Consts.JOB, member.jobInterests)
        } )
    }

//    putParcelableArrayList(Consts.BOTTOM_SHEET_KEY, arrayListOf(
//    BottomSheetInfo("다른 참여자 리스트 둘러보기", BottomSheetColor.DEFAULT),
//    BottomSheetInfo("참여자 목록($count)", BottomSheetColor.DEFAULT),
//    BottomSheetInfo("리스트 수정하기", BottomSheetColor.DEFAULT),
//    BottomSheetInfo("목표 그만두기", BottomSheetColor.RED)
//    ))

//    data class Member(
//        @JsonProperty("uid") var uid: String,
//        @JsonProperty("nickname") var nickname: String,
//        @JsonProperty("imagePath") var imagePath: String?,
//        @JsonProperty("jobInterests") var jobInterests: ArrayList<JobInterest>
//    )

    fun startBlockAnimation() {
        particiBinding.colorLoading.visibility = View.GONE
        particiBinding.blockView.visibility = View.VISIBLE
        particiBinding.whiteLoading.visibility = View.VISIBLE
        particiBinding.colorLoading.cancelAnimation()
        particiBinding.whiteLoading.playAnimation()
    }

    override fun startAnimation() {
        particiBinding.blockView.visibility = View.GONE
        particiBinding.whiteLoading.visibility = View.GONE
        particiBinding.colorLoading.visibility = View.VISIBLE
        particiBinding.whiteLoading.cancelAnimation()
        particiBinding.colorLoading.playAnimation()
    }

    override fun stopAnimation() {
        particiBinding.blockView.visibility = View.GONE
        particiBinding.whiteLoading.visibility = View.GONE
        particiBinding.colorLoading.visibility = View.GONE
        particiBinding.whiteLoading.cancelAnimation()
        particiBinding.colorLoading.cancelAnimation()
    }
}
