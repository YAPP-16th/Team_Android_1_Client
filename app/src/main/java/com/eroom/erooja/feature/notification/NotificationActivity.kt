package com.eroom.erooja.feature.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.AlarmContent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityNotificationBinding
import com.eroom.erooja.feature.endedGoal.EndedGoalActivity
import com.eroom.erooja.singleton.UserInfo
import org.koin.android.ext.android.get

class NotificationActivity : AppCompatActivity(), NotificationContract.View {
    private lateinit var presenter: NotificationPresenter
    private lateinit var binding: ActivityNotificationBinding

    private lateinit var mAdapter: NotificationAdapter
    private val contentList: ArrayList<AlarmContent> = ArrayList()
    private var mPage = 0
    private var isEnd = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
        setUpDataBinding()
        initView()
    }

    private fun initPresenter() {
        presenter = NotificationPresenter(this, get(), get())
    }

    private fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        binding.activity = this
    }

    private fun initView() {
        binding.refresh.setOnRefreshListener {
            mPage = 0
            isEnd = false
            contentList.clear()
            requestInfo()
        }
    }

    override fun onResume() {
        super.onResume()
        requestInfo()
    }

    private fun requestInfo() {
        presenter.requestAlarms(mPage)
    }

    override fun loadAlarms(list: ArrayList<AlarmContent>) {
        contentList.addAll(list)
        mPage += 1
        binding.alarmRecycler.visibility = View.VISIBLE
        if (!::mAdapter.isInitialized) {
            mAdapter = NotificationAdapter(presenter.mAlarmDiffCallback, contentList, this, gotoAlarm)
        } else {
            mAdapter.submitList(contentList)
            mAdapter.notifyItemRangeChanged(0, contentList.size)
        }
        binding.refresh.isRefreshing = false
    }

    override fun nonAlarmList() {
        binding.networkErrorLayout.visibility = View.INVISIBLE
        binding.alarmRecycler.visibility = View.INVISIBLE
    }

    override fun networkError() {
        binding.networkErrorLayout.visibility = View.VISIBLE
        binding.alarmRecycler.visibility = View.INVISIBLE
        binding.refresh.isRefreshing = false
    }

    override fun setIsEnd() {
        isEnd = true
    }

    val recyclerViewScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy >= 0 && contentList.size > 0) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == contentList.size - 1 && !isEnd) {
                    presenter.requestAlarms(mPage)
                }
            }
        }
    }

    private val gotoAlarm = { goalId: Long? ->
        if (goalId != null) startActivity(Intent(this, EndedGoalActivity::class.java).apply {
            putExtra(Consts.GOAL_ID, goalId)
            putExtra(Consts.UID, UserInfo.myUId)
        })
        else this.toastShort("알 수 없는 에러입니다")
    }
}
