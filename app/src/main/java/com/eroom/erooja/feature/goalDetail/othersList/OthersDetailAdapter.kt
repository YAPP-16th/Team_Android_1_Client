package com.eroom.erooja.feature.goalDetail.othersList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.UserSimpleData
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.goal_detail_list.view.*

//리스트뷰로 구현하기
class OthersDetailAdapter(val simpleData: UserSimpleData):
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.goal_detail_list, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int =1

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(simpleData.name, simpleData.percent,
            simpleData.check1, simpleData.check2, simpleData.check3)
    }


}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(name:String, percent: Int, check1:String, check2:String, check3:String){

        itemView.detail_checkbox.text=check1


    }
}