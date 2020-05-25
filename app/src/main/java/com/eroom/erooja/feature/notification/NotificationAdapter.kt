package com.eroom.erooja.feature.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.AlarmContent
import com.eroom.domain.utils.loadDrawable
import com.eroom.domain.utils.toMonthAndDateFormat
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemNotificationBinding

class NotificationAdapter(
    callback: DiffUtil.ItemCallback<AlarmContent>,
    private val list: ArrayList<AlarmContent>,
    private val context: Context,
    private val itemClick: (Long, Long?, Int) -> Unit
) : ListAdapter<AlarmContent, NotificationViewHolder>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding, binding.background, binding.roundIcon, binding.dateText, binding.title)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.titleText.text = list[position].content
        holder.dateText.text = list[position].createDt.toMonthAndDateFormat()
        if (!list[position].isChecked) {
            holder.backgroud.setBackgroundColor(context.resources.getColor(R.color.orgBright, null))
            holder.roundIcon.loadDrawable(context.resources.getDrawable(R.drawable.ic_oval_org, null))
        } else {
            holder.backgroud.setBackgroundColor(context.resources.getColor(R.color.colorWhite, null))
            holder.roundIcon.loadDrawable(context.resources.getDrawable(R.drawable.ic_oval_copy_3, null))
        }
        holder.itemView.setOnClickListener {
            itemClick(list[position].id, list[position].goalId, position)
        }
    }

    override fun getItemCount(): Int = list.size
}

class NotificationViewHolder(
    binding: ItemNotificationBinding,
    val backgroud: ConstraintLayout,
    val roundIcon: ImageView,
    val dateText: TextView,
    val titleText: TextView
) : RecyclerView.ViewHolder(binding.root)