package com.eroom.erooja.feature.editgoal

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.domain.utils.loadDrawable
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemDeleteGoalBinding

class DeleteGoalAdapter (
    callback : DiffUtil.ItemCallback<String>,
    val activity: EditGoalActivity,
    private val deleteClicked: (Int, Boolean) -> Unit
) : ListAdapter<String, RecyclerView.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemDeleteGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeleteGoalViewHolder(binding)
    }

    override fun getItemCount(): Int = activity.editList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DeleteGoalViewHolder).bind(activity.editList[position])
    }

    inner class DeleteGoalViewHolder(val binding: ItemDeleteGoalBinding): RecyclerView.ViewHolder(binding.root) {
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