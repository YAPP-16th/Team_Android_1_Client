package com.eroom.erooja.feature.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.localclass.*
import com.eroom.erooja.databinding.ItemGroupJobBinding

class JobGroupAdapter(
    private val jobGroup: ArrayList<JobGroup>,
    private val mDevelopClassInfo: DevelopSelected,
    private val mDesignClassInfo: DesignSelected,
    private val itemDevClicked: (Int) -> Unit,
    private val itemDesignClicked: (Int) -> Unit
): RecyclerView.Adapter<JobGroupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobGroupViewHolder {
        val binding = ItemGroupJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobGroupViewHolder(binding, parent.context, mDevelopClassInfo, mDesignClassInfo, itemDevClicked, itemDesignClicked)
    }

    override fun getItemCount(): Int = jobGroup.size

    override fun onBindViewHolder(holder: JobGroupViewHolder, position: Int) {
        holder.bind(jobGroup[position])
    }

}

class JobGroupViewHolder(
    val binding: ItemGroupJobBinding,
    val context: Context,
    private val mDevelopClassInfo: DevelopSelected,
    private val mDesignClassInfo: DesignSelected,
    private val itemDevClicked: (Int) -> Unit,
    private val itemDesignClicked: (Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    fun bind(jobGroup: JobGroup) {
        binding.groupText.text = jobGroup.groupName
        val mAdapter = when (jobGroup) {
            JobGroup.DEVELOP -> JobDevelopClassAdapter(mDevelopClassInfo, itemDevClicked, context)
            JobGroup.DESIGN -> JobDesignClassAdapter(mDesignClassInfo, itemDesignClicked, context)
        }
        binding.jobClassRecycler.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}