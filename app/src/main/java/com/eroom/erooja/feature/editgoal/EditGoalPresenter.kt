package com.eroom.erooja.feature.editgoal

import androidx.recyclerview.widget.DiffUtil

class EditGoalPresenter(override val view: EditGoalContract.View) : EditGoalContract.Presenter {
    val callback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}