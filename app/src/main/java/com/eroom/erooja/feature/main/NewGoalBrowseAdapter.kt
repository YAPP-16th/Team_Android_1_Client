package com.eroom.erooja.feature.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.GoalContent
import com.eroom.domain.utils.toRealDateFormat
import com.eroom.erooja.databinding.ItemMainNewGoalBinding

class NewGoalBrowseAdapter(
    private val items: ArrayList<GoalContent>,
    private val classText: String,
    private val clicked: (Long) -> Unit,
    private val context: Context
) :
    RecyclerView.Adapter<NewGoalBrowseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mBinding = ItemMainNewGoalBinding.inflate(inflater, parent, false)
        return ViewHolder(
            mBinding,
            mBinding.title,
            mBinding.jobClass,
            mBinding.term,
            mBinding.itemAll,
            mBinding.imageFrame
        )
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].title
        val extraJobClassSize = items[position].jobInterests.size - 1
        val extraText =
            if (extraJobClassSize == 0) "" else " 외 ${items[position].jobInterests.size - 1}"
        holder.jobClass.text = "$classText$extraText"
        holder.term.text =
            if (items[position].isDateFixed) items[position].startDt.toRealDateFormat() + "~" + items[position].endDt.toRealDateFormat()
            else "기간 설정 자유"
        holder.item.setOnClickListener { clicked(items[position].id) }
        when (items[position].userImages.size) {
            3 -> {
                if (items[position].joinCount > 3) {
                    holder.frame.addView(
                        ItemCircleProfile(
                            context,
                            items[position].userImages[2],
                            60f
                        )
                    )
                    holder.frame.addView(
                        ItemCircleProfile(
                            context,
                            items[position].userImages[1],
                            40f
                        )
                    )
                    holder.frame.addView(
                        ItemCircleProfile(
                            context,
                            items[position].userImages[0],
                            20f
                        )
                    )
                    holder.frame.addView(
                        ItemCircleJoinCount(
                            context,
                            items[position].joinCount - 3
                        )
                    )
                } else {
                    holder.frame.addView(
                        ItemCircleProfile(
                            context,
                            items[position].userImages[2],
                            40f
                        )
                    )
                    holder.frame.addView(
                        ItemCircleProfile(
                            context,
                            items[position].userImages[1],
                            20f
                        )
                    )
                    holder.frame.addView(
                        ItemCircleProfile(
                            context,
                            items[position].userImages[0],
                            0f
                        )
                    )
                }
            }
            2 -> {
                if (items[position].joinCount > 2) {
                    holder.frame.addView(
                        ItemCircleProfile(
                            context,
                            items[position].userImages[1],
                            40f
                        )
                    )
                    holder.frame.addView(
                        ItemCircleProfile(
                            context,
                            items[position].userImages[0],
                            20f
                        )
                    )
                    holder.frame.addView(
                        ItemCircleJoinCount(
                            context,
                            items[position].joinCount - 2
                        )
                    )
                } else {
                    holder.frame.addView(
                        ItemCircleProfile(
                            context,
                            items[position].userImages[1],
                            20f
                        )
                    )
                    holder.frame.addView(
                        ItemCircleProfile(
                            context,
                            items[position].userImages[0],
                            0f
                        )
                    )
                }
            }
            1 -> {
                if (items[position].joinCount > 1) {
                    holder.frame.addView(
                        ItemCircleProfile(
                            context,
                            items[position].userImages[0],
                            20f
                        )
                    )
                    holder.frame.addView(
                        ItemCircleJoinCount(
                            context,
                            items[position].joinCount - 1
                        )
                    )
                } else {
                    holder.frame.addView(
                        ItemCircleProfile(
                            context,
                            items[position].userImages[0],
                            0f
                        )
                    )
                }
            }
            0 -> {
                holder.frame.addView(ItemCircleJoinCount(context, items[position].joinCount))
            }
        }
    }

    inner class ViewHolder(
        mBinding: ItemMainNewGoalBinding,
        val title: TextView,
        val jobClass: TextView,
        val term: TextView,
        val item: ConstraintLayout,
        val frame: FrameLayout
    ) : RecyclerView.ViewHolder(mBinding.root)
}