package com.eroom.erooja.feature.editgoal

import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        mAdapter = EditGoalAdapter(presenter.callback,
            arrayListOf("a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a"),
            this, editGoalBinding.switch1.isChecked, deleteList)
        val mCallback = EditGoalItemTouchHelperCallback(mAdapter)

        mItemTouchHelper = ItemTouchHelper(mCallback).apply { attachToRecyclerView(
            editGoalBinding.editGoalRecycler.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(this@EditGoalActivity)
            }
        ) }
        editGoalBinding.switch1.setOnCheckedChangeListener { _, isChecked ->
            mAdapter.toggleMode = isChecked
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onStartDrag(holder: EditGoalViewHolder) {
        mItemTouchHelper.startDrag(holder)
    }

    private val deleteList = { position: Int ->
        mAdapter.list.removeAt(position)
        mAdapter.notifyItemRemoved(position)
        mAdapter.notifyItemRangeChanged(position, mAdapter.itemCount)
    }
}
