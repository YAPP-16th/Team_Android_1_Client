package com.eroom.erooja.feature.addGoal.newGoalFrame

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


class NewGoalActivity : AppCompatActivity(), NewGoalContract.View {
    private lateinit var newGoalBinding: ActivityNewGoalBinding
    private lateinit var presenter: NewGoalContract.Presenter


    private val mFragmentList = ArrayList<Fragment>()
    private var mPage = 0

    private val REQUEST_CODE = 3000
    private var endDateData = ""

    //    private var nicknameText = ""
//    private lateinit var groupSelected: JobGroup
//    private lateinit var devClassSelected: DevelopSelected
//    private lateinit var designClassSelected: DesignSelected
    private var goalTitleText = ""
    var nextClickable: ObservableField<Boolean> = ObservableField(false)

    private var goalDetailContentText = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initFragment()
        observeData()
    }

    private fun initPresenter() {
        presenter = NewGoalPresenter(this)
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
            goalDetailContentText =it
            Timber.e(goalDetailContentText)
        })
        //(mFragmentList[2] as GoalPeriodFragment).goalPerio
    }

    private fun initFragment() {
        mFragmentList.apply {
            addAll(
                listOf(
                    GoalTitleFragment.newInstance()/*.apply { arguments = Bundle().apply { putString("key", "value") } }*/
                    , GoalDetailFragment.newInstance()
                    , GoalPeriodFragment.newInstance()
                    //JobGroupFragment.newInstance()
                )
            )
        }.also {
            repeat(it.size) { index ->
                supportFragmentManager.beginTransaction().add(R.id.newGoalFrame, it[index])
                    .hide(it[index]).commit()
            }
            supportFragmentManager.beginTransaction().show(it[0]).commit()
        }
    }

    fun nextButtonClicked() {
        nextClickable.get()?.let {
            if (it) {
                mPage += 1
                if (mPage > mFragmentList.size - 1) {
                    finish()
                    return
                }
                showFragment()
            }
        }

    }

    private fun showFragment() {
        hideFragment()
        supportFragmentManager.beginTransaction().show(mFragmentList[mPage]).commit()
        //if(mPage == ..)
    }

    private fun hideFragment() = repeat(mFragmentList.size) {
        supportFragmentManager.beginTransaction().hide(mFragmentList[it]).commit()
    }

    fun prevButtonClicked() {
        mPage -= 1
        if (mPage < 0) {
            finish()
            return
        }
        showFragment()
        nextClickable.set(false)
    }

    private fun requestNewGoal() {
        this.toastLong("$goalTitleText \n 두번쨰 : $goalDetailContentText" )
    }

    override fun onBackPressed() {
        prevButtonClicked()
        requestNewGoal()
    }

    fun calendarCall() {
        val intent = AirCalendarIntent(this)
        intent.isBooking(false)
        intent.isSelect(false)

        intent.setStartDate(2020, 4, 11)

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
                val endDate = data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE) ?: "-"
                if (endDate != "-") {
                    endDateData = endDate
                    val time = endDate.split("-")
                    (mFragmentList[2] as GoalPeriodFragment).setEndDate("${time[0]}년 ${time[1]}월 ${time[2]}일")
                }
            }
        }

    }
}
