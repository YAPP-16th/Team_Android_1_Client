package com.eroom.erooja.feature.othersEndedGoal

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.item_ended_goal_list.view.*
import kotlinx.android.synthetic.main.item_ongoing_goal_list.view.*

class OthersEndedGoalAdapter(val todoList: ArrayList<MinimalTodoListDetail>):
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_ended_goal_list, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(todoList[position].content, todoList[position].isEnd)
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(contentText: String, isEnd: Boolean) {
        itemView.ended_detail_checkbox.text = contentText
        itemView.ended_detail_checkbox.apply {
            isChecked = isEnd
            if (isEnd) {
                setButtonDrawable(R.drawable.ic_icon_check_btn_disabled)
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                setTextColor(context.getColor(R.color.grey4))
            } else {
                paintFlags = 0
                setTextColor(context.getColor(R.color.grey7))
            }
        }
    }
}
