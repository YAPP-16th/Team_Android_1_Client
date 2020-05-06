package com.eroom.erooja.feature.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.JobClass
import com.eroom.data.entity.MinimalGoalDetailContent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.toRealDateFormat
import com.eroom.erooja.R
import com.eroom.erooja.feature.main.ParticipatedItem
import kotlinx.android.synthetic.main.item_main_new_goal.view.*
import kotlinx.android.synthetic.main.item_my_participated_goal.view.*

class MyPageParticipatedGoalAdapter(
    private val minimalGoalDetailContentList: List<MinimalGoalDetailContent>,
    private val isClicked: (Long) -> Unit,
    callback: DiffUtil.ItemCallback<MinimalGoalDetailContent>
): ListAdapter<MinimalGoalDetailContent, MyPageParticipatedGoalAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return minimalGoalDetailContentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: MinimalGoalDetailContent = minimalGoalDetailContentList[position]
        val jobClassInfo = item.minimalGoalDetail.jobInterests.filter { it.jobInterestType != Consts.JOB_GROUP }.toList()
        val extraInfo = if(jobClassInfo.size - 1 == 0) "" else " 외 ${jobClassInfo.size - 1}"
        holder.bind(goalId = item.goalId, isOrg = position % 2 == 0, percent = "50%",
            jobClasses = "${jobClassInfo[0].name}$extraInfo",
            titleText = item.minimalGoalDetail.title,
            duration = "${item.startDt.toRealDateFormat()}~${item.endDt.toRealDateFormat()}",
            isClicked = isClicked)
    }


    inner class ViewHolder(parent:ViewGroup ):
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_participated_goal, parent, false)) {
        private val percentTv: TextView = itemView.percent
        private val jobClassesTv: TextView = itemView.jobClasses
        private val titleTextTv: TextView = itemView.titleText
        private val durationTv: TextView = itemView.duration

        fun bind(goalId: Long, isOrg: Boolean, percent: String, jobClasses: String,
                 titleText: String, duration: String, isClicked: (Long) -> Unit) {
            val context = itemView.context
            itemView.background = if (isOrg)
                context.resources.getDrawable(R.drawable.back_item_list_org, null)
            else
                context.resources.getDrawable(R.drawable.back_item_list_grey, null)
            percentTv.text = percent
            jobClassesTv.text = jobClasses
            titleTextTv.text = titleText
            durationTv.text = duration
            itemView.setOnClickListener { isClicked(goalId) }
        }
    }

}