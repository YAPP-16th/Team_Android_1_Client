package com.eroom.erooja.feature.addGoal.newGoalFrame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.eroom.domain.utils.toastLong
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityNewGoalBinding
import com.eroom.erooja.feature.addGoal.newGoalPage.GoalDetailFragment
import com.eroom.erooja.feature.addGoal.newGoalPage.GoalPeriodFragment
import com.eroom.erooja.feature.addGoal.newGoalPage.GoalTitleFragment
import com.eroom.calendar.AirCalendarDatePickerActivity
import com.eroom.calendar.core.AirCalendarIntent
import com.eroom.domain.utils.ProgressBarAnimation
import com.eroom.erooja.feature.addGoal.newGoalPage.GoalListFragment
import kotlinx.android.synthetic.main.activity_new_goal.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


class NewGoalActivity : AppCompatActivity(), NewGoalContract.View {
    private val REQUEST_CODE = 3000
    private lateinit var newGoalBinding: ActivityNewGoalBinding
    private lateinit var presenter: NewGoalContract.Presenter
    private val mFragmentList = ArrayList<Fragment>()
    private var mPage = 0


    private var goalDetailContentText = ""

    private var startDate: String = ""
    private var endDate = ""

    private var isChangeable: Boolean = false
    private var goalList: ArrayList<String> = ArrayList()

    private var goalTitleText = ""
    var nextClickable: ObservableField<Boolean> = ObservableField(false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initFragment()
        observeData()
        setDefaultPeriod()
    }

    private fun initPresenter() {
        presenter = NewGoalPresenter(this)
    }

    private fun setDefaultPeriod() {
        var today: Calendar = Calendar.getInstance()
        today.timeInMillis = System.currentTimeMillis()
        startDate =
            "" + today.get(Calendar.YEAR) + "년 " + (today.get(Calendar.MONTH) + 1) + "월 " + today.get(
                Calendar.DAY_OF_MONTH
            ) + "일"
        endDate = startDate
    }

    private fun setUpDataBinding() {
        newGoalBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_goal)
        newGoalBinding.activity = this
    }

    private fun observeData() {
        (mFragmentList[0] as GoalTitleFragment).goalTitle.observe(this, Observer {
            goalTitleText = it
        })
        (mFragmentList[0] as GoalTitleFragment).goalTitleCheck.observe(this, Observer {
            nextClickable.set(it)
        })
        (mFragmentList[1] as GoalDetailFragment).goalDetailContent.observe(this, Observer {
            goalDetailContentText = it
            Timber.e(goalDetailContentText)
        })
        (mFragmentList[2] as GoalPeriodFragment).isChangeable.observe(this, Observer {
            isChangeable = it
        })
        (mFragmentList[3] as GoalListFragment).goalList.observe(this, Observer {
            this.goalList.addAll(it)
            nextClickable.set(!it.isNullOrEmpty())
        })
        (mFragmentList[3] as GoalListFragment).goalListCheck.observe(this, Observer {
            nextClickable.set(it)
        })
    }

    private fun initFragment() {
        mFragmentList.apply {
            addAll(
                listOf(
                    GoalTitleFragment.newInstance()
                    , GoalDetailFragment.newInstance()
                    , GoalPeriodFragment.newInstance()
                    , GoalListFragment.newInstance()
                )
            )
        }.also {
            repeat(it.size) { index ->
                supportFragmentManager.beginTransaction().add(R.id.newGoalFrame, it[index])
                    .hide(it[index]).commit()
            }
            supportFragmentManager.beginTransaction().show(it[0]).commit()
        }
        setProgressBar(true)
    }



    private fun showFragment() {
        hideFragment()
        newGoalBinding.nextTextView.text = if(mPage == mFragmentList.size - 1) "완료" else "다음"
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
                if (mPage > mFragmentList.size - 1) {
                    requestNewGoal()
                    finish()
                    return
                } else if (mPage == mFragmentList.size - 1) {
                    nextClickable.set(!goalList.isNullOrEmpty())
                }
                setProgressBar(true)
                showFragment()
            }
        }
    }

    private fun requestNewGoal() {
        var content = ""
        content = if (isChangeable) {
            "수정가능"
        } else {
            "수정 불가능"
        }
        this.toastLong("$content$goalTitleText \n 두번쨰 : $goalDetailContentText  \n 종료일 : $endDate\n\n $goalList")
        showFragment()
        nextClickable.set(false)
    }



    override fun onBackPressed() {
        prevButtonClicked()
    }

    fun calendarCall() {
        val intent = AirCalendarIntent(this)
        intent.isBooking(false)
        intent.isSelect(false)

        var today: Calendar = Calendar.getInstance()
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
                    this.endDate = endDate
                    val time = endDate.split("-")
                    (mFragmentList[2] as GoalPeriodFragment).setEndDate("${time[0]}년 ${time[1]}월 ${time[2]}일")
                }
            }
        }

    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
        requestNewGoal()
    }
}
