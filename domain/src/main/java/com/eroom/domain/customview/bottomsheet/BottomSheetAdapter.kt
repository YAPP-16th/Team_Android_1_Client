package com.eroom.domain.customview.bottomsheet

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.localclass.BottomSheetColor
import com.eroom.domain.R
import com.eroom.domain.databinding.ItemSheetBinding

class BottomSheetAdapter(
    private val list: ArrayList<BottomSheetInfo>,
    private val context: Context,
    private val clicked: (Int) -> Unit
): RecyclerView.Adapter<DevelopViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevelopViewHolder {
        val binding = ItemSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DevelopViewHolder(binding, context, clicked)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: DevelopViewHolder, position: Int) {
        holder.bind(list[position].title, list[position].colorInfo, list[position].boldInfo, position)
    }
}

class DevelopViewHolder(val binding: ItemSheetBinding, val context: Context, val clicked: (Int) -> Unit): RecyclerView.ViewHolder(binding.root) {
    fun bind(text: String, color: BottomSheetColor, boldInfo: Boolean, position: Int) {
        binding.itemSheetText.text = text
        binding.itemSheetText.setTextColor(
            when (color) {
                BottomSheetColor.DEFAULT -> context.resources.getColor(R.color.grey5, null)
                BottomSheetColor.RED -> context.resources.getColor(R.color.colorError, null)
                BottomSheetColor.BLUE -> context.resources.getColor(R.color.colorActive, null)
                BottomSheetColor.ORG_DEFAULT -> context.resources.getColor(R.color.orgDefault, null)
            }
        )
        if(boldInfo) binding.itemSheetText.setTypeface(null, Typeface.BOLD)
        binding.itemSheetLayout.setOnClickListener {
            clicked(position)
        }
    }
}