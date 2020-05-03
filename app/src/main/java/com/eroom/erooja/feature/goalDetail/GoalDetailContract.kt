package com.eroom.erooja.feature.goalDetail

import com.eroom.data.entity.MinimalTodoListContent

interface GoalDetailContract {
    interface View{
        fun setView(title: String, description: String, joinCount: Int, startDate: String, endDate: String)
        fun setInterestedClassName(list: List<String>)
        fun setRecyclerView(todoList: ArrayList<MinimalTodoListContent>)
    }

    interface Presenter{
        var view:View
        fun getData(goalId: Long)
        fun getInterestedClassName(interestedIds: List<Long>)
        fun getMinimalTodoList(goalId: Long)
        fun onCleared()
    }
}