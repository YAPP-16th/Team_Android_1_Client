package com.eroom.erooja.feature.signup.page.jobclass

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.localclass.DevelopSelected
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemJobClassButtonBinding

class DevelopClassAdapter(
    private val list: DevelopSelected,
    val context: Context,
    private val clicked: (Int) -> Unit
): RecyclerView.Adapter<DevelopViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevelopViewHolder {
        val binding = ItemJobClassButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DevelopViewHolder(
            binding,
            context
        )
    }

    override fun getItemCount(): Int = list.classEnum.size


    override fun onBindViewHolder(holder: DevelopViewHolder, position: Int) {
        holder.bind(list.classEnum[position].getName(), clicked, position, list.isSelected[position])
    }
}

class DevelopViewHolder(val binding: ItemJobClassButtonBinding, val context: Context): RecyclerView.ViewHolder(binding.root) {
    fun bind(title: String, clicked: (Int) -> Unit, position: Int, isSelected: Boolean) {
        binding.classTitle.text = title
        binding.itemClassBorder.background = if (isSelected) context.getDrawable(R.drawable.border_active_job_class)
        else context.getDrawable(R.drawable.border_inactive_job_class)

        if (isSelected)
            binding.classTitle.setTextColor(context.resources.getColor(R.color.orgDefault, null))
        else
            binding.classTitle.setTextColor(context.resources.getColor(R.color.grey5, null))

        binding.itemClassBorder.setOnClickListener {
            clicked(position)
        }
    }
}
