package com.eroom.erooja.feature.editgoal

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.erooja.databinding.ItemEditGoalBinding
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class EditGoalAdapter(
    callback : DiffUtil.ItemCallback<String>,
    var activity: EditGoalActivity,
    private val startDragListener: OnStartDragListener
    ) : ListAdapter<String, RecyclerView.ViewHolder>(callback), EditGoalItemTouchHelperCallback.OnItemMoveListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemEditGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EditGoalViewHolder(binding)
    }

    override fun getItemCount(): Int = activity.list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EditGoalViewHolder).bind(activity.list[position], startDragListener)
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

class EditGoalViewHolder(val binding: ItemEditGoalBinding): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("ClickableViewAccessibility")
    fun bind(text: String, startDragListener: EditGoalAdapter.OnStartDragListener) {
        binding.sampleText.text = text
        binding.trigger.visibility = View.GONE
        binding.itemAll.setOnLongClickListener {
            it.setOnTouchListener { v, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    startDragListener.onStartDrag(this)
                }
                return@setOnTouchListener false
            }
            it.setOnTouchListener { v, event -> return@setOnTouchListener false }
            return@setOnLongClickListener true
        }
    }
}