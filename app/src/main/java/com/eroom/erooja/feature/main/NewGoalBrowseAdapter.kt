package com.eroom.erooja.feature.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.GoalContent
import com.eroom.domain.utils.toRealDateFormat
import com.eroom.erooja.databinding.ItemMainNewGoalBinding

class NewGoalBrowseAdapter(private val items: ArrayList<GoalContent>, private val classText: String, private val clicked: (Long) -> Unit) :
    RecyclerView.Adapter<NewGoalBrowseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mBinding = ItemMainNewGoalBinding.inflate(inflater, parent, false)
        return ViewHolder(mBinding, mBinding.title, mBinding.jobClass, mBinding.term, mBinding.itemAll)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].title
        val extraJobClassSize = items[position].jobInterests.size - 1
        val extraText = if (extraJobClassSize == 0) "" else " 외 ${items[position].jobInterests.size - 1}"
        holder.jobClass.text = "$classText$extraText"
        holder.term.text =
            if (items[position].isDateFixed) items[position].startDt.toRealDateFormat() + "~" + items[position].endDt.toRealDateFormat()
            else "기간 설정 자유"
        holder.item.setOnClickListener { clicked(items[position].id) }
    }

    inner class ViewHolder(
        mBinding: ItemMainNewGoalBinding,
        val title: TextView,
        val jobClass: TextView,
        val term: TextView,
        val item: ConstraintLayout
    ) : RecyclerView.ViewHolder(mBinding.root)
}