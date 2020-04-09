package com.eroom.erooja.feature.editgoal

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.erooja.databinding.ItemEditGoalBinding
import timber.log.Timber
import java.util.*

class EditGoalAdapter(
    callback : DiffUtil.ItemCallback<String>,
    val list: ArrayList<String>,
    private val startDragListener: OnStartDragListener,
    var toggleMode: Boolean,
    private val deleted: (Int) -> Unit
    ) : ListAdapter<String, RecyclerView.ViewHolder>(callback), EditGoalItemTouchHelperCallback.OnItemMoveListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemEditGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EditGoalViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (toggleMode)
            (holder as EditGoalViewHolder).bindDelete(list[position], deleted)
        else
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

    fun bindDelete(text: String, deleted: (Int) -> Unit) {
        binding.sampleText.text = text
        binding.trigger.apply {
            setOnClickListener {
                deleted(adapterPosition)
            }
            visibility = View.VISIBLE
        }
    }
}