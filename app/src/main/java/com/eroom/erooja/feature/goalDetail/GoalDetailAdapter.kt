package com.eroom.erooja.feature.goalDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.MinimalTodoListContent
import com.eroom.domain.utils.add
import com.eroom.erooja.R
import com.eroom.erooja.databinding.GoalSimpleListBinding
import com.eroom.erooja.databinding.ItemMainNewGoalBinding
import kotlinx.android.synthetic.main.goal_simple_list.view.*

class GoalDetailAdapter(val TodoList: ArrayList<MinimalTodoListContent>, val click: (String) -> Unit):
    RecyclerView.Adapter<GoalDetailAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mBinding = GoalSimpleListBinding.inflate(inflater, parent, false)

        return ViewHolder(mBinding, mBinding.usernameList, mBinding.putinNumberTxt, mBinding.text1, mBinding.text2, mBinding.text3 , mBinding.simpleList)

    }

    override fun getItemCount(): Int =TodoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nickName.text = TodoList[position].nickName add " 님의 리스트"
        holder.copyCount.text = TodoList[position].copyCount.toString() add "명이 선택"

        val size = TodoList[position].todoList.size
        when(size){
            0 -> {
                holder.content1.visibility = View.INVISIBLE
                holder.content2.visibility = View.INVISIBLE
                holder.content3.visibility = View.INVISIBLE
            }
            1 -> {
                holder.content1.text = TodoList[position].todoList[0].content
                holder.content2.visibility = View.INVISIBLE
                holder.content3.visibility = View.INVISIBLE
            }
            2 -> {
                holder.content1.text = TodoList[position].todoList[0].content
                holder.content2.text = TodoList[position].todoList[1].content
                holder.content3.visibility = View.INVISIBLE

            }
            else -> {
                holder.content1.text = TodoList[position].todoList[0].content
                holder.content2.text = TodoList[position].todoList[1].content
                holder.content3.text = TodoList[position].todoList[2].content
            }

        }

        holder.item.setOnClickListener { click(TodoList[position].uid) }
}


    inner class ViewHolder(
        mBinding : GoalSimpleListBinding,
        val nickName: TextView,
        val copyCount: TextView,
        val content1: TextView,
        val content2: TextView,
        val content3: TextView,
        val item: ConstraintLayout
    ) :RecyclerView.ViewHolder(mBinding.root)
}