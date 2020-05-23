package com.eroom.erooja.feature.goalDetail

import com.eroom.data.entity.MinimalTodoListContent
import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.data.response.GoalDetailResponse

interface GoalDetailContract {
    interface View{
       // fun setView(title: String, description: String, joinCount: Int, isDateFixed:Boolean, startDate: String, endDate: String)
        fun setInterestedClassName(list: List<String>)
        fun setRecyclerView(todoList: ArrayList<MinimalTodoListContent>, isMine: Boolean, isJoined: Boolean)
        fun setTodoList(todoList:ArrayList<MinimalTodoListDetail>)

        fun setInitialView(list: GoalDetailResponse)
    }

    interface Presenter{
        var view:View
        fun getData(goalId: Long)
        fun getUserTodoList(uid:String, goalId: Long)
        fun getInterestedClassName(interestedIds: List<Long>)
        fun getMinimalTodoList(goalId: Long)
        fun onCleared()
    }
}