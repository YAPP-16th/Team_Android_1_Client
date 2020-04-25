package com.eroom.erooja.feature.endPopUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityEndGoalPopUpBinding
import com.eroom.erooja.databinding.ActivityNewGoalFinishBinding
import com.google.android.gms.common.util.DataUtils

class EndGoalPopUpActivity : AppCompatActivity(), EndGoalPopUpContract.View {
    private lateinit var endGoalPopUpBinding: ActivityEndGoalPopUpBinding
    private lateinit var presenter : EndGoalPopUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    private fun setUpDataBinding() {
        endGoalPopUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_end_goal_pop_up)
        endGoalPopUpBinding.activity = this
    }

    override fun setView(goalTitle: String, achieveRate: Int) {
        val badMaxim: String = "‘오늘 하나는 내일 둘의 가치가 있다.’는 말이 있죠.\n" +
                "다음 목표는 더 힘내서 달성해봅시다!"
        val goodMaxim: String = "열심히 달려온 당신, 칭찬의 박수 짝짝짝.\n" +
                "새로운 목표에서 리스트를 시작해보세요."

        endGoalPopUpBinding.goalTitle.text = goalTitle.trim()
        endGoalPopUpBinding.achieveRate.text = "${achieveRate}% 달성"

        when {
            achieveRate <= 40 -> {
                endGoalPopUpBinding.achieveRateImage.setImageResource(R.drawable.ic_achieve_rate_under_40)
                endGoalPopUpBinding.achieveMaxim.text = badMaxim
            }
            achieveRate < 70 -> {
                endGoalPopUpBinding.achieveRateImage.setImageResource(R.drawable.ic_achieve_rate_40_to_70)
                endGoalPopUpBinding.achieveMaxim.text = badMaxim
            }
            else -> {
                endGoalPopUpBinding.achieveRateImage.setImageResource(R.drawable.ic_achieve_rate_over_70)
                endGoalPopUpBinding.achieveOver70Background.visibility = View.VISIBLE
                endGoalPopUpBinding.achieveMaxim.text = goodMaxim
            }
        }
    }

    override fun navigateToMainPage() {
        finish()
    }

    override fun initView() {
        presenter = EndGoalPopUpPresenter(this)
        presenter.getData()
    }

    override fun navigateToSearchGoal() {
        finish()
        //목표탐색뷰로 넘어가는 로직 추가
    }
}
