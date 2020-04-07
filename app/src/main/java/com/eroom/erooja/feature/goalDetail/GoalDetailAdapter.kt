package com.eroom.erooja.feature.goalDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.UserSimpleData
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.goal_simple_list.view.*

class GoalDetailAdapter(val simpleData: UserSimpleData, val click: (View) -> Unit):
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.goal_simple_list, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int =1

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(simpleData.name, simpleData.like,
            simpleData.check1, simpleData.check2, simpleData.check3, click)
    }


}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(name:String, number: Int, check1:String, check2:String, check3:String, click: (View) -> Unit ){
        itemView.username_list.text = "${name}의 리스트"
        itemView.putin_number_txt.text = "${number}명이 담아감"

        itemView.checkbox1.text=check1
        itemView.checkbox2.text=check2
        itemView.checkbox3.text=check3

        itemView.setOnClickListener{ click(itemView.gradient_view)}

    }
}