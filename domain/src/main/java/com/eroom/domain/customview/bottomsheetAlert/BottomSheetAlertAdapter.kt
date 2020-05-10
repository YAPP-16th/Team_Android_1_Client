package com.eroom.domain.customview.bottomsheetAlert

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.localclass.BottomSheetColor
import com.eroom.domain.R
import com.eroom.domain.customview.bottomsheet.BottomSheetInfo
import com.eroom.domain.databinding.AlertItemSheetBinding
import com.eroom.domain.databinding.ItemSheetBinding

class BottomSheetAlertAdapter(
    private val list: ArrayList<BottomSheetInfo>,
    private val context: Context,
    private val clicked: (Int) -> Unit
): RecyclerView.Adapter<BottomSheetAlertAdapter.DevelopViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevelopViewHolder {
        val binding = AlertItemSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DevelopViewHolder(binding, context, clicked)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: DevelopViewHolder, position: Int) {
        holder.bind(list[position].title, list[position].colorInfo, position)
    }

    inner class DevelopViewHolder(
        val binding: AlertItemSheetBinding, val context: Context, val clicked: (Int) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String, color: BottomSheetColor, position: Int) {
            binding.itemSheetText.text = text
            binding.itemSheetText.setTextColor(
                when (color) {
                    BottomSheetColor.DEFAULT -> context.resources.getColor(R.color.grey5, null)
                    BottomSheetColor.RED -> context.resources.getColor(R.color.colorError, null)
                    BottomSheetColor.BLUE -> context.resources.getColor(R.color.colorActive, null)
                    BottomSheetColor.ORG_DEFAULT -> context.resources.getColor(R.color.orgDefault, null)
                }
            )
            binding.itemSheetLayout.setOnClickListener {
                clicked(position)
            }
        }
    }
}

