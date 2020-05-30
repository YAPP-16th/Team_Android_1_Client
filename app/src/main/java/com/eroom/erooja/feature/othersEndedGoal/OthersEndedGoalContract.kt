package com.eroom.erooja.feature.othersEndedGoal

import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.data.response.GoalDetailResponse

interface OthersEndedGoalContract {
    interface View {
        fun setGoalData(goalData: GoalDetailResponse)
        fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>)
        fun setIsAbandoned(isAbandoned: Boolean)
        fun setIsDateFixed(isDateFixed: Boolean)
        fun setIsExistedInMyPage(isExisted: Boolean)
        fun setIsMyOngoingGoal(isMyOngoingGoal: Boolean)
        fun settingDate(startDt: String, endDt: String)
        fun setIsBeforeEndDt(isBeforeEndDt: Boolean)
        fun startAnimation()
        fun stopAnimation()
    }

    interface Presenter {
        var view: View
        fun getData(goalId: Long)
        fun getTodoData(uid: String, goalId: Long)
        fun getMyGoalInfoByGoalId(goalId: Long)
        fun getOthersGoalInfoByUIdAndGoalId(goalId: Long, uid: String)
        fun onCleared()
    }
}