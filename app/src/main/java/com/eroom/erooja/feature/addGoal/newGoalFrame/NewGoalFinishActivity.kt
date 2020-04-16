package com.eroom.erooja.feature.addGoal.newGoalFrame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityNewGoalFinishBinding

class NewGoalFinishActivity : AppCompatActivity() {
    lateinit var newGoalFinishBinding: ActivityNewGoalFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }


    private fun initView() {
        val goalTitle = intent.extras?.getString("goalTitle")
        newGoalFinishBinding.goalTitle.text = goalTitle
    }

    private fun setUpDataBinding() {
        newGoalFinishBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_goal_finish)
        newGoalFinishBinding.activity = this
    }

    override fun onBackPressed() {
        navigateToMainPage()
    }

    fun navigateToMainPage() {
        // 메인화면으로 이동
        finish()
    }

    fun navigateToGoalDetail() {
        // 참여중 목표 상세화면 페이지로 이동
        finish()
    }
}
