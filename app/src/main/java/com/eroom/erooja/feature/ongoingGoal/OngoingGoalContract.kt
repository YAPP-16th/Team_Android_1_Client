package com.eroom.erooja.feature.ongoingGoal

import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.data.request.GoalAbandonedRequest
import com.eroom.data.response.GoalDetailResponse

interface OngoingGoalContract {
    interface View{
        fun setGoalData(goalData: GoalDetailResponse)
        fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>)
        fun reRequestTodoList()
        fun onAbandonedSuccess()
        fun onAbandonedFailure()
    }

    interface Presenter{
        var view: View
        fun getData(goalId: Long)
        fun getTodoData(uid: String, goalId: Long)
        fun onCleared()
        fun setTodoEnd(todoId: Long, boolean: Boolean)
        fun setGoalIsAbandoned(goalId: Long, abandonedRequest: GoalAbandonedRequest)
    }
}