package com.eroom.erooja.feature.addGoal.newGoalPage

import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemGoalListBinding
import timber.log.Timber



class GoalAdapter(
    //private var goalList: MutableLiveData<List<String>>
    private var goalList: ArrayList<String>
) : RecyclerView.Adapter<GoalAdapter.ViewHolder>() {
    lateinit var itemGoalListBinding: ItemGoalListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_goal_list, parent, false)
//        //(view as EditText).setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->  })
//
//        return ViewHolder(view)
        val inflater = LayoutInflater.from(parent.context)
        itemGoalListBinding = ItemGoalListBinding.inflate(inflater, parent, false)
        return ViewHolder(itemGoalListBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item: String? = goalList[position]
        holder.itemGoalListBinding.goalContent.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (event == null || event.action != KeyEvent.ACTION_DOWN) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

                    Log.e("GoalAdapter", "들어옴!")
//                    goalList.add(position,"")
//
//                    notifyItemInserted(position)

                    var len = v.text.trim().length
                    if (len < 1) {

                        v.context.toastShort("한 글자 이상 입력해주세요")
                        return@OnEditorActionListener false
                    } else {
                        goalList.add(position + 1, "")
                        holder.itemGoalListBinding.goalDot.setImageResource(R.drawable.goal_list_dot_activate)
                        v.inputType = InputType.TYPE_NULL
                        this.notifyItemInserted(position + 1)

                    }

                }
                return@OnEditorActionListener false
            }
            holder.itemGoalListBinding.goalContent.context.toastShort("" + actionId)

            true
        })
    }


    override fun getItemCount(): Int = goalList.size

    inner class ViewHolder(val itemGoalListBinding: ItemGoalListBinding) :
        RecyclerView.ViewHolder(itemGoalListBinding.root) {
        fun bind(goalContent: String) {

        }
    }
}
