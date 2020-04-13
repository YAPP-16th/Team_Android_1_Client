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

    //    private var nicknameText = ""
//    private lateinit var groupSelected: JobGroup
//    private lateinit var devClassSelected: DevelopSelected
//    private lateinit var designClassSelected: DesignSelected
    private var goalTitleText = ""
    var nextClickable: ObservableField<Boolean> = ObservableField(false)

    private var goalDetailContentText = ""

    private var startDate: String =""
    private var endDate = ""

    private var isChangeable:Boolean = false


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
        startDate = "" + today.get(Calendar.YEAR) + "년 " +  (today.get(Calendar.MONTH) + 1) + "월 " + today.get(Calendar.DAY_OF_MONTH) + "일"
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
            goalDetailContentText =it
            Timber.e(goalDetailContentText)
        })
        (mFragmentList[2] as GoalPeriodFragment).isChangeable.observe(this, Observer {
            isChangeable = it
        })
//        (mFragmentList[2] as GoalPeriodFragment).endDate.observe(this, Observer {
//            this.endDate = it
//            Timber.e(endDate)
//        })
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
        hideKeyBoard()
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
        observeData()
    }

    private fun requestNewGoal() {
        var content =""
        if(isChangeable) {
            content = "수정가능"
        } else{
            content = "수정 불가능"
        }
        this.toastLong("$content$goalTitleText \n 두번쨰 : $goalDetailContentText  \n 종료일 : $endDate")

    }

    override fun onBackPressed() {
        prevButtonClicked()
        requestNewGoal()
    }

    fun calendarCall() {
        val intent = AirCalendarIntent(this)
        intent.isBooking(false)
        intent.isSelect(false)

        var today: Calendar = Calendar.getInstance()
        today.timeInMillis = System.currentTimeMillis()
        intent.setStartDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DAY_OF_MONTH))

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
    }
}
