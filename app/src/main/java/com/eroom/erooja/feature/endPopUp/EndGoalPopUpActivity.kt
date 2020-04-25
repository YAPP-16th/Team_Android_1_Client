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

    override fun setView(achieveRate: Int) {
        when((achieveRate - 1) / 10) {
            0, 1, 2, 3 -> {
                endGoalPopUpBinding.achieveRateTitle.text = "목표를 ${achieveRate}% 달성했어요..."
                endGoalPopUpBinding.achieveRateImage.setImageResource(R.drawable.ic_achieve_rate_under_40)
            }
            4, 5, 6 -> {
                endGoalPopUpBinding.achieveRateTitle.text = "목표를 ${achieveRate}% 달성했어요..."
                endGoalPopUpBinding.achieveRateImage.setImageResource(R.drawable.ic_achieve_rate_40_to_70)
            }
            else -> {
                endGoalPopUpBinding.achieveRateImage.setImageResource(R.drawable.ic_achieve_rate_over_70)
                endGoalPopUpBinding.achieveOver70Background.visibility = View.VISIBLE
                endGoalPopUpBinding.achieveRateTitle.text = "목표를 ${achieveRate}% 달성했습니다!"
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
}
