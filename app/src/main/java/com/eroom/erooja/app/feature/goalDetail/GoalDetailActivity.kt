package com.eroom.erooja.app.feature.goalDetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.GoalType
import com.eroom.data.entity.MinimalTodoListContent
import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.data.response.GoalDetailResponse
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.*
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalDetailsBinding
import com.eroom.erooja.app.dialog.EroojaDialogActivity
import com.eroom.erooja.app.feature.addDirectList.addMyTodoListPage.AddMyListActivity
import com.eroom.erooja.app.feature.joinOtherList.joinTodoListPage.JoinOtherListActivity
import com.eroom.erooja.app.feature.goalEdit.GoalEditActivity
import com.eroom.erooja.app.feature.otherList.OtherListActivity
import kotlinx.android.synthetic.main.include_completed_goal_desc.view.goal_desc
import org.koin.android.ext.android.get
import ru.rhanza.constraintexpandablelayout.State

class GoalDetailActivity : AppCompatActivity(), GoalDetailContract.View {
    lateinit var binding: ActivityGoalDetailsBinding
    lateinit var presenter: GoalDetailPresenter

    var description: ObservableField<String> = ObservableField("")
    lateinit var userTodoList: ArrayList<String>
    private var userUid = ""
    private var goalId: Long = -1

    private var isJoin: Boolean = false
    var onlyOneLine: ObservableField<Boolean> = ObservableField(false)

