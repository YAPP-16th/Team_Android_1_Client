package com.eroom.erooja.feature.ongoingGoal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.GoalType
import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.data.localclass.BottomSheetColor
import com.eroom.data.request.GoalAbandonedRequest
import com.eroom.data.response.GoalDetailResponse
import com.eroom.domain.customview.bottomsheet.BottomSheetFragment
import com.eroom.domain.customview.bottomsheet.BottomSheetInfo
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.*
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalBinding
import com.eroom.erooja.dialog.EroojaDialogActivity
import com.eroom.erooja.feature.editgoal.EditGoalActivity
import com.eroom.erooja.feature.goalDetail.GoalDetailActivity
import com.eroom.erooja.feature.participants_list.ParticipantsListActivity
import kotlinx.android.synthetic.main.include_ongoing_goal_desc.view.*
import org.koin.android.ext.android.get
import ru.rhanza.constraintexpandablelayout.State

class OngoingGoalActivity: AppCompatActivity(), OngoingGoalContract.View {
    lateinit var binding: ActivityGoalBinding
    lateinit var presenter: OngoingGoalPresenter

    lateinit var bottom: BottomSheetFragment

    lateinit var uId: String

    private var goalId: Long = -1
    private var isFromMyPage: Boolean = false

    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goal)
        binding.mygoal = this@OngoingGoalActivity
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

        initBottomSheet(goalData.joinCount)
    }

    @SuppressLint("SetTextI18n")
    override fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>) {
        binding.mygoalRecyclerview.apply{
            layoutManager = LinearLayoutManager(this@OngoingGoalActivity)
            adapter = OngoingGoalAdapter(todoList, saveChange)
        }
        var count = 0
        todoList.forEach { if (it.isEnd) count += 1 }
        binding.participantListText.text = "${((count.toDouble() / todoList.size)*100).toInt()}% 달성중"
    }


    private val saveChange = { boolean: Boolean, todoId: Long ->
        presenter.setTodoEnd(todoId, boolean)
    }

    fun initView() {
        presenter = OngoingGoalPresenter(this, get(), get(), get(), get())

        statusBarColor(this@OngoingGoalActivity, R.color.orgDefault)

        binding.goalDescLayout.goal_desc.apply {
            showButton = false
            showShadow = false
        }

        when (binding.goalDescLayout.goal_desc.state) {
            State.Expanded, State.Expanding -> binding.moreBtn.loadDrawable(resources.getDrawable(R.drawable.ic_icon_small_arrow_top_white, null))
            State.Collapsed, State.Collapsing -> binding.moreBtn.loadDrawable(resources.getDrawable(R.drawable.ic_icon_small_arrow_right_white, null))
            else -> {}
        }

        val intent = intent
        goalId = intent.getLongExtra(Consts.GOAL_ID, -1)
        presenter.getData(goalId)
        uId = intent.getStringExtra(Consts.UID) ?: ""

        presenter.getTodoData(uId, goalId)

        isFromMyPage = intent.getBooleanExtra(Consts.IS_FROM_MYPAGE_ONGOING_GOAL, false)
    }

    override fun onResume() {
        super.onResume()
        reRequestTodoList()
    }

    override fun reRequestTodoList() {
        presenter.getTodoData(uId, goalId)
    }

    fun moreClick(v: View) {
        binding.goalDescLayout.goal_desc.toggle()
        when (binding.goalDescLayout.goal_desc.state) {
            State.Expanded, State.Expanding -> binding.moreBtn.loadDrawable(resources.getDrawable(R.drawable.ic_icon_small_arrow_top_white, null))
            State.Collapsed, State.Collapsing -> binding.moreBtn.loadDrawable(resources.getDrawable(R.drawable.ic_icon_small_arrow_right_white, null))
            else -> {}
        }
    }

    private fun initBottomSheet(count: Int) {
        bottom = BottomSheetFragment.newInstance().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(Consts.BOTTOM_SHEET_KEY, arrayListOf(
                    BottomSheetInfo("다른 참여자 리스트 둘러보기", BottomSheetColor.DEFAULT),
                    BottomSheetInfo("참여자 목록($count)", BottomSheetColor.DEFAULT),
                    BottomSheetInfo("리스트 수정하기", BottomSheetColor.DEFAULT),
                    BottomSheetInfo("목표 그만두기", BottomSheetColor.RED)
                ))
            }
        }
        bottom.callback.observe(this, Observer {
            when (it) {
                0 -> { //다른 참여자 리스트 둘러보기
                    startActivity(Intent(this@OngoingGoalActivity, GoalDetailActivity::class.java).apply {
                        putExtra(Consts.GOAL_ID, goalId)
                        putExtra(Consts.UID, uId)
                        putExtra(Consts.IS_FROM_MYPAGE_ONGOING_GOAL, isFromMyPage)
                    })
                }
                1 -> { // 참여자 목록
                    startActivity(Intent(this, ParticipantsListActivity::class.java).apply {
                        putExtra(Consts.GOAL_ID, goalId)
                        putExtra(Consts.UID, uId)
                    })
                }
                2 -> { // 리스트 수정하기
                    startActivity(Intent(this, EditGoalActivity::class.java).apply {
                        putExtra(Consts.GOAL_ID, goalId)
                        putExtra(Consts.UID, uId)
                    })
                }
                3 -> { // 목표 그만두기
                    startActivityForResult(Intent(this, EroojaDialogActivity::class.java).apply {
                        putExtra(Consts.DIALOG_TITLE, "목표를 던지시겠습니까?")
                        putExtra(Consts.DIALOG_CONTENT, "그만둔 목표는 참여중인 목표에서 사라집니다. 정말 그만두시겠어요?")
                        putExtra(Consts.DIALOG_CONFIRM, true)
                        putExtra(Consts.DIALOG_CANCEL, true)
                    }, Consts.GOAL_ABANDONED_REQUEST)
                }
                else -> {}
            }
            bottom.dismiss()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Consts.GOAL_ABANDONED_REQUEST && resultCode == 6000) {
            data?.let {
                val result = it.getBooleanExtra(Consts.DIALOG_RESULT, false)
                if (result) {
                    presenter.setGoalIsAbandoned(goalId, abandonedRequest = GoalAbandonedRequest(true))
                }
            }
        }
    }

    override fun onAbandonedSuccess() {
        finish()
    }

    override fun onAbandonedFailure() {
        this.toastShort("포기 실패 ㅠㅠ 다시 시도해주세요.")
    }

    fun additionalOptionClicked() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
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
