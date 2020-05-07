package com.eroom.erooja.feature.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.MinimalGoalDetailContent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.toRealDateFormat
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.item_my_participated_ongoing_goal.view.*

class MyPageOngoingGoalAdapter(
    private var minimalGoalDetailContentList: ArrayList<MinimalGoalDetailContent>,
    private val isClicked: (Long) -> Unit
): RecyclerView.Adapter<MyPageOngoingGoalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return minimalGoalDetailContentList.size
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

    fun submitList(newMinimalGoalDetailContentList: ArrayList<MinimalGoalDetailContent>) {
        val oldList: ArrayList<MinimalGoalDetailContent> = ArrayList()
        minimalGoalDetailContentList.map {
            oldList.add(it)
        }
        newMinimalGoalDetailContentList.map {
            minimalGoalDetailContentList.add(it)
        }
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            MinimalGoalDetailGoalContentItemDiffCallback(oldList, minimalGoalDetailContentList)
        )
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ViewHolder(parent:ViewGroup ):
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_participated_ongoing_goal, parent, false)) {
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

    class MinimalGoalDetailGoalContentItemDiffCallback(
        private var oldMinimalGoalDetailContentList: List<MinimalGoalDetailContent>,
        private var newMinimalGoalDetailContentList: List<MinimalGoalDetailContent>
    ): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldMinimalGoalDetailContentList[oldItemPosition].goalId == newMinimalGoalDetailContentList[newItemPosition].goalId)
        }

        override fun getOldListSize(): Int {
            return oldMinimalGoalDetailContentList.size
        }

        override fun getNewListSize(): Int {
            return newMinimalGoalDetailContentList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldMinimalGoalDetailContentList[oldItemPosition] == newMinimalGoalDetailContentList[newItemPosition])
        }

    }

}