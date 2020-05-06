package com.eroom.erooja.feature.editgoal

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eroom.domain.utils.dpToPx
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityEditGoalBinding

class EditGoalActivity : AppCompatActivity(), EditGoalContract.View, EditGoalAdapter.OnStartDragListener {
    lateinit var presenter: EditGoalPresenter
    lateinit var editGoalBinding: ActivityEditGoalBinding
    lateinit var mEditGoalAdapter: EditGoalAdapter
    lateinit var mDeleteGoalAdapter: DeleteGoalAdapter
    lateinit var mItemEditTouchHelper: ItemTouchHelper
    lateinit var swipeController: SwipeController
    var list = mutableListOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l")

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
        editRecyclerInit()
        deleteRecyclerInit()

        editGoalBinding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editGoalBinding.editGoalRecycler.visibility = View.GONE
                editGoalBinding.deleteGoalRecycler.visibility = View.VISIBLE
            } else {
                editGoalBinding.editGoalRecycler.visibility = View.VISIBLE
                editGoalBinding.deleteGoalRecycler.visibility = View.GONE
            }
            mEditGoalAdapter.notifyDataSetChanged()
            mDeleteGoalAdapter.notifyDataSetChanged()
        }
    }

    private fun editRecyclerInit() {
        mEditGoalAdapter = EditGoalAdapter(
            this,
            this, this)
        val mCallback = EditGoalItemTouchHelperCallback(mEditGoalAdapter)
        mItemEditTouchHelper = ItemTouchHelper(mCallback)
        mItemEditTouchHelper.attachToRecyclerView(
            editGoalBinding.editGoalRecycler
        )

        editGoalBinding.editGoalRecycler.apply {
            adapter = mEditGoalAdapter
            layoutManager = LinearLayoutManager(this@EditGoalActivity)
        }
    }

    private fun deleteRecyclerInit() {
        mDeleteGoalAdapter = DeleteGoalAdapter(presenter.callback, this)
        swipeController = SwipeController(object : SwipeControllerActions() {
            override fun onRightClicked(position: Int) {
                list.removeAt(position)
                mDeleteGoalAdapter.notifyItemRemoved(position)
                mDeleteGoalAdapter.notifyItemRangeChanged(position, mDeleteGoalAdapter.itemCount)
                mEditGoalAdapter.notifyItemRemoved(position)
                mEditGoalAdapter.notifyItemRangeChanged(position, mEditGoalAdapter.itemCount)
            }
        }, dpToPx(this, 100f), dpToPx(this, 18f))
        editGoalBinding.deleteGoalRecycler.apply {
            adapter = mDeleteGoalAdapter
            layoutManager = LinearLayoutManager(this@EditGoalActivity)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    swipeController.onDraw(c)
                }
            })
        }
    }

    override fun onStartDrag(holder: EditGoalViewHolder) {
        //mItemEditTouchHelper.startDrag(holder)
    }
}
