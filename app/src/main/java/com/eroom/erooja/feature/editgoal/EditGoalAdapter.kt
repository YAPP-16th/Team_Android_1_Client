package com.eroom.erooja.feature.editgoal

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.domain.utils.loadDrawable
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemEditGoalBinding
import com.eroom.erooja.databinding.ItemGoalListBinding
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class EditGoalAdapter(
    var activity: EditGoalActivity,
    private val startDragListener: OnStartDragListener,
    private val context: Context
    ) : RecyclerView.Adapter<EditGoalViewHolder>(), EditGoalItemTouchHelperCallback.OnItemMoveListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditGoalViewHolder {
        val binding = ItemEditGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EditGoalViewHolder(binding)
    }

    override fun getItemCount(): Int = activity.list.size

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: EditGoalViewHolder, position: Int) {
        holder.binding.contentText.text = activity.list[position]
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
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(activity.list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    interface OnStartDragListener {
        fun onStartDrag(holder: EditGoalViewHolder)
    }
}

class EditGoalViewHolder(val binding: ItemEditGoalBinding): RecyclerView.ViewHolder(binding.root)