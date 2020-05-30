package com.eroom.erooja.app.feature.othersOngoingGoal

import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.data.response.GoalDetailResponse

interface OthersOngoingGoalContract {
    interface View{
        fun setIsMyOngoingGoal(isMyOngoing: Boolean)
        fun setIsDateFixed(isDateFixed: Boolean)
        fun setGoalData(goalData: GoalDetailResponse)
        fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>)
        fun reRequestTodoList()
        fun settingDate(startDt: String, endDt: String)
        fun setIsExistedInMyPage(isExistedInMyPage: Boolean)
        fun startAnimation()
        fun stopAnimation()
    }

    interface Presenter{
        var view: View
        fun getData(goalId: Long)
        fun getTodoData(uid: String, goalId: Long)
        fun getMyGoalInfoByGoalId(goalId: Long)
        fun getOthersGoalInfoByUIdAndGoalId(goalId: Long, uid: String)
        fun onCleared()
        fun setTodoEnd(todoId: Long, boolean: Boolean)
    }
}