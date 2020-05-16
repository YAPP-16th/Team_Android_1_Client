package com.eroom.erooja.feature.goalEdit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalEditBinding

class GoalEditActivity : AppCompatActivity(), GoalEditContract.View {
    lateinit var binding: ActivityGoalEditBinding
    lateinit var presenter: GoalEditPresenter

    val underlineError: ObservableField<Boolean> = ObservableField(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
    }

    fun initPresenter() {
        presenter = GoalEditPresenter(this)
    }

    fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goal_edit)
        binding.activity = this
    }

    fun initView() {

    }
}
