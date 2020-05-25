package com.eroom.erooja.feature.endPopUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.eroom.data.entity.AlarmContent
import com.eroom.domain.customview.parcelizeclass.ParcelizeAlarmContent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.loadGif
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityEndGoalPopUpBinding
import com.eroom.erooja.databinding.ActivityNewGoalFinishBinding
import com.google.android.gms.common.util.DataUtils
import org.koin.android.ext.android.get

class EndGoalPopUpActivity : AppCompatActivity(), EndGoalPopUpContract.View {
    private lateinit var endGoalPopUpBinding: ActivityEndGoalPopUpBinding
    private lateinit var presenter : EndGoalPopUpPresenter

    private lateinit var list: ArrayList<ParcelizeAlarmContent>
    private var alarmSize: Int = 0
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    private fun setUpDataBinding() {
        endGoalPopUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_end_goal_pop_up)
        endGoalPopUpBinding.activity = this
    }

    override fun initView() {
        overridePendingTransition(R.anim.hold, R.anim.slide_down)
        presenter = EndGoalPopUpPresenter(this, get(), get())
        list = intent.getParcelableArrayListExtra(Consts.POP_UP_LIST) ?: ArrayList()
        alarmSize = list.size
        if (alarmSize == 0) finish()
        else loadPopUpData()
    }

    private fun loadPopUpData() {
        if (index >= alarmSize) endGoalPopUpBinding.nextButton.visibility = View.GONE
        else {
            endGoalPopUpBinding.nextButton.visibility = View.VISIBLE
            startAnimation()
            presenter.getData(list[index])
            presenter.readAlarmRequest(list[index].id)
        }
    }

    fun nextButtonClicked() {
        index++
        loadPopUpData()
    }

    override fun setView(goalTitle: String, achieveRate: Int) {
        val badMaxim: String = "‘오늘 하나는 내일 둘의 가치가 있다.’는 말이 있죠.\n" +
                "다음 목표는 더 힘내서 달성해봅시다!"
        val goodMaxim: String = "열심히 달려온 당신, 칭찬의 박수 짝짝짝.\n" +
                "새로운 목표에서 리스트를 시작해보세요."

        endGoalPopUpBinding.goalTitle.text = goalTitle.trim()
        endGoalPopUpBinding.achieveRate.text = "${achieveRate}% 달성"
        endGoalPopUpBinding.rlDoneBtn.text = "${index + 1}/$alarmSize"

        when {
            achieveRate <= 40 -> {
                endGoalPopUpBinding.gifImage.visibility = View.VISIBLE
                endGoalPopUpBinding.gifImage.loadGif(R.raw.onboarding0to40)
                endGoalPopUpBinding.lottie100Image.visibility = View.GONE
                endGoalPopUpBinding.achieveMaxim.text = badMaxim
            }
            achieveRate < 70 -> {
                endGoalPopUpBinding.gifImage.visibility = View.VISIBLE
                endGoalPopUpBinding.gifImage.loadGif(R.raw.onboarding40to70)
                endGoalPopUpBinding.lottie100Image.visibility = View.GONE
                endGoalPopUpBinding.achieveMaxim.text = badMaxim
            }
            else -> {
                endGoalPopUpBinding.lottie100Image.visibility = View.VISIBLE
                endGoalPopUpBinding.lottie100Image.playAnimation()
                endGoalPopUpBinding.gifImage.visibility = View.GONE
                endGoalPopUpBinding.achieveMaxim.text = goodMaxim
            }
        }
    }

    override fun navigateToMainPage() {
        finish()
    }

    fun startBlockAnimation() {
        endGoalPopUpBinding.colorLoading.visibility = View.GONE
        endGoalPopUpBinding.blockView.visibility = View.VISIBLE
        endGoalPopUpBinding.whiteLoading.visibility = View.VISIBLE
        endGoalPopUpBinding.colorLoading.cancelAnimation()
        endGoalPopUpBinding.whiteLoading.playAnimation()
    }

    fun startAnimation() {
        endGoalPopUpBinding.blockView.visibility = View.GONE
        endGoalPopUpBinding.whiteLoading.visibility = View.GONE
        endGoalPopUpBinding.colorLoading.visibility = View.VISIBLE
        endGoalPopUpBinding.whiteLoading.cancelAnimation()
        endGoalPopUpBinding.colorLoading.playAnimation()
    }

    override fun stopAnimation() {
        endGoalPopUpBinding.blockView.visibility = View.GONE
        endGoalPopUpBinding.whiteLoading.visibility = View.GONE
        endGoalPopUpBinding.colorLoading.visibility = View.GONE
        endGoalPopUpBinding.whiteLoading.cancelAnimation()
        endGoalPopUpBinding.colorLoading.cancelAnimation()
    }
}
