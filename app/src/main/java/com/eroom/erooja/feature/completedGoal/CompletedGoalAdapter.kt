package com.eroom.erooja.feature.completedGoal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.UserSimpleData
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.item_completed_goal_list.view.*

class OthersDetailAdapter(private val simpleDataDetail: UserSimpleData):
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_completed_goal_list, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(simpleDataDetail.check1)
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(text:String){
        itemView.completed_detail_checkbox.text=text
    }
}