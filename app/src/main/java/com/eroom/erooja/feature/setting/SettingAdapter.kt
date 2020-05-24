package com.eroom.erooja.feature.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.eroom.erooja.databinding.ItemSettingListBinding


class SettingAdapter(
    val context: Context,
    private val settingList: Array<String>,
    private val click: (Int) -> Unit
) :
    RecyclerView.Adapter<SettingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mBinding = ItemSettingListBinding.inflate(inflater, parent, false)
        return ViewHolder(
            mBinding,
            mBinding.settingTxt,
            mBinding.settingBtn,
            mBinding.settingSelectList
        )

    }

    override fun getItemCount(): Int = settingList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 3 == 0 && position != 0) {
            val param = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            param.setMargins(0, 100, 0, 0)
            holder.item.layoutParams = param
        }

        holder.settingTxt.text = settingList[position]

        if (position == itemCount - 1) {
            holder.settingTxt.setTextColor(
                context.resources.getColor(
                    com.eroom.erooja.R.color.colorError,
                    null
                )
            )
            holder.settingBtn.visibility = View.INVISIBLE
        }

        holder.item.setOnClickListener { click(position) }
        holder.settingBtn.setOnClickListener { click(position) }
    }


    inner class ViewHolder(
        mBinding: ItemSettingListBinding,
        val settingTxt: TextView,
        val settingBtn: ImageButton,
        val item: ConstraintLayout
    ) : RecyclerView.ViewHolder(mBinding.root)
}