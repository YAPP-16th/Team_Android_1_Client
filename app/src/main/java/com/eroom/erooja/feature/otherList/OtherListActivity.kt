package com.eroom.erooja.feature.otherList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.*
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityOthersListBinding
import com.eroom.erooja.feature.addMyGoalJoin.AddMyListActivity
import com.kakao.kakaostory.StringSet.description
import org.koin.android.ext.android.get
import timber.log.Timber

class OtherListActivity : AppCompatActivity(),
    OtherListContract.View {
    lateinit var binding: ActivityOthersListBinding
    lateinit var presenter: OtherListPresenter
    private var userTodoList = ArrayList<String>()
    private var userUid = ""
    private var goalId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_others_list)
        binding.othersDetail = this@OtherListActivity
    }

    override fun setAllView(todoList: ArrayList<MinimalTodoListDetail>) {
        binding.othersDetailRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@OtherListActivity)
            adapter = OthersDetailAdapter(todoList)
        }

        repeat(todoList.size) {
            userTodoList.add(todoList[it].content)
        }
    }

    fun initView() {
        if (intent.getBooleanExtra(Consts.IS_FROM_MYPAGE_ONGOING_GOAL, false)
            || intent.getBooleanExtra(Consts.IS_FROM_MYPAGE_ENDED_GOAL, false)
        ) {
            binding.savelistBtn.visibility = View.INVISIBLE
        }
        userUid = intent.getStringExtra(Consts.UID) ?: ""
        goalId = intent.getLongExtra(Consts.GOAL_ID, -1)
        presenter = OtherListPresenter(this, get(), get())
        presenter.getData(userUid, goalId)
        presenter.getProfileImage(userUid)
        binding.usernameList.text = intent.getStringExtra(Consts.NAME)
        binding.goalDateTxt.text = intent.getStringExtra(Consts.DATE)

        statusBarColor(this@OtherListActivity, R.color.subLight3)
    }

    fun addTodoListBtn() {
        val intent = Intent(this@OtherListActivity, AddMyListActivity::class.java)
            .apply {
                putExtra(Consts.GOAL_ID, intent.getLongExtra(Consts.GOAL_ID, -1))
                putExtra(Consts.UID, userUid)
                putExtra(
                    Consts.GOAL_DETAIL_REQUEST_verOTHER,
                    Consts.GOAL_DETAIL_REQUEST_NUM_verOTHER
                )
                putExtra(Consts.DATE, binding.goalDateTxt.text)
                putExtra(Consts.GOAL_TITLE, intent.getStringExtra(Consts.GOAL_TITLE))
                putExtra(Consts.DESCRIPTION, intent.getStringExtra(Consts.DESCRIPTION))
                putExtra(Consts.USER_TODO_LIST, userTodoList)

            }
        startActivity(intent)
    }

    override fun setProfileImage(imagePath: String?) {
        imagePath?.let {
            binding.circleImageView.loadUrl(it)
            this.toastShort(it)
            Timber.e(it)
        }
            ?: run {
                binding.circleImageView.loadDrawable(
                    resources.getDrawable(
                        R.drawable.ic_icon_users_blank,
                        null
                    )
                )
            }
    }

    fun back(v: View) {
        finish()
    }

}