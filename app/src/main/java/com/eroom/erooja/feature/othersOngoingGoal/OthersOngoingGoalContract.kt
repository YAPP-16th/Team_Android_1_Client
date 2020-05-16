package com.eroom.erooja.feature.othersOngoingGoal

import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.data.response.GoalDetailResponse

interface OthersOngoingGoalContract {
    interface View{
        fun setIsMyOngoingGoal(isMyOngoing: Boolean)
        fun setGoalData(goalData: GoalDetailResponse)
        fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>)
        fun reRequestTodoList()
    }

    interface Presenter{
        var view: View
        fun getData(goalId: Long)
        fun getTodoData(uid: String, goalId: Long)
        fun getGoalInfoByGoalId(goalId: Long)
        fun onCleared()
        fun setTodoEnd(todoId: Long, boolean: Boolean)
    }
}