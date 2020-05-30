package com.eroom.erooja.app.feature.editgoal

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemDeleteGoalBinding

class DeleteGoalAdapter(
    callback: DiffUtil.ItemCallback<String>,
    val activity: EditGoalActivity,
    private val deleteClicked: (Int, Boolean) -> Unit
) : ListAdapter<String, RecyclerView.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemDeleteGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeleteGoalViewHolder(binding)
    }

    override fun getItemCount(): Int = activity.editTodoList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DeleteGoalViewHolder).bind(activity.editTodoList[position].content)
        if (activity.editTodoList[position].isEnd) {
            holder.binding.dot.imageTintList =
                ColorStateList.valueOf(activity.resources.getColor(R.color.grey4, null))
            holder.binding.contentText.apply {
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                setTextColor(context.getColor(R.color.grey4))
            }
            holder.binding.completedDetailCheckbox.isActivated = false
            holder.binding.completedDetailCheckbox.isClickable = false
            holder.binding.completedDetailCheckbox.buttonDrawable =
                activity.resources.getDrawable(R.drawable.ic_icon_check_btn_inactive, null)
        }
    }

    inner class DeleteGoalViewHolder(val binding: ItemDeleteGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bind(text: String) {
            binding.contentText.text = text
            binding.completedDetailCheckbox.isChecked = false
            binding.completedDetailCheckbox.setOnClickListener {
                deleteClicked(adapterPosition, (it as CheckBox).isChecked)
            }
        }
    }
}