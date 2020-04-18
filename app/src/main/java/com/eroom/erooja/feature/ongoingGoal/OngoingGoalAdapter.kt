package com.eroom.erooja.feature.ongoingGoal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.UserSimpleData
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.item_ongoing_goal_list.view.*

class OngoingGoalAdapter(val simpleData: ArrayList<UserSimpleData>):
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ongoing_goal_list, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int =simpleData.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(simpleData[position].index, simpleData[position].name, simpleData[position].like,
            simpleData[position].check1, simpleData[position].check2, simpleData[position].check3)
    }

}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(index:Int, name:String, number: Int, text1:String, text2:String, text3:String) {
        itemView.ongoing_detail_checkbox.text=text1
    }
}