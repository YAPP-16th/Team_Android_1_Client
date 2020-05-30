package com.eroom.erooja.feature.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.JobClass
import com.eroom.domain.utils.loadDrawable
import com.eroom.erooja.R


class MyPageJobClassAdapter(
    private val jobClassList: List<JobClass>,
    private val fragment: MyPageFragment,
    private val isMoreInfo: Boolean,
    private var isExpaned: Boolean
): RecyclerView.Adapter<MyPageJobClassAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return if (jobClassList.size % 2 == 0) jobClassList.size / 2 else jobClassList.size / 2 + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val visibility = (position == itemCount - 1 && isMoreInfo)
        when (position) {
            itemCount - 1 -> {
                if (jobClassList.size % 2 == 0 ) {
                    holder.bind(jobClassList[position * 2].name, jobClassList[position * 2 + 1].name, visibility)
                }
                else {
                    holder.bind(jobClassList[position * 2].name, "", visibility)
                }
            }
            else -> holder.bind(jobClassList[position * 2].name, jobClassList[position * 2 + 1].name, visibility)
        }
    }
    
    inner class ViewHolder(parent:ViewGroup):
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mypage_job_class, parent, false)) {
        private val classTitle: TextView = itemView.findViewById(R.id.class_title)
        private val classTitle2: TextView = itemView.findViewById(R.id.class_title2)
        private val itemClassBorder2: LinearLayout = itemView.findViewById(R.id.item_class_border2)
        private val expendButton: ImageView = itemView.findViewById(R.id.expand_btn)
        fun bind(title1: String, title2: String, moreVisibility: Boolean) {
            classTitle.text = title1
            itemClassBorder2.visibility = View.VISIBLE
            if(title2.isNotEmpty()) {
                classTitle2.text = title2
            } else {
                itemClassBorder2.visibility = View.INVISIBLE
            }
            expendButton.visibility = if (moreVisibility) View.VISIBLE else View.GONE
            if (isExpaned)
                expendButton.loadDrawable(fragment.context?.resources?.getDrawable(R.drawable.ic_icon_small_arrow_top_white, null))
            else
                expendButton.loadDrawable(fragment.context?.resources?.getDrawable(R.drawable.ic_icon_small_arrow_bottom_white, null))
            expendButton.setOnClickListener {
                fragment.expandButtonClicked(isExpaned)
            }
        }
    }
}