package com.eroom.erooja.feature.editgoal

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemEditGoalBinding
import java.util.*

class EditGoalAdapter(
    var activity: EditGoalActivity,
    private val startDragListener: OnStartDragListener,
    private val context: Context,
    private val isMovable: Boolean
    ) : RecyclerView.Adapter<EditGoalViewHolder>(), EditGoalItemTouchHelperCallback.OnItemMoveListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditGoalViewHolder {
        val binding = ItemEditGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EditGoalViewHolder(binding)
    }

    override fun getItemCount(): Int = activity.editTodoList.size

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: EditGoalViewHolder, position: Int) {
        holder.binding.trigger.visibility = if (isMovable) View.VISIBLE else View.GONE
        holder.binding.contentText.text = activity.editTodoList[position].content
        holder.binding.trigger.setOnTouchListener { v, event ->
            activity.attachRecyclerView()
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                startDragListener.onStartDrag(holder)
            }
            return@setOnTouchListener false
        }
        holder.binding.contentText.setOnTouchListener { v, event ->
            activity.detachRecyclerView()
            return@setOnTouchListener false
        }
        holder.binding.dot.setOnTouchListener { v, event ->
            activity.detachRecyclerView()
            return@setOnTouchListener false
        }
        if (activity.editTodoList[position].isEnd) {
            holder.binding.dot.imageTintList = ColorStateList.valueOf(context.resources.getColor(R.color.grey4, null))
            holder.binding.contentText.apply {
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                setTextColor(context.getColor(R.color.grey4))
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(activity.editTodoList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        activity.editTodoListObserver.value = activity.editTodoList
        return true
    }

    interface OnStartDragListener {
        fun onStartDrag(holder: EditGoalViewHolder)
    }
}

class EditGoalViewHolder(val binding: ItemEditGoalBinding): RecyclerView.ViewHolder(binding.root)