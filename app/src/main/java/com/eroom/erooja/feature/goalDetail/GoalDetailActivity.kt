package com.eroom.erooja.feature.goalDetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalDetailsBinding
import kotlinx.android.synthetic.main.goal_detail_desc.*

class GoalDetailActivity :AppCompatActivity() {
    lateinit var binding : ActivityGoalDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goal_details)
        binding.goalDetail = this@GoalDetailActivity

        initview()
    }

    fun moreClick(view:View){

        when(more_txt.text){
            "더보기" -> {
                gradient_view.visibility = View.INVISIBLE
                more_txt.text="닫기"
                Log.i("click"," INVISIBLE ")
            }
            "닫기" -> {
                gradient_view.visibility = View.VISIBLE
                more_txt.text="더보기"
                Log.i("click"," VISIBLE ")

            }
        }

    }

    fun initview(){

    }
}