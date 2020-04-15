package com.eroom.erooja.feature.addGoal.newGoalPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroom.erooja.databinding.ItemGoalListBinding


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
        holder.bind(goalList[position])
    }


    override fun getItemCount(): Int = goalList.size

    inner class ViewHolder(itemGoalListBinding: ItemGoalListBinding) :
        RecyclerView.ViewHolder(itemGoalListBinding.root) {
        fun bind(goalContent: String) {
            itemGoalListBinding.goalContentTextview.text = goalContent
        }
    }
}
