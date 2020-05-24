package com.eroom.erooja.feature.othersOngoingGoal

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
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.*
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityOthersOngoingGoalBinding
import com.eroom.erooja.dialog.EroojaDialogActivity
import com.eroom.erooja.feature.addDirectList.addMyTodoListPage.AddMyListActivity
import com.eroom.erooja.feature.goalDetail.GoalDetailActivity
import com.eroom.erooja.feature.joinOtherList.joinTodoListPage.JoinOtherListActivity
import com.eroom.erooja.feature.ongoingGoal.OngoingGoalActivity
import com.eroom.erooja.singleton.UserInfo
import kotlinx.android.synthetic.main.include_ongoing_goal_desc.view.*
import org.koin.android.ext.android.get
import ru.rhanza.constraintexpandablelayout.State


class OthersOngoingGoalActivity : AppCompatActivity(), OthersOngoingGoalContract.View {
    lateinit var binding: ActivityOthersOngoingGoalBinding
    lateinit var presenter: OthersOngoingGoalPresenter

    lateinit var bottom: BottomSheetFragment

    lateinit var uId: String

    private var goalId: Long = -1
    private var isMyOngoingGoal = false
    private var isExistedInMyPage = false
    private var isDateFixed = false

    private var mLastClickTime: Long = 0

