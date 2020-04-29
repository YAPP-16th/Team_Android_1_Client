package com.eroom.erooja.feature.search.search_detail_frame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.GoalContent
import com.eroom.domain.utils.StringDateUtil
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.item_main_new_goal.view.*

class SearchResultAdapter (val GoalContent: ArrayList<GoalContent>, val click: (Int) -> Unit):
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_new_goal, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int =GoalContent.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(GoalContent[position].title, GoalContent[position].joinCount, GoalContent[position].startDt,
            GoalContent[position].endDt, click)
    }

}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(title:String, joinCount: Int, startDt: String, endDt: String , click: (Int) -> Unit) {
        itemView.title.text = title
        itemView.term.text = StringDateUtil.stringForTime(startDt, endDt)
        itemView.setOnClickListener {
            val pos = adapterPosition
            if(pos != RecyclerView.NO_POSITION) click(pos)
        }
    }
}