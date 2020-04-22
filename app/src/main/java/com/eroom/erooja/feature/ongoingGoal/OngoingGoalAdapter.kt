package com.eroom.erooja.feature.ongoingGoal

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.core.view.ViewCompat.animate
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.UserSimpleData
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.item_ongoing_goal_list.view.*

class OngoingGoalAdapter(val simpleData: ArrayList<UserSimpleData>, val save_animate: (Boolean) -> ViewPropertyAnimator):
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ongoing_goal_list, parent, false)
        return Holder(inflatedView)

    }

    override fun getItemCount(): Int =simpleData.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(simpleData[position].index, simpleData[position].name, simpleData[position].like,
            simpleData[position].check1, simpleData[position].check2, simpleData[position].check3, save_animate)
    }

}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(index:Int, name:String, number: Int, text1:String, text2:String, text3:String, save_animate: (Boolean) -> ViewPropertyAnimator) {
        itemView.ongoing_detail_checkbox.text=text1

        itemView.ongoing_detail_checkbox.setOnClickListener {
            if (itemView.ongoing_detail_checkbox.isChecked) {
                itemView.ongoing_detail_checkbox.apply {
                    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(context.getColor(R.color.grey4))
                    save_animate(true)
                }
            } else {
                itemView.ongoing_detail_checkbox.apply {
                    paintFlags = 0
                    setTextColor(context.getColor(R.color.grey7))
                    save_animate(false)
                }
            }

        }

    }
}