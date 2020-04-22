package com.eroom.erooja.feature.ongoingGoal

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.UserSimpleData
import com.eroom.domain.utils.statusBarColor
import com.eroom.domain.utils.toastLong
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

    override fun setAllView(list: ArrayList<UserSimpleData>) {
        binding.mygoalRecyclerview.apply{
            layoutManager = LinearLayoutManager(this@OngoingGoalActivity)
            adapter = OngoingGoalAdapter(list, saveChange)
        }
    }

    private val saveChange = { b:Boolean ->
        isChecked.set(b)
        if(b) binding.saveListBtn.animate().translationY(-300f).withLayer()
        else binding.saveListBtn.animate().translationY(300f).withLayer() }

    fun initView() {
        var presenter = OngoingGoalPresenter(this)
        presenter.getData()

        statusBarColor(this@OngoingGoalActivity, R.color.orgDefault)

        binding.goalDescLayout.goal_desc.apply {
            showButton = false
            showShadow = false
        }

    }

    fun moreClick(v: View){
        binding.goalDescLayout.goal_desc.toggle()
    }

    fun backClick(v: View){
        if(isChecked.get()!!){
            this.toastLong("변경된 리스트 내역을 저장하시겠어요?")
        }
        //else finish()
    }
}
