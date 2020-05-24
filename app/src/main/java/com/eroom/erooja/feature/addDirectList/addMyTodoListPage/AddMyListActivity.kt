package com.eroom.erooja.feature.addDirectList.addMyTodoListPage


import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.eroom.erooja.R
import com.eroom.calendar.AirCalendarDatePickerActivity
import com.eroom.calendar.core.AirCalendarIntent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.ProgressBarAnimation
import com.eroom.domain.utils.toLocalDateFormat
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.databinding.ActivityAddMyNewGoalBinding
import com.eroom.erooja.dialog.EroojaDialogActivity
import com.eroom.erooja.feature.addDirectList.addMyTodoListFrame.*
import com.eroom.erooja.feature.addGoal.newGoalFrame.NewGoalFinishActivity
import com.eroom.erooja.feature.addDirectList.inactivejob.InactiveJobFragment
import com.eroom.erooja.feature.addGoal.newGoalPage.GoalListFragment
import com.eroom.erooja.feature.joinOtherList.joinTodoListFrame.JoinGoalPeriodFragment
import com.eroom.erooja.feature.joinOtherList.joinTodoListFrame.JoinTodoListFragment
import com.eroom.erooja.singleton.UserInfo
import org.koin.android.ext.android.get
import java.util.*
import kotlin.collections.ArrayList


