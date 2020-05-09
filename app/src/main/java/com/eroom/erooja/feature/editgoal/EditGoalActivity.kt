package com.eroom.erooja.feature.editgoal

import android.graphics.Canvas
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
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
    var list = mutableListOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l")

    private var mMode: EditMode = EditMode.EDIT

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
        imageListenerInit()
    }

    private fun editRecyclerInit() {
        mEditGoalAdapter = EditGoalAdapter(this, this, this)

        editGoalBinding.editGoalRecycler.apply {
            layoutManager = LinearLayoutManager(this@EditGoalActivity)
        }

        val mCallback = EditGoalItemTouchHelperCallback(mEditGoalAdapter)
        mItemEditTouchHelper = ItemTouchHelper(mCallback)

        mItemEditTouchHelper.attachToRecyclerView(null)

        editGoalBinding.editGoalRecycler.adapter = mEditGoalAdapter
    }

    private fun deleteRecyclerInit() {
        mDeleteGoalAdapter = DeleteGoalAdapter(presenter.callback, this)
        editGoalBinding.deleteGoalRecycler.apply {
            adapter = mDeleteGoalAdapter
            layoutManager = LinearLayoutManager(this@EditGoalActivity)
        }
    }

    fun attachRecyclerView() {
        mItemEditTouchHelper.attachToRecyclerView(editGoalBinding.editGoalRecycler)
    }

    fun detachRecyclerView() {
        mItemEditTouchHelper.attachToRecyclerView(null)
    }

    override fun onStartDrag(holder: EditGoalViewHolder) {
        mItemEditTouchHelper.startDrag(holder)
    }

    private fun imageListenerInit() {
        editGoalBinding.deleteImage.setOnClickListener {
            when (mMode) {
                EditMode.DELETE -> {
                    mMode = EditMode.EDIT
                    (it as ImageView).setImageDrawable(getDrawable(R.drawable.ic_icon_navi_trash))
                    editGoalBinding.plusImage.setImageDrawable(getDrawable(R.drawable.ic_icon_navi_plus_normal))
                    editGoalBinding.editGoalRecycler.visibility = View.VISIBLE
                    editGoalBinding.deleteGoalRecycler.visibility = View.GONE
                }
                EditMode.ADD -> {

                }
                EditMode.EDIT -> {
                    mMode = EditMode.DELETE
                    (it as ImageView).setImageDrawable(getDrawable(R.drawable.ic_icon_check_active))
                    editGoalBinding.plusImage.setImageDrawable(getDrawable(R.drawable.ic_icon_navi_plus_inactive))
                    editGoalBinding.editGoalRecycler.visibility = View.GONE
                    editGoalBinding.deleteGoalRecycler.visibility = View.VISIBLE
                }
            }
            mEditGoalAdapter.notifyDataSetChanged()
            mDeleteGoalAdapter.notifyDataSetChanged()
        }
        editGoalBinding.plusImage.setOnClickListener {
            when (mMode) {
                EditMode.DELETE -> {

                }
                EditMode.ADD -> {

                }
                EditMode.EDIT -> {

                }
            }
            mEditGoalAdapter.notifyDataSetChanged()
            mDeleteGoalAdapter.notifyDataSetChanged()
        }
    }
}
