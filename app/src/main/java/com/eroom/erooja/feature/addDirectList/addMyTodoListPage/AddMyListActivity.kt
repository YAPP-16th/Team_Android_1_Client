package com.eroom.erooja.feature.addDirectList.addMyTodoListPage


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
import com.eroom.erooja.R
import com.eroom.calendar.AirCalendarDatePickerActivity
import com.eroom.calendar.core.AirCalendarIntent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.ProgressBarAnimation
import com.eroom.domain.utils.toLocalDateFormat
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.databinding.ActivityAddMyNewGoalBinding
import com.eroom.erooja.feature.addGoal.newGoalFrame.NewGoalFinishActivity
import com.eroom.erooja.feature.addDirectList.addMyTodoListFrame.AddMyTodoListFragment
import com.eroom.erooja.feature.joinOtherList.joinTodoListFrame.JoinGoalPeriodFragment
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
    private var userTodoList: ArrayList<String> = arrayListOf("")
    private var isThereAnyFragment = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initFragment()
        observeData()
        setDefaultPeriod()
        addAndJoinMyList()
    }

    private fun addAndJoinMyList() {
        // case 1: 기간 고정, 내가 리스트를 추가하는 경우
        if (!goalDate.equals("기간 설정 자유")
        ) {
            mPage = 1
            isThereAnyFragment = false
            showFragment()
            val endGoalDate = goalDate!!.split("~")
            val endGoalDate1 = endGoalDate[1].split(".")
            endDate = toLocalDateFormat(endGoalDate1[0], endGoalDate1[1], endGoalDate1[2])
        }
        // case 2: 기간 설정 가능, 내가 리스트를 추가하는 경우
        else {
            mPage = 0
            //nextClickable.set(true)
            showFragment()
        }
    }

    private fun initPresenter() {
        presenter = AddMyListPresenter(this, get())

        //Todo: GoalDetailActivity에서 담은 데이터를 받음
        goalId = intent.getLongExtra(Consts.GOAL_ID, -1L)
        goalTitleText = intent.getStringExtra(Consts.GOAL_TITLE)
        goalDetailContentText = intent.getStringExtra(Consts.DESCRIPTION)
        goalDate = intent.getStringExtra(Consts.DATE)
        ownerUid = null

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
//        (mFragmentList[1] as AddMyTodoListFragment).goalList.observe(this, Observer {
//              nextClickable.set(!this.goalList.isNullOrEmpty())
//        })
//        (mFragmentList[1] as AddMyTodoListFragment).goalListCheck.observe(this, Observer {
//           // nextClickable.set(it)
//        })
//        (mFragmentList[1] as AddMyTodoListFragment).writingText.observe(this, Observer {
//
//        })
//
        (mFragmentList[1] as AddMyTodoListFragment).writingText.observe(this, androidx.lifecycle.Observer {
            additionalGoalList = it
        })
    }

    private fun initFragment() {
        mFragmentList.apply {
            addAll(
                listOf(
                    JoinGoalPeriodFragment.newInstance(),
                    AddMyTodoListFragment.newInstance()
                )
            )
        }.also {
            repeat(it.size) { index ->
                supportFragmentManager.beginTransaction().add(R.id.newGoalFrame, it[index])
                    .hide(it[index]).commit()
            }

            supportFragmentManager.beginTransaction().show(it[mPage]).commit()
        }
        setProgressBar()
    }

    private fun showFragment() {
        hideFragment()
        newGoalBinding.nextTextView.text = if (mPage == 0) "다음" else "완료"

        supportFragmentManager.beginTransaction().show(mFragmentList[mPage]).commit()
    }

    private fun hideFragment() = repeat(mFragmentList.size) {
        supportFragmentManager.beginTransaction().hide(mFragmentList[it]).commit()
    }

    private fun setProgressBar() {
        val progressBar = newGoalBinding.horizontalProgressBar
        val anim = ProgressBarAnimation(
            progressBar,
            progressBar.max.toFloat() * (mPage + 2) / 4,
            progressBar.max.toFloat() * (mPage + 3) / 4
        )
        anim.duration = 250
        progressBar.startAnimation(anim)
    }

    fun prevButtonClicked() {
        hideKeyBoard()

        //이전 프래그먼트가 존재합니까?
        if(!isThereAnyFragment){
            finish()
            return
        }
        else{
            mPage -=1
            if (mPage < 0) {
                finish()
                return
            }
            nextClickable.set(true)
            setProgressBar()
            showFragment()
        }


    }

    fun nextButtonClicked() {
        hideKeyBoard()
        mPage += 1
        when {
            mPage >= 2 -> {
                networkRequest()
                return
            }
//                    mPage == 1 -> {
//                        addMyList?.let{ nextClickable.set(!goalList.isNullOrEmpty()) }
//                    }
            else -> {
                nextClickable.set(true)
            }
        }
        setProgressBar()
        showFragment()

    }

    override fun redirectNewGoalFinish(resultId: Long) {
        val intent = Intent(this, NewGoalFinishActivity::class.java)
        intent.putExtra(Consts.GOAL_TITLE, goalTitleText)
        intent.putExtra(Consts.ADD_NEW_GOAL_RESULT_ID, resultId)
        startActivity(intent)
        finish()
    }

    private fun networkRequest() {
        if (additionalGoalList.isNotEmpty()) {
            presenter.addMyGoal(
                goalId,
                ownerUid,
                endDate,
                goalList.apply { add(additionalGoalList) })
        } else
            presenter.addMyGoal(goalId, ownerUid, endDate, goalList)
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
                    (mFragmentList[0] as JoinGoalPeriodFragment).setEndDate("${time[0]}년 ${time[1]}월 ${time[2]}일")
                    this.endDate = toLocalDateFormat(time[0], time[1], time[2])
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
}