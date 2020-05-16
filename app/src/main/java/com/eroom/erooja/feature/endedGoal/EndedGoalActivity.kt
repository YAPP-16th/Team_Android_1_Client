package com.eroom.erooja.feature.endedGoal

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.GoalType
import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.data.localclass.BottomSheetColor
import com.eroom.data.response.GoalDetailResponse
import com.eroom.domain.customview.bottomsheetAlert.BottomSheetAlertFragment
import com.eroom.domain.customview.bottomsheet.BottomSheetFragment
import com.eroom.domain.customview.bottomsheet.BottomSheetInfo
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.*
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityEndedGoalBinding
import com.eroom.erooja.feature.editgoal.EditGoalActivity
import com.eroom.erooja.feature.goalDetail.GoalDetailActivity
import com.eroom.erooja.feature.participants_list.ParticipantsListActivity
import kotlinx.android.synthetic.main.include_ongoing_goal_desc.view.*
import org.koin.android.ext.android.get
import ru.rhanza.constraintexpandablelayout.State

class EndedGoalActivity : AppCompatActivity(), EndedGoalContract.View {
    lateinit var binding: ActivityEndedGoalBinding
    lateinit var presenter: EndedGoalPresenter

    lateinit var bottom: BottomSheetFragment
    lateinit var bottomAlert: BottomSheetAlertFragment

    lateinit var uId: String

    private var goalId: Long = -1
    private var isFromMyPage: Boolean = false
    private var isAbandoned: Boolean = false
    private var isDateFixed: Boolean = false

    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ended_goal)
        binding.activity = this@EndedGoalActivity
    }

    @SuppressLint("SetTextI18n")
    override fun setGoalData(goalData: GoalDetailResponse) {
        binding.goalNameTxt.text = goalData.title
        binding.goalDateTxt.text = "${goalData.startDt.toRealDateFormat()}~${goalData.endDt.toRealDateFormat()}"
        binding.include.text.text = goalData.description

        binding.goalDescLayout.goal_desc.apply {
            showButton = false
            showShadow = false
        }

        binding.goalDescLayout.keyword_txt.text = goalData.jobInterests.mapIndexed { index: Int, goalType: GoalType ->
            if (index == goalData.jobInterests.size - 1) goalType.name else goalType.name add ", "
        }.toList().join()

        setBottomSheetAlert()
        initBottomSheet(goalData.joinCount)
    }

    @SuppressLint("SetTextI18n")
    override fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>) {
        binding.mygoalRecyclerview.apply{
            layoutManager = LinearLayoutManager(this@EndedGoalActivity)
            adapter = EndedGoalAdapter(todoList)
        }
        var count = 0
        todoList.forEach { if (it.isEnd) count += 1 }
        binding.participantListText.text = "종료(${(count.toDouble() / todoList.size * 100).toInt()}%)"
    }

    fun initView() {
        presenter = EndedGoalPresenter(this, get(), get(), get())

        statusBarColor(this@EndedGoalActivity, R.color.grey1)

        binding.goalDescLayout.goal_desc.apply {
            showButton = false
            showShadow = false
        }

        when (binding.goalDescLayout.goal_desc.state) {
            State.Expanded, State.Expanding -> binding.moreBtn.loadDrawable(resources.getDrawable(R.drawable.ic_icon_small_arrow_top_white, null))
            State.Collapsed, State.Collapsing -> binding.moreBtn.loadDrawable(resources.getDrawable(
                R.drawable.ic_icon_small_arrow_right_white, null))
            else -> {}
        }

        val intent = intent
        goalId = intent.getLongExtra(Consts.GOAL_ID, -1)
        presenter.getData(goalId)
        uId = intent.getStringExtra(Consts.UID) ?: ""
        isFromMyPage = intent.getBooleanExtra(Consts.IS_FROM_MYPAGE_ENDED_GOAL, false)

        presenter.getTodoData(uId, goalId)
        presenter.getGoalInfoByGoalId(goalId)
    }

    fun moreClick(v: View) {
        binding.goalDescLayout.goal_desc.toggle()
        when (binding.goalDescLayout.goal_desc.state) {
            State.Expanded, State.Expanding -> binding.moreBtn.loadDrawable(resources.getDrawable(R.drawable.ic_icon_small_arrow_top_white, null))
            State.Collapsed, State.Collapsing -> binding.moreBtn.loadDrawable(resources.getDrawable(
                R.drawable.ic_icon_small_arrow_right_white, null))
            else -> {}
        }
    }

    private fun setBottomSheetAlert() {
        bottomAlert = BottomSheetAlertFragment.newInstance().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(
                    Consts.BOTTOM_SHEET_KEY, arrayListOf(
                        BottomSheetInfo("기간이 종료되어\n참여할 수 없는 목표입니다.",BottomSheetColor.DEFAULT)
                    ))
            }
        }
    }

    private fun initBottomSheet(count: Int) {
        bottom = BottomSheetFragment.newInstance().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(
                    Consts.BOTTOM_SHEET_KEY, arrayListOf(
                        BottomSheetInfo("목표에 다시 참여하기", BottomSheetColor.ORG_DEFAULT, true),
                        BottomSheetInfo("다른 참여자 리스트 둘러보기", BottomSheetColor.DEFAULT, true)
                    ))
            }
        }
        bottom.callback.observe(this, Observer {
            when (it) {
                0 -> { // 목표에 다시 참여하기
                    if(!isDateFixed) {
                        this.toastShort("이 목표는 기간 설정 자유")
                        startActivity(
                            Intent(
                                this@EndedGoalActivity,
                                GoalDetailActivity::class.java
                            ).apply {
                                putExtra(Consts.GOAL_ID, goalId)
                                putExtra(Consts.UID, uId)
                                putExtra(Consts.IS_FROM_MYPAGE_ENDED_GOAL, isFromMyPage)
                            })
                    } else {
                        this.toastLong(" 이목표는 기간설정 못함니다")

                    }
                }
                1 -> { // 다른 참여자 리스트 둘러보기
                    startActivity(Intent(this@EndedGoalActivity, GoalDetailActivity::class.java).apply {
                        putExtra(Consts.GOAL_ID, goalId)
                        putExtra(Consts.UID, uId)
                        putExtra(Consts.IS_FROM_MYPAGE_ONGOING_GOAL, isFromMyPage)
                    })
                }
                else -> {}
            }
            bottom.dismiss()
        })
    }

    override fun setIsAbandoned(isAbandoned: Boolean) {
        this.isAbandoned = isAbandoned
    }

    override fun setIsDateFixed(isDateFixed: Boolean) {
        this.isDateFixed = isDateFixed
    }

    fun additionalOptionClicked() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()

        if(!isDateFixed || (isDateFixed && isAbandoned)) {
            bottom.show(supportFragmentManager, bottom.tag)
        } else {
            bottomAlert.show(supportFragmentManager, bottom.tag)
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
}
