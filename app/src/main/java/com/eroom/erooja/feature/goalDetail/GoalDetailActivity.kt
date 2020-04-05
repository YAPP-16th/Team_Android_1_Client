package com.eroom.erooja.feature.goalDetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.UserSimpleData
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalDetailsBinding
import com.eroom.erooja.feature.goalDetail.othersList.OthersDetailActivity
import kotlinx.android.synthetic.main.activity_goal_details.*
import kotlinx.android.synthetic.main.include_goal_desc.*
import kotlinx.android.synthetic.main.include_goal_desc.view.*
import org.koin.android.ext.android.bind

class GoalDetailActivity :AppCompatActivity(), GoalDetailContract.View {
    lateinit var binding : ActivityGoalDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    fun moreClick(view:View){
        binding.goalDescLayout.goal_desc.toggle()

        when(more_text.text){
            resources.getString(R.string.more) -> more_text.text = resources.getString(R.string.close)
            resources.getString(R.string.close) -> more_text.text = resources.getString(R.string.more)
        }

    }

    fun othersDetailClick(view:View){
        var intent= Intent(this@GoalDetailActivity, OthersDetailActivity::class.java)
        startActivity(intent)
    }

    override fun getAllView(list: UserSimpleData) {
        others_recyclerview.apply{
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
}