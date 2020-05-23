package com.eroom.erooja.feature.othersEndedGoal

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
import com.eroom.domain.customview.bottomsheet.BottomSheetFragment
import com.eroom.domain.customview.bottomsheet.BottomSheetInfo
import com.eroom.domain.customview.bottomsheetAlert.BottomSheetAlertFragment
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.*
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityOthersEndedGoalBinding
import com.eroom.erooja.feature.addDirectList.addMyTodoListPage.AddMyListActivity
import com.eroom.erooja.feature.endedGoal.EndedGoalAdapter
import com.eroom.erooja.feature.goalDetail.GoalDetailActivity
import com.eroom.erooja.feature.ongoingGoal.OngoingGoalActivity
import com.eroom.erooja.singleton.UserInfo
import kotlinx.android.synthetic.main.include_ongoing_goal_desc.view.*
import org.koin.android.ext.android.get
import ru.rhanza.constraintexpandablelayout.State

class OthersEndedGoalActivity : AppCompatActivity(), OthersEndedGoalContract.View {
    lateinit var binding: ActivityOthersEndedGoalBinding
    lateinit var presenter: OthersEndedGoalPresenter

    lateinit var bottom: BottomSheetFragment
    lateinit var bottomAlert: BottomSheetAlertFragment

    lateinit var uId: String

    private var goalId: Long = -1
    private var isFromMyPage: Boolean = false
    private var isAbandoned: Boolean = false
    private var isDateFixed: Boolean = false

