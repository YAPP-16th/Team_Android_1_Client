package com.eroom.erooja.feature.addGoal.newGoalFrame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityNewGoalFinishBinding

class NewGoalFinishActivity : AppCompatActivity() {
    private lateinit var newGoalFinishBinding: ActivityNewGoalFinishBinding
    private var resultId: Long = -1

    companion object {
        const val errorId: Long = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }


    private fun initView() {
        val goalTitle = intent.extras?.getString(Consts.GOAL_TITLE)
        newGoalFinishBinding.goalTitle.text = goalTitle
        resultId = intent.extras?.getLong(Consts.ADD_NEW_GOAL_RESULT_ID) ?: -1
    }

    private fun setUpDataBinding() {
        newGoalFinishBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_goal_finish)
        newGoalFinishBinding.activity = this
    }

    override fun onBackPressed() {
        navigateToMainPage()
    }

    fun navigateToMainPage() {
        finish()
    }

    fun navigateToGoalDetail() {
        if (resultId != errorId) {
            //startActivity(Intent(this, 참여중 목표 뷰 Activity))
        }
        finish()
    }
}
