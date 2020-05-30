package com.eroom.erooja.app.feature.goalDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.MinimalTodoListContent
import com.eroom.domain.utils.add
import com.eroom.erooja.databinding.GoalSimpleListBinding

class GoalDetailAdapter(
    callback: DiffUtil.ItemCallback<MinimalTodoListContent>,
    val TodoList: ArrayList<MinimalTodoListContent>,
    val isMyOngoingGoal: Boolean,
    val click: (String, String) -> Unit,
    val joinTodoList: (String) -> Unit
) :
    ListAdapter<MinimalTodoListContent, GoalDetailAdapter.ViewHolder>(callback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mBinding = GoalSimpleListBinding.inflate(inflater, parent, false)


        return ViewHolder(
            mBinding, mBinding.usernameList, mBinding.putinNumberTxt, mBinding.text1,
            mBinding.text2, mBinding.text3, mBinding.goalAddBtn, mBinding.simpleList
        )
    }

    override fun getItemCount(): Int = TodoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nickName.text = TodoList[position].nickName add " 님의 리스트"
        holder.copyCount.text = TodoList[position].copyCount.toString() add "명이 선택"

        val size = TodoList[position].todoList.size
        when (size) {
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

        holder.item.setOnClickListener {
            click(
                TodoList[position].uid,
                TodoList[position].nickName
            )
        }
        holder.goalBtn.setOnClickListener { joinTodoList(TodoList[position].uid) }
    }


    inner class ViewHolder(
        mBinding: GoalSimpleListBinding,
        val nickName: TextView,
        val copyCount: TextView,
        val content1: TextView,
        val content2: TextView,
        val content3: TextView,
        val goalBtn: Button,
        val item: ConstraintLayout
    ) : RecyclerView.ViewHolder(mBinding.root) {
        init {
            if (isMyOngoingGoal) {
                mBinding.goalAddBtn.visibility = View.INVISIBLE
            }
        }
    }
}