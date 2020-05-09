package com.eroom.erooja.feature.editgoal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.erooja.databinding.ItemGoalListBinding


class AddTodoAdapter(private var goalList: ArrayList<String>, private val deleteClick: (Int) -> Unit) : RecyclerView.Adapter<AddTodoAdapter.ViewHolder>() {
    lateinit var itemGoalListBinding: ItemGoalListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        itemGoalListBinding = ItemGoalListBinding.inflate(inflater, parent, false)
        return ViewHolder(itemGoalListBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(goalList[position])
    }


    override fun getItemCount(): Int = goalList.size


    inner class ViewHolder(itemGoalListBinding: ItemGoalListBinding) :
        RecyclerView.ViewHolder(itemGoalListBinding.root) {
        fun bind(goalContent: String) {
            itemGoalListBinding.goalContentTextview.text = goalContent
            itemGoalListBinding.goalDelBtn.setOnClickListener { deleteClick(adapterPosition) }
        }
    }
}