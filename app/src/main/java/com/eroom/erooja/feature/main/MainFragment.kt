package com.eroom.erooja.feature.main


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.GoalContent
import com.eroom.data.entity.JobClass
import com.eroom.data.entity.MinimalGoalDetailContent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.toRealDateFormat

import com.eroom.erooja.databinding.FragmentMainBinding
import com.eroom.erooja.dialog.EroojaDialogActivity
import com.eroom.erooja.feature.goalDetail.GoalDetailActivity
import com.eroom.erooja.feature.ongoingGoal.OngoingGoalActivity
import com.eroom.erooja.feature.search.search_detail_page.SearchDetailActivity
import com.eroom.erooja.feature.tab.TabActivity
import com.eroom.erooja.singleton.UserInfo
import org.koin.android.ext.android.get

class MainFragment : Fragment(), MainContract.View {
    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var presenter: MainPresenter

    private lateinit var mNewGoalAdapter: NewGoalBrowseAdapter

    val nicknameText: ObservableField<String> = ObservableField()
    val randomJobText: ObservableField<String> = ObservableField()

    private var uId: String = ""

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = MainPresenter(this, get(), get(), get(), get(), get())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return mainBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mainBinding = FragmentMainBinding.inflate(inflater, container, false)
        mainBinding.fragment = this
    }

    private fun initView() {
        if (presenter.isGuest()) {
            nicknameText.set("게스트 님의 관심직무")
        } else {
            presenter.getUserInfo()
        }
    }

    override fun saveUid(uid: String) {
        uId = uid
        presenter.getMyParticipatedList(uId)
        presenter.getMemberJobInterest()
    }

    override fun onResume() {
        super.onResume()
        presenter.getMyParticipatedList(UserInfo.myUId)
    }

    override fun setNickname(nickname: String) = nicknameText.set("$nickname 님의 관심직무")

    @SuppressLint("SetTextI18n")
    override fun setJobInterestInfo(
        randomJob: String,
        randomJobId: Long,
        classList: ArrayList<JobClass>
    ) {
        randomJobText.set(randomJob)
        mainBinding.noGoal.text = randomJob + "직무와 관련된 목표가 아직 없네요.\n 새로운 목표를 추가해보세요"
        var contentString = ""
        for ((index, classItem) in classList.withIndex()) {
            contentString += if (index != classList.size - 1) classItem.name + ", "
            else classItem.name
        }
        mainBinding.userInterestInfoCount.setOnClickListener {
            startActivity(Intent(activity, EroojaDialogActivity::class.java).apply {
                putExtra(Consts.DIALOG_TITLE, "관심직무(${classList.size})")
                putExtra(Consts.DIALOG_CONTENT, contentString)
                putExtra(Consts.DIALOG_CONFIRM, true)
                putExtra(Consts.DIALOG_CANCEL, false)
            })
        }
        presenter.getInterestedGoals(randomJobId, uId)
    }

    override fun setParticipatedList(list: ArrayList<MinimalGoalDetailContent>) {
        mainBinding.participantFrame.removeAllViews()
        mainBinding.participantScroll.visibility = View.VISIBLE
        for ((index, it) in list.withIndex()) {
            val jobClassInfo = it.minimalGoalDetail.jobInterests.filter { it.jobInterestType != Consts.JOB_GROUP }.toList()
            val extraInfo = if (jobClassInfo.size - 1 == 0) "" else " 외 ${jobClassInfo.size - 1}"
            mainBinding.participantFrame.addView(
                ParticipatedItem(
                    context,
                    it.goalId,
                    isOrg = index % 2 == 0,
                    percent = "${(it.checkedTodoRate * 100).toInt()}%",
                    jobClasses = "${jobClassInfo[0].name}$extraInfo",
                    titleText = it.minimalGoalDetail.title,
                    duration = "${it.startDt.toRealDateFormat()}~${it.endDt.toRealDateFormat()}",
                    isClicked = myGoalClicked
                )
            )
        }
        if (list.size == 1) {
            mainBinding.participantFrame.addView(AddGoalItem(context, addGoalClicked))
        } else if (list.size == 0) {
            mainBinding.participantScroll.visibility = View.GONE
        }
    }

    override fun setNewGoalBrowse(content: ArrayList<GoalContent>) {
        if (content.size > 0) mainBinding.newGoalAddFrame.visibility = View.GONE
        else mainBinding.newGoalAddFrame.visibility = View.VISIBLE
        mNewGoalAdapter = NewGoalBrowseAdapter(content, randomJobText.get() ?: "", newGoalClicked, requireContext())
        mainBinding.newGoalRecycler.apply {
            adapter = mNewGoalAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private val myGoalClicked = { goalId: Long ->
        startActivity(Intent(activity, OngoingGoalActivity::class.java).apply {
            putExtra(Consts.GOAL_ID, goalId)
            putExtra(Consts.UID, uId)
            putExtra(Consts.IS_FROM_MYPAGE_ONGOING_GOAL, true)
        })
    }

    private val addGoalClicked = { (activity as TabActivity).navigateToNewGoal(uId) }

    private val newGoalClicked = { goalId: Long ->
        startActivity(Intent(activity, GoalDetailActivity::class.java).apply {
            putExtra(Consts.GOAL_ID, goalId)
        })
    }

    fun navigateToSearchTab() = (activity as TabActivity).changeTabToSearch()

    fun navigateToMyPageTab() = (activity as TabActivity).changeTabToMyPage()

    fun navigateToAddGoal() = (activity as TabActivity).navigateToNewGoal(uId)

    fun navigateToSearchActivity() =
        startActivity(Intent(activity, SearchDetailActivity::class.java))

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}
