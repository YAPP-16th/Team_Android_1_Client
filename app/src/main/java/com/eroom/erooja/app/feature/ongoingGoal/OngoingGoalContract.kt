package com.eroom.erooja.app.feature.ongoingGoal

import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.data.request.GoalAbandonedRequest
import com.eroom.data.response.GoalDetailResponse

interface OngoingGoalContract {
    interface View {
        fun setGoalData(goalData: GoalDetailResponse)
        fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>)
        fun reRequestTodoList()
        fun onAbandonedSuccess()
        fun onAbandonedFailure()
        fun settingEditButton(isMine: Boolean)
        fun settingDate(startDt: String, endDt: String)
        fun stopAnimation()
    }

    interface Presenter {
        var view: View
        fun getData(goalId: Long)
        fun getTodoData(uid: String, goalId: Long)
        fun onCleared()
        fun setTodoEnd(todoId: Long, boolean: Boolean)
        fun setGoalIsAbandoned(goalId: Long, abandonedRequest: GoalAbandonedRequest)
        fun getGoalInfo(goalId: Long)
    }
}