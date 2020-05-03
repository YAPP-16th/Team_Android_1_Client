package com.eroom.erooja.feature.goalDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.MinimalTodoListContent
import com.eroom.data.entity.UserSimpleData
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.add
import com.eroom.domain.utils.statusBarColor
import com.eroom.domain.utils.toRealDateFormat
import com.eroom.domain.utils.toastLong
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalDetailsBinding
import com.eroom.erooja.feature.otherList.OtherListActivity
import kotlinx.android.synthetic.main.activity_goal_details.view.*
import kotlinx.android.synthetic.main.goal_simple_list.view.*
import kotlinx.android.synthetic.main.include_completed_goal_desc.view.*
import org.koin.android.ext.android.get
import ru.rhanza.constraintexpandablelayout.State

class GoalDetailActivity: AppCompatActivity(), GoalDetailContract.View {
    lateinit var binding: ActivityGoalDetailsBinding
    lateinit var presenter: GoalDetailPresenter

    val description: ObservableField<String> = ObservableField("")
    val jobClass: ObservableField<String> = ObservableField("")

    val flag: ObservableField<Boolean> = ObservableField(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
    }

    fun initPresenter() {
        presenter = GoalDetailPresenter(this, get(), get(), get())
    }

    fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goal_details)
        binding.goalDetail = this@GoalDetailActivity
    }

    fun initView() {
        val intent = intent
        presenter.getData(intent.getLongExtra(Consts.GOAL_ID, -1))
        presenter.getMinimalTodoList(intent.getLongExtra(Consts.GOAL_ID, -1))
        statusBarColor(this@GoalDetailActivity, R.color.subLight3)
    }

    @SuppressLint("SetTextI18n")
    override fun setView(title: String, description: String, joinCount: Int, startDate: String, endDate: String) {
        binding.participantListText.text = binding.participantListText.text.toString() add "($joinCount)"
        binding.goalNameTxt.text = title
        binding.goalDateTxt.text = startDate.toRealDateFormat() + "~" + endDate.toRealDateFormat()
        this.description.set(description)

        if(description.isEmpty()) {
            binding.goalDescLayout.goal_desc.apply {
                invalidateState(State.Statical)
                binding.goalDescLayout.more_btn.visibility = View.INVISIBLE
            }
        }

        binding.goalDescLayout.goal_desc.apply{
            showButton = false
            showShadow = false
        }
        //presenter.getInterestedClassName(interestIdList)
    }

    override fun setRecyclerView(todoList: ArrayList<MinimalTodoListContent>) {
        binding.othersRecyclerview.apply{
            adapter = GoalDetailAdapter(presenter.getGoalContentCallback(), todoList, click())
            layoutManager = LinearLayoutManager(context)
        }


    }

    override fun setInterestedClassName(list: List<String>) {
        var result = ""
        for (string in list) {
            result += string add ", "
        }
        this.jobClass.set(result)
    }

    private fun click() = { uid:String ->
        val intent = Intent(this@GoalDetailActivity, OtherListActivity::class.java)
            .apply{
                putExtra(Consts.GOAL_ID, intent.getLongExtra(Consts.GOAL_ID, -1))
                putExtra(Consts.UID, uid)
                putExtra(Consts.NAME, binding.othersRecyclerview.username_list.text)
                putExtra(Consts.DATE, binding.goalDateTxt.text)
            }
        startActivityForResult(intent, 4000)
    }

    fun moreClick(v: View) {
        if(!flag.get()!!){
             binding.moreBtn.background = resources.getDrawable(R.drawable.ic_icon_small_arrow_top_white,null)
             flag.set(true)
        } else {
            binding.moreBtn.background = resources.getDrawable(R.drawable.ic_icon_small_arrow_right_gray,null)
             flag.set(false)
         }
        binding.goalDescLayout.goal_desc.toggle()
    }


    fun navigationToBack() {
        finish()
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}