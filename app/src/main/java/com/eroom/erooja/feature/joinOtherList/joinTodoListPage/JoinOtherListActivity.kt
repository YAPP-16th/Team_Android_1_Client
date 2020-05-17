package com.eroom.erooja.feature.joinOtherList.joinTodoListPage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.eroom.calendar.AirCalendarDatePickerActivity
import com.eroom.calendar.core.AirCalendarIntent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.ProgressBarAnimation
import com.eroom.domain.utils.toLocalDateFormat
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityJoinOtherListBinding
import com.eroom.erooja.feature.addDirectList.addMyTodoListFrame.*
import com.eroom.erooja.feature.addDirectList.inactivejob.InactiveJobFragment
import com.eroom.erooja.feature.addGoal.newGoalFrame.NewGoalFinishActivity
import com.eroom.erooja.feature.joinOtherList.joinTodoListFrame.JoinGoalPeriodFragment
import com.eroom.erooja.feature.joinOtherList.joinTodoListFrame.JoinTodoListFragment
import org.koin.android.ext.android.get
import java.util.*
import kotlin.collections.ArrayList

class JoinOtherListActivity : AppCompatActivity(), JoinOtherListContract.View {

    private val REQUEST_CODE = 3000

    private lateinit var newGoalBinding: ActivityJoinOtherListBinding
    private lateinit var presenter: JoinOtherListPresenter

    private val mFragmentList = ArrayList<Fragment>()
    private var mPage = 0
    var nextClickable: ObservableField<Boolean> = ObservableField(true)

    private var startDate: String = ""
    private var endDate = ""
    private var additionalGoalList = ""
    private var goalTitleText = ""
    private var goalDetailContentText: String? = null

    //getExtra GoalDetailActivity -> NewGoalActivity
    private var goalDate: String? = null
    private var goalId: Long = 0L
    private var userUid: String = ""
    var userTodoList: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initFragment()
        observeData()
        setDefaultPeriod()
        joinTodoList()
    }

    private fun joinTodoList() {
        // case 1: 기간 고정, 다른 사람의 리스트를 추가하는 경우
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
            //case 2: 기간 설정 가능, 다른 사람의 리스트를 추가하는 경우
            3 -> {
                showFragment()
            }
        }
    }


    private fun initPresenter() {
        presenter = JoinOtherListPresenter(this, get())
        intent.getStringArrayListExtra(Consts.USER_TODO_LIST)?.let {
            userTodoList = it
        }

        //Todo: GoalDetailActivity에서 담은 데이터를 받음
        goalId = intent.getLongExtra(Consts.GOAL_ID, -1L)
        goalTitleText = intent.getStringExtra(Consts.GOAL_TITLE)
        goalDetailContentText = intent.getStringExtra(Consts.DESCRIPTION)
        goalDate = intent.getStringExtra(Consts.DATE)
        userUid = intent.getStringExtra(Consts.UID)

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
        newGoalBinding = DataBindingUtil.setContentView(this, R.layout.activity_join_other_list)
        newGoalBinding.activity = this
    }

    private fun observeData() {
        (mFragmentList[4] as JoinTodoListFragment).writingText.observe(this, Observer {
            additionalGoalList = it
        })
    }

    private fun initFragment() {
        mFragmentList.apply {
            addAll(
                listOf(
                    InactiveJobFragment.newInstance().apply {
                        arguments = Bundle().apply {
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
                    JoinGoalPeriodFragment.newInstance(),
                    JoinTodoListFragment.newInstance().apply {
                        arguments = Bundle().apply {
                            putStringArrayList(Consts.USER_TODO_LIST, userTodoList)
                        }
                    }
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
            finish()
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


    private fun networkRequest() {
        if (additionalGoalList.isNotEmpty()) {
            presenter.addMyGoal(
                goalId,
                userUid,
                endDate,
                userTodoList.apply { add(additionalGoalList) })
        } else
            presenter.addMyGoal(goalId, userUid, endDate, userTodoList)
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
                    (mFragmentList[3] as JoinGoalPeriodFragment).setEndDate("${time[0]}년 ${time[1]}월 ${time[2]}일")
                    this.endDate = toLocalDateFormat(time[0], time[1], time[2])
                }
            }
        }
    }

    override fun redirectNewGoalFinish(resultId: Long) {
        val intent = Intent(this, NewGoalFinishActivity::class.java)
        intent.putExtra(Consts.GOAL_TITLE, goalTitleText)
        intent.putExtra(Consts.ADD_NEW_GOAL_RESULT_ID, resultId)
        startActivity(intent)
        finish()
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
        //presenter.onCleared()
        super.onDestroy()
    }
}