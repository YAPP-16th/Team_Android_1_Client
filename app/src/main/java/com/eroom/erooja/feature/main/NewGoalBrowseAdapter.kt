package com.eroom.erooja.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.GoalContent
import com.eroom.domain.utils.toRealDateFormat
import com.eroom.erooja.databinding.ItemMainNewGoalBinding

class NewGoalBrowseAdapter(private val items: ArrayList<GoalContent>, private val classText: String) :
    RecyclerView.Adapter<NewGoalBrowseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mBinding = ItemMainNewGoalBinding.inflate(inflater, parent, false)
        return ViewHolder(mBinding, mBinding.title, mBinding.jobClass, mBinding.term)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.jobClass.text = classText
        holder.term.text =
            if (items[position].isDateFixed) items[position].startDt.toRealDateFormat() + "~" + items[position].endDt.toRealDateFormat()
            else "기간 설정 자유"

    }

    inner class ViewHolder(
        mBinding: ItemMainNewGoalBinding,
        val title: TextView,
        val jobClass: TextView,
        val term: TextView
    ) : RecyclerView.ViewHolder(mBinding.root)
}