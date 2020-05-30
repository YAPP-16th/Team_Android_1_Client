package com.eroom.erooja.feature.addGoal.newGoalPage.selectjob

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.JobClass
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.erooja.databinding.ItemGroupJobBinding


class SelectJobAdapter(
    val context: Context,
    private var jobList: List<JobGroupAndClassResponse>,
    private var selectedIds: ArrayList<Long>,
    private val itemClick: (Long, Boolean) -> Unit
) : RecyclerView.Adapter<SelectJobAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mBinding = ItemGroupJobBinding.inflate(inflater, parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(jobList[position].name, context, jobList[position].jobInterests)
    }


    override fun getItemCount(): Int = jobList.size


    inner class ViewHolder(private val mBinding: ItemGroupJobBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(groupText: String, context: Context, list: ArrayList<JobClass>) {
            mBinding.groupText.text = groupText
            mBinding.jobClassRecycler.apply {
                adapter = SelectJobClassAdapter(list, context, selectedIds, itemClick)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}