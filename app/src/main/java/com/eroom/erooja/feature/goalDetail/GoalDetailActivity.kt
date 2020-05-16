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
import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.*
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalDetailsBinding
import com.eroom.erooja.feature.addDirectList.addMyTodoListPage.AddMyListActivity
import com.eroom.erooja.feature.joinOtherList.joinTodoListPage.JoinOtherListActivity
import com.eroom.erooja.feature.goalEdit.GoalEditActivity
import com.eroom.erooja.feature.otherList.OtherListActivity
import kotlinx.android.synthetic.main.activity_goal_details.view.*
import kotlinx.android.synthetic.main.include_completed_goal_desc.view.*
import org.koin.android.ext.android.get
import ru.rhanza.constraintexpandablelayout.State

class GoalDetailActivity: AppCompatActivity(), GoalDetailContract.View {
    lateinit var binding: ActivityGoalDetailsBinding
    lateinit var presenter: GoalDetailPresenter

    var description: ObservableField<String> = ObservableField("")
    var jobClass: ObservableField<String> = ObservableField("")
    lateinit var userTodoList : ArrayList<String>
    private var userUid = ""
    private var goalId: Long = -1

    private var isJoin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
    }

    fun initPresenter() {
        presenter = GoalDetailPresenter(this, get(), get(), get(), get(), get())
    }

    fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goal_details)
        binding.goalDetail = this@GoalDetailActivity
    }

    fun initView() {
        val intent = intent
        goalId = intent.getLongExtra(Consts.GOAL_ID, -1)
        presenter.getMinimalTodoList(goalId)

        statusBarColor(this@GoalDetailActivity, R.color.subLight3)
    }

    override fun onResume() {
        super.onResume()
        presenter.getData(goalId)
    }

    @SuppressLint("SetTextI18n")
    override fun setView(title: String, description: String, joinCount: Int, isDateFixed: Boolean, startDate: String, endDate: String) {
        binding.participantListText.text = binding.participantListText.text.toString() add "($joinCount)"
        binding.goalNameTxt.text = title
        binding.goalDateTxt.text =
            if(isDateFixed) startDate.toRealDateFormat() + "~" + endDate.toRealDateFormat()
            else "기간 설정 자유"
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
    }

    override fun setRecyclerView(todoList: ArrayList<MinimalTodoListContent>, isMine: Boolean, isJoined: Boolean) {
        isJoin = isJoined
        if(isJoin) {
            binding.addListBtn.visibility = View.GONE
        }
        binding.othersRecyclerview.apply{
            adapter = GoalDetailAdapter(presenter.getGoalContentCallback(), todoList, isJoin, click(), clickPlusBtn())
            layoutManager = LinearLayoutManager(context)
        }
        if (isMine) {
            binding.editImage.visibility = View.VISIBLE
        }
    }

    override fun setInterestedClassName(list: List<String>) {
        var result = ""
        for (string in list) {
            result += string add ", "
        }
        this.jobClass.set(result)
    }

    //Todo: 참여자 목록의 Todo list 상세보기 (카드뷰)
    //Todo: 현재 액티비티에서 AddMyListActivity 로 넘어갈 때 필요한 요소: GoalID, UID, NAME, DATE, GoalTITLE, DESC)
    private fun click() = { uid:String , nickname: String->
        val intent = Intent(this@GoalDetailActivity, OtherListActivity::class.java)
            .apply{
                putExtra(Consts.GOAL_ID, intent.getLongExtra(Consts.GOAL_ID, -1))
                putExtra(Consts.UID, uid)
                putExtra(Consts.NAME, nickname)
                putExtra(Consts.DATE, binding.goalDateTxt.text)
                putExtra(Consts.GOAL_TITLE, binding.goalNameTxt.text)
                putExtra(Consts.DESCRIPTION, description.get())
                putExtra(Consts.IS_FROM_MYPAGE_ONGOING_GOAL, isJoin)
                putExtra(Consts.IS_FROM_MYPAGE_ENDED_GOAL, isJoin)
            }
        startActivityForResult(intent, 4000)
    }

    //Todo: 카드뷰의 "+ 버튼"을 눌러 TodoList 에 참여하기
    private fun clickPlusBtn() = { uid:String ->
        presenter.getUserTodoList(uid, intent.getLongExtra(Consts.GOAL_ID, -1))
        userUid = uid
    }

    override fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>) {
        userTodoList = ArrayList()
        repeat(todoList.size){
            userTodoList.add(todoList[it].content)
        }
        joinOtherList(userUid)
    }


    //Todo: 현재 액티비티에서 JoinOtherListActivity 로 넘어갈 때 필요한 요소: GoalID, UID, NAME, DATE, GoalTITLE, DESC)
    private fun joinOtherList(uid: String){
        val intent = Intent(this@GoalDetailActivity, JoinOtherListActivity::class.java)
            .apply{
                putExtra(Consts.GOAL_ID, intent.getLongExtra(Consts.GOAL_ID, -1))
                putExtra(Consts.UID, uid)
                putExtra(Consts.DATE, binding.goalDateTxt.text)
                putExtra(Consts.GOAL_TITLE, binding.goalNameTxt.text)
                putExtra(Consts.DESCRIPTION, description.get())
                putExtra(Consts.USER_TODO_LIST, userTodoList)
            }
        startActivity(intent)
    }


    fun moreClick(v: View) {
        binding.goalDescLayout.goal_desc.onStateChangeListener =
            { oldState: State, newState: State ->
                when (newState) {
                    State.Expanded -> {
                        binding.moreBtn.background =
                            resources.getDrawable(R.drawable.ic_icon_small_arrow_top_white, null)
                    }
                    State.Collapsed -> {
                        binding.moreBtn.background =
                            resources.getDrawable(R.drawable.ic_icon_small_arrow_bottom_white, null)

                    }
                }
            }
        binding.goalDescLayout.goal_desc.toggle()
    }

    //Todo: "리스트 직접 추가하기" Button
    fun addNewList(){
        val intent = Intent(this@GoalDetailActivity, AddMyListActivity::class.java)
            .apply{
                putExtra(Consts.DATE, binding.goalDateTxt.text)
                putExtra(Consts.GOAL_TITLE, binding.goalNameTxt.text)
                putExtra(Consts.DESCRIPTION, description.get())
                putExtra(Consts.GOAL_ID,intent.getLongExtra(Consts.GOAL_ID, -1))
            }
        startActivity(intent)
    }

    fun navigateToEdit() {
        startActivity(Intent(this, GoalEditActivity::class.java).apply {
            putExtra(Consts.GOAL_TITLE, binding.goalNameTxt.text)
            putExtra(Consts.DESCRIPTION, description.get())
            putExtra(Consts.GOAL_ID, goalId)
        })
    }

    fun navigationToBack() {
        finish()
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }

}