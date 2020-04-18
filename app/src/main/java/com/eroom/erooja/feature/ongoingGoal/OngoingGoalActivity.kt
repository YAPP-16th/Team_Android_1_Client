package com.eroom.erooja.feature.ongoingGoal

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.UserSimpleData
import com.eroom.domain.utils.statusBarColor
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalBinding
import kotlinx.android.synthetic.main.include_ongoing_goal_desc.view.*

class OngoingGoalActivity: AppCompatActivity(), OngoingGoalContract.View {
    lateinit var binding: ActivityGoalBinding
    val isChecked: ObservableField<Boolean> = ObservableField(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    fun setUpDataBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goal)
        binding.mygoal = this@OngoingGoalActivity

    }

    override fun getAllView(list: ArrayList<UserSimpleData>) {
        binding.mygoalRecyclerview.apply{
            layoutManager = LinearLayoutManager(this@OngoingGoalActivity)
            adapter = OngoingGoalAdapter(list)
        }
//        if (binding.mygoalRecyclerview.ongoing_detail_checkbox.isChecked) {
//            isChecked.set(true)
//            binding.mygoalRecyclerview.ongoing_detail_checkbox.apply {
//                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//                setTextColor(context.getColor(R.color.grey4))
//            }
//        } else {
//            isChecked.set(false)
//            binding.mygoalRecyclerview.ongoing_detail_checkbox.apply {
//                paintFlags = 0
//                setTextColor(context.getColor(R.color.grey7))
//            }
//        }
    }

    fun initView() {
        var presenter = OngoingGoalPresenter(this)
        presenter.getData()

        statusBarColor(this@OngoingGoalActivity, R.color.orgDefault)

        binding.goalDescLayout.goal_desc.apply {
            showButton = false
            showShadow = false
        }
    }

    fun moreClick(view: View){
        binding.goalDescLayout.goal_desc.toggle()
    }
}
