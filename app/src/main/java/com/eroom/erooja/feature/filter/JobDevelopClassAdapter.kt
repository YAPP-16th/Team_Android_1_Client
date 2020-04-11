package com.eroom.erooja.feature.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.localclass.DevelopClass
import com.eroom.data.localclass.DevelopSelected
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemJobClassFilterBinding

class JobDevelopClassAdapter(
    private val jobClass: DevelopSelected,
    private val itemClick: (Int) -> Unit,
    private val context: Context
): RecyclerView.Adapter<JobDevelopClassViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobDevelopClassViewHolder {
        val binding = ItemJobClassFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobDevelopClassViewHolder(binding, itemClick, context)
    }

    override fun getItemCount(): Int = if (jobClass.classEnum.size % 2 == 0) jobClass.classEnum.size / 2 else jobClass.classEnum.size / 2 + 1

    override fun onBindViewHolder(holder: JobDevelopClassViewHolder, position: Int) {
        when {
            jobClass.classEnum.size % 2 == 0 -> holder.bind(position, jobClass.classEnum[position * 2], jobClass.isSelected[position * 2], jobClass.classEnum[position * 2 + 1], jobClass.isSelected[position * 2 + 1])
            position == itemCount - 1 -> holder.bind(position, jobClass.classEnum[position * 2], jobClass.isSelected[position * 2], null, null)
            else -> holder.bind(position, jobClass.classEnum[position * 2], jobClass.isSelected[position * 2], jobClass.classEnum[position * 2 + 1], jobClass.isSelected[position * 2 + 1])
        }
    }
}

class JobDevelopClassViewHolder(val binding: ItemJobClassFilterBinding, private val itemClick: (Int) -> Unit, val context: Context): RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int, firstItem: DevelopClass, firstBoolean: Boolean, secondItem: DevelopClass?, secondBoolean: Boolean?) {
        binding.classTitle.text = firstItem.getName()
        if (firstBoolean) {
            binding.classTitle.setTextColor(context.resources.getColor(R.color.orgDefault, null))
            binding.itemClassBorder.background = context.resources.getDrawable(R.drawable.border_active_job_class, null)
        } else {
            binding.classTitle.setTextColor(context.resources.getColor(R.color.grey4, null))
            binding.itemClassBorder.background = context.resources.getDrawable(R.drawable.border_inactive_job_class_grey4, null)
        }
        binding.itemClassBorder.setOnClickListener { itemClick(position * 2) }
        secondItem?.let {
            binding.classTitle2.text = it.getName()
            binding.itemClassBorder2.visibility = View.VISIBLE
            if (secondBoolean!!) {
                binding.classTitle2.setTextColor(context.resources.getColor(R.color.orgDefault, null))
                binding.itemClassBorder2.background = context.resources.getDrawable(R.drawable.border_active_job_class, null)
            } else {
                binding.classTitle2.setTextColor(context.resources.getColor(R.color.grey4, null))
                binding.itemClassBorder2.background = context.resources.getDrawable(R.drawable.border_inactive_job_class_grey4, null)
            }
            binding.itemClassBorder2.setOnClickListener { itemClick(position * 2 + 1) }
        }
    }
}