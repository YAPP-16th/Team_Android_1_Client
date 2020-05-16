package com.eroom.erooja.feature.othersEndedGoal

import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.data.response.GoalDetailResponse

interface OthersEndedGoalContract {
    interface View{
        fun setGoalData(goalData: GoalDetailResponse)
        fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>)
        fun setIsAbandoned(isAbandoned: Boolean)
        fun setIsDateFixed(isDateFixed: Boolean)
        fun setIsMyOngoingGoal(isOngoing: Boolean)
    }

    interface Presenter{
        var view: View
        fun getData(goalId: Long)
        fun getTodoData(uid: String, goalId: Long)
        fun getGoalInfoByGoalId(goalId: Long)
        fun onCleared()
        fun setTodoEnd(boolean: Boolean)
    }
}