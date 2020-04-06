package com.eroom.erooja.feature.goalDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.UserSimpleData
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalDetailsBinding
import com.eroom.erooja.feature.goalDetail.othersList.OthersDetailActivity
import kotlinx.android.synthetic.main.include_goal_desc.view.*

class GoalDetailActivity :AppCompatActivity(), GoalDetailContract.View {
    lateinit var binding : ActivityGoalDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
        istouched()
    }

    fun moreClick(view:View){
        binding.goalDescLayout.goal_desc.toggle()

        when(binding.goalDescLayout.more_text.text){
            resources.getString(R.string.more) -> binding.goalDescLayout.more_text.text = resources.getString(R.string.close)
            resources.getString(R.string.close) -> binding.goalDescLayout.more_text.text = resources.getString(R.string.more)
        }

    }

    override fun getAllView(list: UserSimpleData) {
        binding.othersRecyclerview.apply{
            layoutManager = LinearLayoutManager(this@GoalDetailActivity)
            adapter = GoalDetailAdapter(list)

        }
    }

    fun initView(){
        var presenter = GoalDetailPresenter(this)
        presenter.getData()

        binding.goalDescLayout.goal_desc.showButton = false
        binding.goalDescLayout.goal_desc.showShadow = true
    }

    fun setUpDataBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goal_details)
        binding.goalDetail = this@GoalDetailActivity
    }

    @SuppressLint("ClickableViewAccessibility")
    fun istouched(){
        binding.othersRecyclerview.setOnTouchListener { _, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    var intent = Intent(this, OthersDetailActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

    }
}