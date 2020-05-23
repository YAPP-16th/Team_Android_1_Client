package com.eroom.erooja.feature.othersOngoingGoal

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.item_ongoing_goal_list.view.*

class OthersOngoingGoalAdapter(val todoList: ArrayList<MinimalTodoListDetail>, private val saveAnimate: (Boolean, Long) -> Unit):
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_ongoing_goal_list, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(todoList[position].content, todoList[position].isEnd, saveAnimate, todoList[position].id)
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(contentText: String, isEnd: Boolean, saveAnimate: (Boolean, Long) -> Unit, todoId: Long) {
        itemView.ongoing_detail_checkbox.text = contentText
        itemView.ongoing_detail_checkbox.apply {
            isChecked = isEnd
            if (isEnd) {
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                setTextColor(context.getColor(R.color.grey4))
                //setOnClickListener { saveAnimate(false, todoId) }
            } else {
                paintFlags = 0
                setTextColor(context.getColor(R.color.grey7))
                //setOnClickListener { saveAnimate(true, todoId) }
            }
        }
    }
}