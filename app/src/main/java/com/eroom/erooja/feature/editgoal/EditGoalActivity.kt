package com.eroom.erooja.feature.editgoal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityEditGoalBinding

class EditGoalActivity : AppCompatActivity(), EditGoalContract.View, EditGoalAdapter.OnStartDragListener {
    lateinit var presenter: EditGoalPresenter
    lateinit var editGoalBinding: ActivityEditGoalBinding
    lateinit var mAdapter: EditGoalAdapter
    lateinit var mItemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
    }

    private fun initPresenter() {
        presenter = EditGoalPresenter(this)
    }

    private fun setUpDataBinding() {
        editGoalBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_goal)
        editGoalBinding.activity = this
    }

    private fun initView() {
        mAdapter = EditGoalAdapter(presenter.callback, arrayListOf("a", "b", "c"), this)
        val mCallback = EditGoalItemTouchHelperCallback(mAdapter)
        mItemTouchHelper = ItemTouchHelper(mCallback).apply { attachToRecyclerView(
            editGoalBinding.editGoalRecycler.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(this@EditGoalActivity)
            }
        ) }
    }

    override fun onStartDrag(holder: EditGoalViewHolder) {
        mItemTouchHelper.startDrag(holder)
    }
}
