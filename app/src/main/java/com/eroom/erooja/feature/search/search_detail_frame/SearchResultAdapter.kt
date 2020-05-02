package com.eroom.erooja.feature.search.search_detail_frame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.GoalContent
import com.eroom.data.entity.GoalType
import com.eroom.domain.utils.toRealDateFormat
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.item_main_new_goal.view.*

class SearchResultAdapter (callback: DiffUtil.ItemCallback<GoalContent>, private val goalContent: ArrayList<GoalContent>, private val click: (Long) -> Unit):
    ListAdapter<GoalContent, Holder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_new_goal, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int = goalContent.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(goalContent[position].title, goalContent[position].joinCount, goalContent[position].startDt,
            goalContent[position].endDt, goalContent[position].isDateFixed, click, goalContent[position].id, goalContent[position].jobInterests)
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(title:String, joinCount: Int, startDt: String, endDt: String ,fix: Boolean, click: (Long) -> Unit, goalId: Long, jobInterests: ArrayList<GoalType>) {
        itemView.title.text = title
        if (jobInterests.size > 0) {
            val extraJobClassSize = jobInterests.size - 1
            itemView.jobClass.text = if (extraJobClassSize == 0) jobInterests[0].name else "${jobInterests[0].name} 외 $extraJobClassSize"
        }
        itemView.term.text =
            if(fix) {
           startDt.toRealDateFormat() + "~" + endDt.toRealDateFormat()
            } else "기간 설정 자유"

        itemView.setOnClickListener {
            click(goalId)
        }
    }
}