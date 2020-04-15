package com.eroom.erooja.feature.addGoal.newGoalPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.erooja.databinding.ItemGoalListBinding


class GoalAdapter(
    //private var goalList: MutableLiveData<List<String>>
    private var goalList: ArrayList<String>
) : RecyclerView.Adapter<GoalAdapter.ViewHolder>() {
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
        }
    }
}
