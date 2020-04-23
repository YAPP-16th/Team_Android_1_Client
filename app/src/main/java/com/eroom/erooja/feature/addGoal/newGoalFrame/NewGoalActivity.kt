
package com.eroom.erooja.feature.addGoal.newGoalFrame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.eroom.domain.utils.toastLong
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityNewGoalBinding
import com.eroom.calendar.AirCalendarDatePickerActivity
import com.eroom.calendar.core.AirCalendarIntent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.ProgressBarAnimation
import com.eroom.domain.utils.toLocalDateFormat
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.feature.addGoal.newGoalPage.*
import com.eroom.erooja.feature.addGoal.newGoalPage.selectjob.SelectJobFragment
import org.koin.android.ext.android.get
import java.util.*
import kotlin.collections.ArrayList


class NewGoalActivity : AppCompatActivity(), NewGoalContract.View {
    private val REQUEST_CODE = 3000

    private lateinit var newGoalBinding: ActivityNewGoalBinding
    private lateinit var presenter: NewGoalPresenter

    private val mFragmentList = ArrayList<Fragment>()
    private var mPage = 0
    var nextClickable: ObservableField<Boolean> = ObservableField(false)

    private var selectedIds: ArrayList<Long> = ArrayList()
    private var goalTitleText = ""
    private var goalDetailContentText = ""
    private var isDateFixed: Boolean = false
    private var goalList: ArrayList<String> = ArrayList()
    private var startDate: String = ""
    private var endDate = ""
    private var additionalGoalList = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initFragment()
        observeData()
        setDefaultPeriod()
    }

    private fun initPresenter() {
        presenter = NewGoalPresenter(this, get())
    }

    private fun setDefaultPeriod() {
        var today: Calendar = Calendar.getInstance()
        today.timeInMillis = System.currentTimeMillis()
        startDate =
            "" + today.get(Calendar.YEAR) + "년 " + (today.get(Calendar.MONTH) + 1) + "월 " + today.get(
                Calendar.DAY_OF_MONTH
            ) + "일"
        //2020-05-25T00:00:00
        endDate = toLocalDateFormat(today.get(Calendar.YEAR), (today.get(Calendar.MONTH) + 1), today.get(Calendar.DAY_OF_MONTH))
    }

    private fun setUpDataBinding() {
        newGoalBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_goal)
        newGoalBinding.activity = this
    }

    private fun observeData() {
        (mFragmentList[0] as SelectJobFragment).selectCheck.observe(this, Observer {
            nextClickable.set(it)
        })
        (mFragmentList[0] as SelectJobFragment).selectList.observe(this, Observer {
            selectedIds = it
        })
        (mFragmentList[1] as GoalTitleFragment).goalTitle.observe(this, Observer {
            goalTitleText = it
        })
        (mFragmentList[1] as GoalTitleFragment).goalTitleCheck.observe(this, Observer {
            nextClickable.set(it)
        })
        (mFragmentList[2] as GoalDetailFragment).goalDetailContent.observe(this, Observer {
            goalDetailContentText = it
        })
        (mFragmentList[3] as GoalPeriodFragment).isChangeable.observe(this, Observer {
            isDateFixed = !it
        })
        (mFragmentList[4] as GoalListFragment).goalList.observe(this, Observer {
            this.goalList = it
            nextClickable.set(!it.isNullOrEmpty())
        })
        (mFragmentList[4] as GoalListFragment).goalListCheck.observe(this, Observer {
            nextClickable.set(it)
        })
        (mFragmentList[4] as GoalListFragment).writingText.observe(this, Observer {
            additionalGoalList = it
        })
    }

    private fun initFragment() {
        mFragmentList.apply {
            addAll(
                listOf(
                    SelectJobFragment.newInstance(),
                    GoalTitleFragment.newInstance()/*.apply { arguments = Bundle().apply { putString("key", "value") } }*/,
                    GoalDetailFragment.newInstance(),
                    GoalPeriodFragment.newInstance(),
                    GoalListFragment.newInstance()
                )
            )
        }.also {
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
        newGoalBinding.nextTextView.text = if (mPage == mFragmentList.size - 1) "완료" else "다음"

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
        nextClickable.get()?.let {
            if (it) {
                mPage += 1
                when {
                    mPage > mFragmentList.size - 1 -> {
                        networkRequest()
                        return
                    }
                    mPage == mFragmentList.size - 1 -> {
                        nextClickable.set(!goalList.isNullOrEmpty())
                    }
                    mPage == 1 -> {
                        nextClickable.set((mFragmentList[1] as GoalTitleFragment).getTitleTextLength() > 4)
                    }
                    else -> {
                        nextClickable.set(true)
                    }
                }
                setProgressBar(true)
                showFragment()
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

    private fun networkRequest() {
        if (additionalGoalList.isNotEmpty())
            presenter.addNewGoal(goalTitleText, goalDetailContentText, isDateFixed, endDate, selectedIds, goalList.apply { add(additionalGoalList) })
        else
            presenter.addNewGoal(goalTitleText, goalDetailContentText, isDateFixed, endDate, selectedIds, goalList)
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
                    (mFragmentList[3] as GoalPeriodFragment).setEndDate("${time[0]}년 ${time[1]}월 ${time[2]}일")
                    this.endDate = toLocalDateFormat(time[0], time[1], time[2])
                }
            }
        }
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }

    override fun failRequest() {
        mPage -= 1
        this.toastShort("목표생성을 실패하였습니다")
    }
}