package com.eroom.erooja.app.feature.otherList

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.item_completed_goal_list.view.*

class OthersDetailAdapter(private val TodoList: ArrayList<MinimalTodoListDetail>) :
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_completed_goal_list, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int = TodoList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(TodoList[position].content, TodoList[position].isEnd)
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(contentText: String, isEnd: Boolean) {
        itemView.completed_detail_checkbox.text = contentText
        itemView.completed_detail_checkbox.apply {
            isChecked = isEnd
            if (isEnd) {
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                setTextColor(context.getColor(R.color.grey4))
            } else {
                paintFlags = 0
                setTextColor(context.getColor(R.color.grey7))
            }
        }
    }
}