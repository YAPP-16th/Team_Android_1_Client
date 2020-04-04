package com.eroom.erooja.feature.goalDetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalDetailsBinding
import com.eroom.erooja.feature.goalDetail.othersList.OthersDetailActivity
import kotlinx.android.synthetic.main.include_goal_desc.*

class GoalDetailActivity :AppCompatActivity() {
    lateinit var binding : ActivityGoalDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goal_details)
        binding.goalDetail = this@GoalDetailActivity

        goal_desc.showButton = false
        goal_desc.showShadow = true
        goal_desc.animationDuration = 300
    }

    fun moreClick(view:View){
        goal_desc.toggle()

        when(more_text.text){
            "더보기" -> more_text.text = "닫기"
            "닫기" -> more_text.text = "더보기"
        }

    }

    fun othersDetailClick(view:View){
        var intent= Intent(this@GoalDetailActivity, OthersDetailActivity::class.java)
        startActivity(intent)
    }

}