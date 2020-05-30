package com.eroom.erooja.app.feature.signup.page.jobclass

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.JobClass
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemJobClassButtonBinding

class ClassAdapter(
    private val list: ArrayList<JobClass>,
    val context: Context,
    private val clicked: (Long, Boolean) -> Unit,
    private val selectedList: ArrayList<Long>
) : RecyclerView.Adapter<ClassViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val binding =
            ItemJobClassButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClassViewHolder(
            binding,
            context,
            selectedList
        )
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bind(list[position].id, list[position].name, clicked)
    }
}

class ClassViewHolder(
    val binding: ItemJobClassButtonBinding,
    val context: Context,
    private val selectedList: ArrayList<Long>
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(id: Long, title: String, clicked: (Long, Boolean) -> Unit) {
        binding.classTitle.text = title

        var isSelected = false

        for (selectedId in selectedList) {
            if (selectedId == id) isSelected = true
        }

        binding.itemClassBorder.background =
            if (isSelected) context.getDrawable(R.drawable.border_active_job_class)
            else context.getDrawable(R.drawable.border_inactive_job_class)

        if (isSelected)
            binding.classTitle.setTextColor(context.resources.getColor(R.color.orgDefault, null))
        else
            binding.classTitle.setTextColor(context.resources.getColor(R.color.grey5, null))

        binding.itemClassBorder.setOnClickListener {
            clicked(id, isSelected)
        }
    }
}
