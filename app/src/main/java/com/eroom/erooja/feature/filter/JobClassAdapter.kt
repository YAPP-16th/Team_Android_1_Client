package com.eroom.erooja.feature.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.JobClass
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemJobClassFilterBinding

class JobClassAdapter(private var list: ArrayList<JobClass>,
                      private val context: Context,
                      private var selectedIds: ArrayList<Long>,
                      private val itemClick: (Long, Boolean) -> Unit
) : RecyclerView.Adapter<JobClassAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mBinding = ItemJobClassFilterBinding.inflate(inflater, parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            itemCount - 1 -> holder.bind(list[position * 2].id, list[position * 2].name, null, null)
            else -> holder.bind(list[position * 2].id, list[position * 2].name, list[position * 2 + 1].id, list[position * 2 + 1].name)
        }
    }


    override fun getItemCount(): Int = if (list.size % 2 == 0) list.size / 2 else list.size / 2 + 1


    inner class ViewHolder(private val binding: ItemJobClassFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(firstId: Long, firstItem: String, secondId: Long?, secondItem: String?) {
            binding.classTitle.text = firstItem
            var firstBoolean = false
            for (id in selectedIds) {
                if (firstId == id) firstBoolean = true
            }
            if (firstBoolean) {
                binding.classTitle.setTextColor(context.resources.getColor(R.color.orgDefault, null))
                binding.itemClassBorder.background = context.resources.getDrawable(R.drawable.border_active_job_class, null)
            } else {
                binding.classTitle.setTextColor(context.resources.getColor(R.color.grey4, null))
                binding.itemClassBorder.background = context.resources.getDrawable(R.drawable.border_inactive_job_class_grey4, null)
            }
            binding.itemClassBorder.setOnClickListener { itemClick(firstId, firstBoolean) }
            secondItem?.let {
                var secondBoolean = false
                for (id in selectedIds) {
                    if (secondId!! == id) secondBoolean = true
                }
                binding.classTitle2.text = it
                binding.itemClassBorder2.visibility = View.VISIBLE
                if (secondBoolean) {
                    binding.classTitle2.setTextColor(context.resources.getColor(R.color.orgDefault, null))
                    binding.itemClassBorder2.background = context.resources.getDrawable(R.drawable.border_active_job_class, null)
                } else {
                    binding.classTitle2.setTextColor(context.resources.getColor(R.color.grey4, null))
                    binding.itemClassBorder2.background = context.resources.getDrawable(R.drawable.border_inactive_job_class_grey4, null)
                }
                binding.itemClassBorder2.setOnClickListener { itemClick(secondId!!, secondBoolean) }
            }
        }
    }
}