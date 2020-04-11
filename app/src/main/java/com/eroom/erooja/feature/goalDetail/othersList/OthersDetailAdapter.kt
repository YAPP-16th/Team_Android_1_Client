package com.eroom.erooja.feature.goalDetail.othersList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.UserSimpleData
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.goal_detail_list.view.*

class OthersDetailAdapter(val simpleData: UserSimpleData):
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.goal_detail_list, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int =1

    //하나의 클래스에서 데이터를 다 불러올 수 있는 방법 찾아보기
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(simpleData.check1)
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(check:String){

        itemView.detail_checkbox.text=check
    }
}