    private var isExistedInMyPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        // initView()
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
        initView()
        startAnimation()
        presenter.getData(goalId)
    }

    //ongoing_text : desc
    //keyword_text : keyword
    override fun setInitialView(list: GoalDetailResponse) {
        binding.participantListText.text = "참여자 리스트" add "(${list.joinCount})"
        binding.goalNameTxt.text = list.title
        binding.goalDateTxt.text =
            if (list.isDateFixed) list.startDt.toRealDateFormat() + "~" + list.endDt.toRealDateFormat()
            else "기간 설정 자유"
        binding.include.ongoingDescText.text = list.description


        if (list.description.isEmpty()) {
            binding.goalDescLayout.goal_desc.invalidateState(State.Statical)
            binding.moreBtn.visibility = View.INVISIBLE
            updateView()
        } else {
            binding.include.ongoingDescText.post {
                binding.include.goalDesc.collapsedHeight = binding.include.ongoingDescText.height
                binding.include.ongoingDescText.maxLines = Int.MAX_VALUE
            }
        }

        binding.include.keywordTxt.text =
            list.jobInterests.mapIndexed { index: Int, goalType: GoalType ->
                if (index == list.jobInterests.size - 1) goalType.name else goalType.name add ", "
            }.toList().join()

        binding.include.goneKeywordTxt.text = binding.include.keywordTxt.text


    }

    private fun updateView() {
        onlyOneLine.set(true)
    }


    override fun setRecyclerView(
        todoList: ArrayList<MinimalTodoListContent>,
        isMine: Boolean,
        isJoined: Boolean
    ) {
        isJoin = isJoined
        if (isJoin) {
            binding.addListBtn.visibility = View.GONE
        }
        binding.othersRecyclerview.apply {
            adapter = GoalDetailAdapter(
                presenter.getGoalContentCallback(),
                todoList,
                isJoin,
                click(),
                clickPlusBtn()
            )
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
        binding.include.ongoingDescText.text = result
    }

    //Todo: 참여자 목록의 Todo list 상세보기 (카드뷰)
    //Todo: 현재 액티비티에서 AddMyListActivity 로 넘어갈 때 필요한 요소: GoalID, UID, NAME, DATE, GoalTITLE, DESC)
    private fun click() = { uid: String, nickname: String ->
        val intent = Intent(this@GoalDetailActivity, OtherListActivity::class.java)
            .apply {
                //addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(Consts.GOAL_ID, intent.getLongExtra(Consts.GOAL_ID, -1))
                putExtra(Consts.UID, uid)
                putExtra(Consts.NAME, nickname)
                putExtra(Consts.DATE, binding.goalDateTxt.text)
                putExtra(Consts.GOAL_TITLE, binding.goalNameTxt.text)
                putExtra(Consts.DESCRIPTION, binding.include.ongoingDescText.text)
                putExtra(Consts.IS_FROM_MYPAGE_ONGOING_GOAL, isJoin)
                putExtra(Consts.IS_EXISTED_IN_MY_PAGE, isExistedInMyPage)
            }
        startActivityForResult(intent, 4000)
    }

    //Todo: 카드뷰의 "+ 버튼"을 눌러 TodoList 에 참여하기
    private fun clickPlusBtn() = { uid: String ->
        showAlert()
        userUid = uid
    }

    private fun showAlert() {
        startActivityForResult(
            Intent(
                this,
                EroojaDialogActivity::class.java
            ).apply {
                putExtra(Consts.DIALOG_TITLE, "")
                putExtra(
                    Consts.DIALOG_CONTENT,
                    if (isExistedInMyPage) "이미 목표에 참여한 이력이 존재합니다. 참여 시 해당 이력이 삭제될 수 있습니다." else "이 리스트에 참여하시겠어요?"
                )
                putExtra(Consts.DIALOG_CONFIRM, true)
                putExtra(Consts.DIALOG_CANCEL, true)
            }, 4000
        )
    }

    private fun showDirectAddAlert() {
        startActivityForResult(
            Intent(
                this,
                EroojaDialogActivity::class.java
            ).apply {
                putExtra(Consts.DIALOG_TITLE, "")
                putExtra(
                    Consts.DIALOG_CONTENT,
                    if (isExistedInMyPage) "이미 목표에 참여한 이력이 존재합니다. 참여 시 해당 이력이 삭제될 수 있습니다." else "이 리스트에 참여하시겠어요?"
                )
                putExtra(Consts.DIALOG_CONFIRM, true)
                putExtra(Consts.DIALOG_CANCEL, true)
            }, 5000
        )
    }

    override fun setIsExistedInMyPage(isExistedInMyPage: Boolean) {
        this.isExistedInMyPage = isExistedInMyPage
    }


    override fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>) {
        userTodoList = ArrayList()
        repeat(todoList.size) {
            userTodoList.add(todoList[it].content)
        }
        joinOtherList(userUid)
    }


    //Todo: 현재 액티비티에서 JoinOtherListActivity 로 넘어갈 때 필요한 요소: GoalID, UID, NAME, DATE, GoalTITLE, DESC)
    private fun joinOtherList(uid: String) {
        val intent = Intent(this@GoalDetailActivity, JoinOtherListActivity::class.java)
            .apply {
                //addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(Consts.GOAL_ID, intent.getLongExtra(Consts.GOAL_ID, -1))
                putExtra(Consts.UID, uid)
                putExtra(Consts.DATE, binding.goalDateTxt.text)
                putExtra(Consts.GOAL_TITLE, binding.goalNameTxt.text)
                putExtra(Consts.DESCRIPTION, binding.include.ongoingDescText.text)
                putExtra(Consts.USER_TODO_LIST, userTodoList)
            }
        startActivity(intent)
    }


    fun moreClick(v: View) {
        binding.include.goalDesc.onStateChangeListener =
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
    fun addNewList() {
        if (isExistedInMyPage) {
            showDirectAddAlert()
        } else {
            val intent = Intent(this@GoalDetailActivity, AddMyListActivity::class.java)
                .apply {
                    //addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    putExtra(Consts.DATE, binding.goalDateTxt.text)
                    putExtra(Consts.GOAL_TITLE, binding.goalNameTxt.text)
                    putExtra(Consts.DESCRIPTION, binding.include.ongoingDescText.text)
                    putExtra(Consts.GOAL_ID, intent.getLongExtra(Consts.GOAL_ID, -1))
                }
            startActivity(intent)
        }
    }

    fun navigateToEdit() {
        startActivity(Intent(this, GoalEditActivity::class.java).apply {
            putExtra(Consts.GOAL_TITLE, binding.goalNameTxt.text)
            putExtra(Consts.DESCRIPTION, description.get())
            putExtra(Consts.GOAL_ID, goalId)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = data?.getBooleanExtra(Consts.DIALOG_RESULT, false) //확인 or 취소

        if (requestCode == 4000 && resultCode == 6000) {
            if (result!!) {
                presenter.getUserTodoList(userUid, intent.getLongExtra(Consts.GOAL_ID, -1))
            }
        } else if (requestCode == 5000 && resultCode == 6000) {
            if (result!!) {
                val intent = Intent(this@GoalDetailActivity, AddMyListActivity::class.java)
                    .apply {
                        putExtra(Consts.DATE, binding.goalDateTxt.text)
                        putExtra(Consts.GOAL_TITLE, binding.goalNameTxt.text)
                        putExtra(Consts.DESCRIPTION, binding.include.ongoingDescText.text)
                        putExtra(Consts.GOAL_ID, intent.getLongExtra(Consts.GOAL_ID, -1))
                    }
                startActivity(intent)
            }
        }
    }

    fun navigationToBack() {
        finish()
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }

    fun startBlockAnimation() {
        binding.colorLoading.visibility = View.GONE
        binding.blockView.visibility = View.VISIBLE
        binding.whiteLoading.visibility = View.VISIBLE
        binding.colorLoading.cancelAnimation()
        binding.whiteLoading.playAnimation()
    }

    fun startAnimation() {
        binding.blockView.visibility = View.GONE
        binding.whiteLoading.visibility = View.GONE
        binding.colorLoading.visibility = View.VISIBLE
        binding.whiteLoading.cancelAnimation()
        binding.colorLoading.playAnimation()
    }

    override fun stopAnimation() {
        binding.blockView.visibility = View.GONE
        binding.whiteLoading.visibility = View.GONE
        binding.colorLoading.visibility = View.GONE
        binding.whiteLoading.cancelAnimation()
        binding.colorLoading.cancelAnimation()
    }
}