    private lateinit var userTodoList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_others_ongoing_goal)
        binding.mygoal = this@OthersOngoingGoalActivity
    }

    override fun setIsMyOngoingGoal(isMyOngoing: Boolean) {
        this.isMyOngoingGoal = isMyOngoing
        initBottomSheet()
    }

    override fun setIsExistedInMyPage(isExistedInMyPage: Boolean) {
        this.isExistedInMyPage = isExistedInMyPage
    }

    override fun setIsDateFixed(isDateFixed: Boolean) {
        this.isDateFixed = isDateFixed
    }

    private fun setUserToDoList(todoList: ArrayList<MinimalTodoListDetail>) {
        userTodoList = ArrayList()
        repeat(todoList.size){
            userTodoList.add(todoList[it].content)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setGoalData(goalData: GoalDetailResponse) {
        binding.goalNameTxt.text = goalData.title
        binding.include.text.text = goalData.description

        binding.goalDescLayout.goal_desc.apply {
            showButton = false
            showShadow = false
        }

        binding.goalDescLayout.keyword_txt.text =
            goalData.jobInterests.mapIndexed { index: Int, goalType: GoalType ->
                if (index == goalData.jobInterests.size - 1) goalType.name else goalType.name add ", "
            }.toList().join()

        //initBottomSheet()
    }

    @SuppressLint("SetTextI18n")
    override fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>) {
        setUserToDoList(todoList)
        binding.mygoalRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@OthersOngoingGoalActivity)
            adapter = OthersOngoingGoalAdapter(todoList)
        }
        var count = 0
        todoList.forEach { if (it.isEnd) count += 1 }
        binding.participantListText.text =
            "${((count.toDouble() / todoList.size) * 100).toInt()}% 달성중"
    }

    fun initView() {
        presenter = OthersOngoingGoalPresenter(this, get(), get(), get(), get(), get())

        statusBarColor(this@OthersOngoingGoalActivity, R.color.orgDefault)

        binding.goalDescLayout.goal_desc.apply {
            showButton = false
            showShadow = false
        }

        when (binding.goalDescLayout.goal_desc.state) {
            State.Expanded, State.Expanding -> binding.moreBtn.loadDrawable(
                resources.getDrawable(
                    R.drawable.ic_icon_small_arrow_top_white,
                    null
                )
            )
            State.Collapsed, State.Collapsing -> binding.moreBtn.loadDrawable(
                resources.getDrawable(
                    R.drawable.ic_icon_small_arrow_right_white,
                    null
                )
            )
            else -> {
            }
        }

        val intent = intent
        goalId = intent.getLongExtra(Consts.GOAL_ID, -1)
        uId = intent.getStringExtra(Consts.UID) ?: ""
        presenter.getMyGoalInfoByGoalId(goalId)
        presenter.getOthersGoalInfoByUIdAndGoalId(goalId, uId)
        presenter.getData(goalId)
        presenter.getTodoData(uId, goalId)
    }

    override fun onResume() {
        super.onResume()
        reRequestTodoList()
    }

    @SuppressLint("SetTextI18n")
    override fun settingDate(startDt: String, endDt: String) {
        binding.goalDateTxt.text = "${startDt.toRealDateFormat()}~${endDt.toRealDateFormat()}"
    }

    override fun reRequestTodoList() {
        presenter.getTodoData(uId, goalId)
    }

    fun moreClick(v: View) {
        binding.goalDescLayout.goal_desc.toggle()
        when (binding.goalDescLayout.goal_desc.state) {
            State.Expanded, State.Expanding -> binding.moreBtn.loadDrawable(
                resources.getDrawable(
                    R.drawable.ic_icon_small_arrow_top_white,
                    null
                )
            )
            State.Collapsed, State.Collapsing -> binding.moreBtn.loadDrawable(
                resources.getDrawable(
                    R.drawable.ic_icon_small_arrow_right_white,
                    null
                )
            )
            else -> {
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
            if (isMyOngoingGoal) {
                when (it) {
                    0 -> { //현재 참여중인 목표입니다
                        startActivity(
                            Intent(
                                this@OthersOngoingGoalActivity,
                                OngoingGoalActivity::class.java
                            ).apply {
                                putExtra(Consts.GOAL_ID, goalId)
                                putExtra(Consts.UID, UserInfo.myUId) //myUID를 보내야함.//
                            })
                    }
                    1 -> { // 다른 참여자 리스트 둘러보기
                        startActivity(
                            Intent(
                                this@OthersOngoingGoalActivity,
                                GoalDetailActivity::class.java
                            ).apply {
                                putExtra(Consts.GOAL_ID, goalId)
                                putExtra(Consts.UID, uId)
                            })
                    }
                    else -> {}
                }
            } else {
                when (it) {
                    0 -> { //이 리스트에 참여하기
                        if(isExistedInMyPage) {
                            startActivityForResult(Intent(this, EroojaDialogActivity::class.java).apply {
                                putExtra(Consts.DIALOG_TITLE, "")
                                putExtra(Consts.DIALOG_CONTENT, "이미 목표에 참여한 이력이 존재합니다. 참여 시 해당 이력이 삭제될 수 있습니다.")
                                putExtra(Consts.DIALOG_CONFIRM, true)
                                putExtra(Consts.DIALOG_CANCEL, true)
                            }, Consts.MY_GOAL_REJOIN_REQUEST)
                        } else {
                            joinOtherList(uId)
                        }
                    }
                    1 -> { // 다른 참여자 리스트 둘러보기
                        startActivity(
                            Intent(
                                this@OthersOngoingGoalActivity,
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

    //Todo: 현재 액티비티에서 JoinOtherListActivity 로 넘어갈 때 필요한 요소: GoalID, UID, NAME, DATE, GoalTITLE, DESC)
    private fun joinOtherList(uid: String) {
        val intent = Intent(this@OthersOngoingGoalActivity, JoinOtherListActivity::class.java)
            .apply{
                putExtra(Consts.GOAL_ID, intent.getLongExtra(Consts.GOAL_ID, -1))
                putExtra(Consts.UID, uid)
                if(isDateFixed) {
                    putExtra(Consts.DATE, binding.goalDateTxt.text)
                } else {
                    putExtra(Consts.DATE, "기간 설정 자유")
                }
                putExtra(Consts.GOAL_TITLE, binding.goalNameTxt.text)
                putExtra(Consts.DESCRIPTION, binding.include.text.text)
                putExtra(Consts.USER_TODO_LIST, userTodoList)
            }
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Consts.MY_GOAL_REJOIN_REQUEST && resultCode == 6000) {
            data?.let {
                val result = it.getBooleanExtra(Consts.DIALOG_RESULT, false)
                if (result) {
                    joinOtherList(uId)
                }
            }
        }
    }

    fun additionalOptionClicked() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        bottom.show(supportFragmentManager, bottom.tag)
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
