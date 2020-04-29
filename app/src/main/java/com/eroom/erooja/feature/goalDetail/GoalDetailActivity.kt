package com.eroom.erooja.feature.goalDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.UserSimpleData
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.add
import com.eroom.domain.utils.statusBarColor
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalDetailsBinding
import com.eroom.erooja.feature.otherList.OtherListActivity
import kotlinx.android.synthetic.main.goal_simple_list.view.*
import kotlinx.android.synthetic.main.include_completed_goal_desc.view.*
import org.koin.android.ext.android.get

class GoalDetailActivity: AppCompatActivity(), GoalDetailContract.View {
    lateinit var binding: ActivityGoalDetailsBinding
    lateinit var presenter: GoalDetailPresenter

    val description: ObservableField<String> = ObservableField("")
    val jobClass: ObservableField<String> = ObservableField("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
    }

    fun initPresenter() {
        presenter = GoalDetailPresenter(this, get(), get())
    }

    fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goal_details)
        binding.goalDetail = this@GoalDetailActivity
    }

    fun initView() {
        val intent = intent
        presenter.getData(intent.getLongExtra(Consts.GOAL_ID, -1))
        statusBarColor(this@GoalDetailActivity, R.color.subLight3)

        binding.goalDescLayout.goal_desc.apply{
            showButton = false
            showShadow = false
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setView(title: String, description: String, joinCount: Int, startDate: String, endDate: String) {
        binding.participantListText.text = binding.participantListText.text.toString() add "($joinCount)"
        binding.goalNameTxt.text = title
        this.description.set(description)
        //presenter.getInterestedClassName(interestIdList)
    }

    override fun setInterestedClassName(list: List<String>) {
        var result = ""
        for (string in list) {
            result += string add ", "
        }
        this.jobClass.set(result)
    }

    private fun click() = {  _:View, index:Int ->
        val intent = Intent(this@GoalDetailActivity, OtherListActivity::class.java)
            .apply{
                putExtra(Consts.INDEX, index)
                putExtra(Consts.NAME, binding.othersRecyclerview.username_list.text)
                putExtra(Consts.DATE, binding.goalDateTxt.text)
            }
        startActivityForResult(intent, 4000)
    }

    fun moreClick(v: View) {
        binding.goalDescLayout.goal_desc.toggle()
    }

    fun othersDetailClick(v: View) {
        val intent= Intent(this@GoalDetailActivity, OtherListActivity::class.java)
        startActivity(intent)
    }

    fun navigationToBack() {
        finish()
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}