class AddMyListActivity : AppCompatActivity(),
    AddMyListContract.View {
    private val REQUEST_CODE = 3000

    private lateinit var newGoalBinding: ActivityAddMyNewGoalBinding
    private lateinit var presenter: AddMyListPresenter

    private val mFragmentList = ArrayList<Fragment>()
    private var mPage = 0
    var nextClickable: ObservableField<Boolean> = ObservableField(true)


    private var goalList: ArrayList<String> = ArrayList()
    private var startDate: String = ""
    private var endDate = ""
    private var additionalGoalList = ""
    private var goalTitleText = ""
    private var goalDetailContentText: String? = null

    //getExtra GoalDetailActivity -> NewGoalActivity
    private var goalDate: String? = null
    private var goalId: Long? = null
    private var ownerUid: String? = null

    private var isMyEndedGoal: Boolean = false
    private var isMyAbandonedGoal: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initFragment()
        observeData()
        setDefaultPeriod()
        addMyList()
    }

    private fun addMyList() {
        // case 1: 기간 고정, 내가 리스트를 추가하는 경우
        when(mPage) {
            4 -> {
                showFragment()
                val endGoalDate = goalDate!!.split("~")
                val endGoalDate1 = endGoalDate[1].split(".")
                endDate =
                    toLocalDateFormat("20" + endGoalDate1[0], endGoalDate1[1], endGoalDate1[2])
                mFragmentList[3].apply {
                    arguments = Bundle().apply {
                        putString(Consts.END_DATE, endGoalDate[1])
                    }
                }
            }
            // case 2: 기간 설정 가능, 내가 리스트를 추가하는 경우
            3 -> {
                showFragment()
            }
        }
    }

    private fun initPresenter() {
        presenter = AddMyListPresenter(this, get(), get())

        //Todo: GoalDetailActivity에서 담은 데이터를 받음
        goalId = intent.getLongExtra(Consts.GOAL_ID, -1L)
        goalTitleText = intent.getStringExtra(Consts.GOAL_TITLE)
        goalDetailContentText = intent.getStringExtra(Consts.DESCRIPTION)
        goalDate = intent.getStringExtra(Consts.DATE)
        ownerUid = intent.getStringExtra(Consts.OWNER_UID)

        isMyEndedGoal = intent.getBooleanExtra(Consts.IS_MY_ENDED_GOAL, false)
        isMyAbandonedGoal = intent.getBooleanExtra(Consts.IS_MY_ABANDONED_GOAL, false)

        if (!goalDate.equals("기간 설정 자유")) mPage = 4
        else mPage = 3

    }


    private fun setDefaultPeriod() {
        val today: Calendar = Calendar.getInstance()
        today.timeInMillis = System.currentTimeMillis()
        startDate =
            "" + today.get(Calendar.YEAR) + "년 " + (today.get(Calendar.MONTH) + 1) + "월 " + today.get(
                Calendar.DAY_OF_MONTH
            ) + "일"
        //2020-05-25T00:00:00
        endDate = toLocalDateFormat(
            today.get(Calendar.YEAR),
            (today.get(Calendar.MONTH) + 1),
            today.get(Calendar.DAY_OF_MONTH)
        )
    }

    private fun setUpDataBinding() {
        newGoalBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_my_new_goal)
        newGoalBinding.activity = this
    }

    private fun observeData() {
        (mFragmentList[4] as AddMyTodoListFragment).goalList.observe(this, Observer {
            this.goalList = it
            nextClickable.set(!it.isNullOrEmpty())
        })
        (mFragmentList[4] as AddMyTodoListFragment).goalListCheck.observe(this, Observer {
            nextClickable.set(it)
        })

        (mFragmentList[4] as AddMyTodoListFragment).writingText.observe(this, Observer {
            additionalGoalList = it //한 글자마다 인식 엔터 칠때마다 다시 여기로 들어
        })

//        if(goalList.size >= 1) newGoalBinding.nextTextView.setTextColor(resources.getColor(R.color.grey4, null))
//        else newGoalBinding.nextTextView.setTextColor(resources.getColor(R.color.grey7, null))
    }

    private fun initFragment() {
        mFragmentList.apply {
            addAll(
                listOf(
                    InactiveJobFragment.newInstance().apply{
                        arguments = Bundle().apply{
                            goalId?.let { putLong(Consts.GOAL_ID, it) }
                        }
                    },
                    InactiveGoalTitleFragment.newInstance().apply {
                        arguments = Bundle().apply {
                            putString(Consts.GOAL_TITLE, goalTitleText)
                        }
                    },
                    InactiveGoalDetailFragment.newInstance().apply {
                        arguments = Bundle().apply {
                            putString(Consts.DESCRIPTION, goalDetailContentText)
                        }
                    },
                    AddGoalPeriodFragment.newInstance(),
                    AddMyTodoListFragment.newInstance()
                )
            )
        }
        if (!goalDate.equals("기간 설정 자유")) {
            mFragmentList[3] = InactiveGoalPeriodFragment.newInstance()
        }
        mFragmentList.also {
            repeat(it.size) { index ->
                supportFragmentManager.beginTransaction().add(R.id.newGoalFrame, it[index])
                    .hide(it[index]).commit()
            }

            supportFragmentManager.beginTransaction().show(it[mPage]).commit()
        }
        setProgressBar(true)
    }

    private fun showFragment() {
        hideFragment()
        newGoalBinding.nextTextView.text = if (mPage == 4) "완료" else "다음"
        supportFragmentManager.beginTransaction().show(mFragmentList[mPage]).commit()

    }

    private fun hideFragment() = repeat(mFragmentList.size) {
        supportFragmentManager.beginTransaction().hide(mFragmentList[it]).commit()
    }

    private fun setProgressBar(isIncreasing: Boolean) {
        val progressBar = newGoalBinding.horizontalProgressBar
        val prev = if (isIncreasing) {
            (progressBar.max.toDouble() / mFragmentList.size) * (mPage)
        } else {
            (progressBar.max.toDouble() / mFragmentList.size) * (mPage + 2)
        }
        val next = (100.0 / mFragmentList.size) * (mPage + 1)
        val anim = ProgressBarAnimation(progressBar, prev.toFloat(), next.toFloat())
        anim.duration = 250
        progressBar.startAnimation(anim)
    }


    fun prevButtonClicked() {
        hideKeyBoard()
        mPage -= 1
        if (mPage < 0) {
            showAlert()
            return
        }
        nextClickable.set(true)
        setProgressBar(false)
        showFragment()
    }

    fun nextButtonClicked() {
        hideKeyBoard()
        mPage += 1
        when {
            mPage > mFragmentList.size - 1 -> {
                networkRequest()
                return
            }
            else -> {
                nextClickable.set(true)
            }
        }
        setProgressBar(true)
        showFragment()

    }

    private fun showAlert() {
            //해당 리스트에 참여하기를 그만두시겠어요?
            startActivityForResult(
                Intent(
                    this,
                    EroojaDialogActivity::class.java
                ).apply {
                    putExtra(Consts.DIALOG_TITLE, "")
                    putExtra(
                        Consts.DIALOG_CONTENT,
                        "해당 리스트에 참여하기를 그만두시겠어요?"
                    )
                    putExtra(Consts.DIALOG_CONFIRM, true)
                    putExtra(Consts.DIALOG_CANCEL, true)
                }, 1300
            )
        }

    override fun redirectNewGoalFinish(resultId: Long) {
        val intent = Intent(this, NewGoalFinishActivity::class.java)
        intent.putExtra(Consts.GOAL_TITLE, goalTitleText)
        intent.putExtra(Consts.ADD_NEW_GOAL_RESULT_ID, resultId)
        intent.putExtra(Consts.UID, UserInfo.myUId)
        startActivity(intent)
        finish()
    }

    private fun networkRequest() {
        startBlockAnimation()
        if(isMyEndedGoal) {
            //myPage -> 종료탭에 있는 목표라면? -> 재참
            presenter.reparticipateToMyEndedGoal(goalId!!, endDate)
        } else {
            if (additionalGoalList.isNotEmpty()) {
                presenter.addMyGoal(
                    goalId,
                    ownerUid,
                    endDate,
                    goalList.apply { add(additionalGoalList) })
            } else {
                var a = goalId
                var b = ownerUid
                var c = endDate
                var d = goalList
                presenter.addMyGoal(goalId, ownerUid, endDate, goalList)
            }
        }
    }

    override fun onBackPressed() {
        prevButtonClicked()
    }

    fun calendarCall() {
        val intent = AirCalendarIntent(this)
        intent.isBooking(false)
        intent.isSelect(false)

        val today: Calendar = Calendar.getInstance()
        today.timeInMillis = System.currentTimeMillis()
        intent.setStartDate(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH) + 1,
            today.get(Calendar.DAY_OF_MONTH)
        )

        intent.isMonthLabels(false)
        intent.setSelectButtonText("선택") //the select button text
        intent.setWeekStart(Calendar.MONDAY)
        intent.setWeekDaysLanguage(AirCalendarIntent.Language.KO) //language for the weekdays
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                val endDate =
                    data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE) ?: "-"

                if (endDate != "-") {
                    val time = endDate.split("-")
                    (mFragmentList[3] as AddGoalPeriodFragment).setEndDate("${time[0]}년 ${time[1]}월 ${time[2]}일")
                    this.endDate = toLocalDateFormat(time[0], time[1], time[2])
                }
            }
        } else if (requestCode == 1300 && resultCode == 6000) {
            data?.let {
                val result = it.getBooleanExtra(Consts.DIALOG_RESULT, false) //확인 or 취소
                if (result) {
                    finish()
                } else {
                    mPage = 0
                }
            }
        }
    }

    fun showKeyboard(input: EditText) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(input, 0)
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }

    override fun failRequest() {
        mPage -= 1
        this.toastShort("목표생성을 실패하였습니다")
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }

    fun startBlockAnimation() {
        newGoalBinding.colorLoading.visibility = View.GONE
        newGoalBinding.blockView.visibility = View.VISIBLE
        newGoalBinding.whiteLoading.visibility = View.VISIBLE
        newGoalBinding.colorLoading.cancelAnimation()
        newGoalBinding.whiteLoading.playAnimation()
    }

    fun startAnimation() {
        newGoalBinding.blockView.visibility = View.GONE
        newGoalBinding.whiteLoading.visibility = View.GONE
        newGoalBinding.colorLoading.visibility = View.VISIBLE
        newGoalBinding.whiteLoading.cancelAnimation()
        newGoalBinding.colorLoading.playAnimation()
    }

    override fun stopAnimation() {
        newGoalBinding.blockView.visibility = View.GONE
        newGoalBinding.whiteLoading.visibility = View.GONE
        newGoalBinding.colorLoading.visibility = View.GONE
        newGoalBinding.whiteLoading.cancelAnimation()
        newGoalBinding.colorLoading.cancelAnimation()
    }
}