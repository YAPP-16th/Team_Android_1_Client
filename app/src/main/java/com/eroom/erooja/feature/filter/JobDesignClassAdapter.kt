package com.eroom.erooja.feature.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.localclass.DesignClass
import com.eroom.data.localclass.DesignSelected
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemJobClassFilterBinding

class JobDesignClassAdapter(
    private val jobClass: DesignSelected,
    private val itemClick: (Int) -> Unit,
    private val context: Context
): RecyclerView.Adapter<JobDesignClassViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobDesignClassViewHolder {
        val binding = ItemJobClassFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobDesignClassViewHolder(binding, itemClick, context)
    }

    override fun getItemCount(): Int = if (jobClass.classEnum.size % 2 == 0) jobClass.classEnum.size / 2 else jobClass.classEnum.size / 2 + 1

    override fun onBindViewHolder(holder: JobDesignClassViewHolder, position: Int) {
        when {
            jobClass.classEnum.size % 2 == 0 -> holder.bind(position, jobClass.classEnum[position * 2], jobClass.isSelected[position * 2], jobClass.classEnum[position * 2 + 1], jobClass.isSelected[position * 2 + 1])
            position == itemCount - 1 -> holder.bind(position, jobClass.classEnum[position * 2], jobClass.isSelected[position * 2], null, null)
            else -> holder.bind(position, jobClass.classEnum[position * 2], jobClass.isSelected[position * 2], jobClass.classEnum[position * 2 + 1], jobClass.isSelected[position * 2 + 1])
        }
    }
}

class JobDesignClassViewHolder(val binding: ItemJobClassFilterBinding, private val itemClick: (Int) -> Unit, val context: Context): RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int, firstItem: DesignClass, firstBoolean: Boolean, secondItem: DesignClass?, secondBoolean: Boolean?) {
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