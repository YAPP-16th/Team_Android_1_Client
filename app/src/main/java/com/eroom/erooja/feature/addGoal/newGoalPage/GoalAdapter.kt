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


/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
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
        holder.itemGoalListBinding.goalContent.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, event ->
            if (event == null || event.action != KeyEvent.ACTION_DOWN) {
                return@OnEditorActionListener false
            }
            when (actionId) {
                EditorInfo.IME_ACTION_UNSPECIFIED -> {
                    Log.e("GoalAdapter", "들어옴!")
//                    goalList.add(position,"")
//
//                    notifyItemInserted(position)

                    var len = holder.itemGoalListBinding.goalContent.text.trim().length
                    Log.e(
                        "GoalAdapter",
                        holder.itemGoalListBinding.goalContent.text.toString() + "길이 : " + len
                    )

                    if (len < 1) {

                        holder.itemView.context.toastShort("한 글자 이상 입력해주세요")
                        return@OnEditorActionListener false
                    } else {
                        goalList.add(position + 1, "")
                        holder.itemGoalListBinding.goalDot.setImageResource(R.drawable.goal_list_dot_activate)
                        holder.itemGoalListBinding.goalContent.inputType = InputType.TYPE_NULL
                        this.notifyItemInserted(position + 1)
                    }

                }
                else -> {                // 기본 엔터키 동작

                }
            }
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