    private var isExistedInMyPage: Boolean = false
    private var isBeforeEndDt: Boolean = false
    private var isMyOngoingGoal: Boolean = false

    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_others_ended_goal)
        binding.activity = this@OthersEndedGoalActivity
    }

    @SuppressLint("SetTextI18n")
    override fun setGoalData(goalData: GoalDetailResponse) {
        binding.goalNameTxt.text = goalData.title
        binding.include.text.text = goalData.description

        binding.goalDescLayout.goal_desc.apply {
            showButton = false
            showShadow = false
        }

        binding.goalDescLayout.keyword_txt.text = goalData.jobInterests.mapIndexed { index: Int, goalType: GoalType ->
            if (index == goalData.jobInterests.size - 1) goalType.name else goalType.name add ", "
        }.toList().join()

        setBottomSheetAlert()
        initBottomSheet()
    }

    @SuppressLint("SetTextI18n")
    override fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>) {
        binding.mygoalRecyclerview.apply{
            layoutManager = LinearLayoutManager(this@OthersEndedGoalActivity)
            adapter = OthersEndedGoalAdapter(todoList)
        }
        var count = 0
        todoList.forEach { if (it.isEnd) count += 1 }
        binding.participantListText.text = "종료(${(count.toDouble() / todoList.size * 100).toInt()}%)"
    }

    fun initView() {
        presenter = OthersEndedGoalPresenter(this, get(), get(), get(), get())

        statusBarColor(this@OthersEndedGoalActivity, R.color.grey1)

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
        presenter.getMyGoalInfoByGoalId(goalId)
        presenter.getOthersGoalInfoByUIdAndGoalId(goalId,uId)
    }

    @SuppressLint("SetTextI18n")
    override fun settingDate(startDt: String, endDt: String) {
        binding.goalDateTxt.text = "${startDt.toRealDateFormat()}~${endDt.toRealDateFormat()}"
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
                        BottomSheetInfo("기간이 종료되어\n참여할 수 없는 목표입니다.", BottomSheetColor.DEFAULT)
                    ))
            }
        }
    }

    private fun initBottomSheet() {
        bottom = BottomSheetFragment.newInstance().apply {
            arguments = Bundle().apply {
                if (isMyOngoingGoal) {
                    putParcelableArrayList(
                        Consts.BOTTOM_SHEET_KEY, arrayListOf(
                            BottomSheetInfo(
                                "현재 참여중인 목표입니다",
                                BottomSheetColor.ORG_DEFAULT,
                                boldInfo = true
                            ),
                            BottomSheetInfo("다른 참여자 리스트 둘러보기", BottomSheetColor.DEFAULT)
                        )
                    )
                } else {
                    putParcelableArrayList(
                        Consts.BOTTOM_SHEET_KEY, arrayListOf(
                            BottomSheetInfo(
                                "이 리스트에 참여하기",
                                BottomSheetColor.ORG_DEFAULT,
                                boldInfo = true
                            ),
                            BottomSheetInfo("다른 참여자 리스트 둘러보기", BottomSheetColor.DEFAULT)
                        )
                    )
                }
            }
        }
        bottom.callback.observe(this, Observer {
            if (isExistedInMyPage && isBeforeEndDt && !isAbandoned) {
                when (it) {
                    0 -> { //현재 참여중인 목표입니다
                        startActivity(
                            Intent(
                                this@OthersEndedGoalActivity,
                                OngoingGoalActivity::class.java
                            ).apply {
                                putExtra(Consts.GOAL_ID, goalId)
                                putExtra(Consts.UID, UserInfo.myUId) //myUID를 보내야함.//
                            })
                    }
                    1 -> { // 다른 참여자 리스트 둘러보기
                        startActivity(
                            Intent(
                                this@OthersEndedGoalActivity,
                                GoalDetailActivity::class.java
                            ).apply {
                                putExtra(Consts.GOAL_ID, goalId)
                                putExtra(Consts.UID, uId)
                            })
                    }
                    else -> {}
                }
            } else {
                //if(isExistedInMyPage)////////이전에 1번이라도 참여이력이 있다면
                when (it) {
                    0 -> { //이 리스트에 참여하기
                        startActivity(
                            Intent(
                                this@OthersEndedGoalActivity,
                                AddMyListActivity::class.java
                            ).apply {
                                putExtra(Consts.GOAL_ID, goalId)
                                putExtra(Consts.UID, uId) //myUId(x) - owner의 uid
                                putExtra(Consts.GOAL_DETAIL_REQUEST_verOTHER, true)
                                putExtra(Consts.DATE, binding.goalDateTxt.text.toString())
                                putExtra(Consts.GOAL_TITLE, binding.goalNameTxt.text.toString())
                                // putExtra(Consts.DESCRIPTION, "DUMMY")
                                putExtra("Description", "DUMMY")
                            }
                        )
                    }
                    1 -> { // 다른 참여자 리스트 둘러보기
                        startActivity(
                            Intent(
                                this@OthersEndedGoalActivity,
                                GoalDetailActivity::class.java
                            ).apply {
                                putExtra(Consts.GOAL_ID, goalId)
                                putExtra(Consts.UID, uId)
                            })
                    }
                    else -> {}

                }

            }
            bottom.dismiss()
        })
    }

    override fun setIsAbandoned(isAbandoned: Boolean) {
        this.isAbandoned = isAbandoned
    }

    override fun setIsExistedInMyPage(isExisted: Boolean) {
        this.isExistedInMyPage = isExisted
    }

    override fun setIsDateFixed(isDateFixed: Boolean) {
        this.isDateFixed = isDateFixed
    }

    override fun setIsBeforeEndDt(isBeforeEndDt: Boolean) {
        this.isBeforeEndDt = isBeforeEndDt
    }

    override fun setIsMyOngoingGoal(isMyOngoingGoal: Boolean) {
        this.isMyOngoingGoal = isMyOngoingGoal
    }

    fun additionalOptionClicked() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()

        if(isDateFixed && !isBeforeEndDt) {
            bottomAlert.show(supportFragmentManager, bottom.tag)
        } else {
            bottom.show(supportFragmentManager, bottom.tag)
        }
//
//        if(!isDateFixed || (isDateFixed && isAbandoned)) {
//            bottom.show(supportFragmentManager, bottom.tag)
//        } else {
//            bottomAlert.show(supportFragmentManager, bottom.tag)
//        }
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
