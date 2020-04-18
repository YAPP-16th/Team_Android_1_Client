package com.eroom.erooja.feature.goalDetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.UserSimpleData
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.add
import com.eroom.domain.utils.statusBarColor
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalDetailsBinding
import com.eroom.erooja.feature.completedGoal.CompletedGoalActivity
import kotlinx.android.synthetic.main.goal_simple_list.view.*
import kotlinx.android.synthetic.main.include_completed_goal_desc.view.*

class GoalDetailActivity: AppCompatActivity(), GoalDetailContract.View {
    lateinit var binding: ActivityGoalDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    fun moreClick(v: View){
        binding.goalDescLayout.goal_desc.toggle()
    }

    fun othersDetailClick(v: View){
        var intent= Intent(this@GoalDetailActivity, CompletedGoalActivity::class.java)
        startActivity(intent)
    }

    override fun getAllView(list: ArrayList<UserSimpleData>) {
        binding.othersRecyclerview.apply{
            layoutManager = LinearLayoutManager(this@GoalDetailActivity)
            adapter = GoalDetailAdapter(list, click())

        }
        binding.participantListText.text = binding.participantListText.text.toString() add "(${list.size})"
    }
    private fun click() = {  _:View, index:Int ->
        var intent = Intent(this@GoalDetailActivity, CompletedGoalActivity::class.java)
            .apply{
                putExtra(Consts.INDEX, index)
                putExtra(Consts.NAME, binding.othersRecyclerview.username_list.text)
                putExtra(Consts.DATE, binding.goalDateTxt.text)
            }
        startActivityForResult(intent, 4000)
    }

    fun initView(){
        var presenter = GoalDetailPresenter(this)
        presenter.getData()
        statusBarColor(this@GoalDetailActivity, R.color.subLight3)

        binding.goalDescLayout.goal_desc.apply{
            showButton = false
            showShadow = false
        }
    }

    fun setUpDataBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goal_details)
        binding.goalDetail = this@GoalDetailActivity
    }


}