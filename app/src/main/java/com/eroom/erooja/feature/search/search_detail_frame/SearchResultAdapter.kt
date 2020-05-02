package com.eroom.erooja.feature.search.search_detail_frame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.GoalContent
import com.eroom.domain.utils.toRealDateFormat
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.item_main_new_goal.view.*

class SearchResultAdapter (private val goalContent: ArrayList<GoalContent>, private val click: (Long) -> Unit):
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_new_goal, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int = goalContent.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(goalContent[position].title, goalContent[position].joinCount, goalContent[position].startDt,
            goalContent[position].endDt, goalContent[position].isDateFixed, click, goalContent[position].id)
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(title:String, joinCount: Int, startDt: String, endDt: String ,fix: Boolean, click: (Long) -> Unit, goalId: Long) {
        itemView.title.text = title
        itemView.jobClass.text = joinCount.toString()
        itemView.term.text =
            if(fix) {
           startDt.toRealDateFormat() + "~" + endDt.toRealDateFormat()
            } else "기간 설정 자유"

        itemView.setOnClickListener {
            click(goalId)
        }
    }
}