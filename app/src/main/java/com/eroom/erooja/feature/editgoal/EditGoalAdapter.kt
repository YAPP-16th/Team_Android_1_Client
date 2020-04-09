package com.eroom.erooja.feature.editgoal

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.erooja.databinding.ItemEditGoalBinding
import java.util.*

class EditGoalAdapter(
    callback : DiffUtil.ItemCallback<String>,
    private val list: ArrayList<String>,
    private val startDragListener: OnStartDragListener
    ) : ListAdapter<String, RecyclerView.ViewHolder>(callback), EditGoalItemTouchHelperCallback.OnItemMoveListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemEditGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EditGoalViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EditGoalViewHolder).bind(list[position], startDragListener)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(list, fromPosition, toPosition)
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
        binding.itemAll.setOnTouchListener { v, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                startDragListener.onStartDrag(this)
            }
            return@setOnTouchListener false
        }
    }
}