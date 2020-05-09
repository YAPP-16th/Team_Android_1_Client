package com.eroom.erooja.feature.editgoal

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemDeleteGoalBinding
import com.eroom.erooja.databinding.ItemEditGoalBinding

class DeleteGoalAdapter (
    callback : DiffUtil.ItemCallback<String>,
    val activity: EditGoalActivity
) : ListAdapter<String, RecyclerView.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemDeleteGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeleteGoalViewHolder(binding)
    }

    override fun getItemCount(): Int = activity.list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DeleteGoalViewHolder).bind(activity.list[position])
    }
}

class DeleteGoalViewHolder(val binding: ItemDeleteGoalBinding): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("ClickableViewAccessibility")
    fun bind(text: String) {
        binding.contentText.text = text
    }
}