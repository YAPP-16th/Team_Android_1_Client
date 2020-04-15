package com.eroom.erooja.feature.goalDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.UserSimpleData
import com.eroom.domain.utils.add
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.goal_simple_list.view.*

class GoalDetailAdapter(val simpleData: ArrayList<UserSimpleData>, val click: (View, Int) -> Unit):
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.goal_simple_list, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int =simpleData.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(simpleData[position].index, simpleData[position].name, simpleData[position].like,
            simpleData[position].check1, simpleData[position].check2, simpleData[position].check3, click)
    }


}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(index:Int, name:String, number: Int, text1:String, text2:String, text3:String, click: (View, Int) -> Unit ){
        itemView.username_list.text = name add " 님의 리스트"
        itemView.putin_number_txt.text = number.toString() add "명이 선택"

        itemView.text1.text=text1
        itemView.text2.text=text2
        itemView.text3.text=text3

        itemView.setOnClickListener{ click(itemView.gradient_view, index)}

    }
}