package com.eroom.erooja.feature.ongoingGoal

import com.eroom.data.response.GoalDetailResponse

interface OngoingGoalContract {
    interface View{
        fun setGoalData(goalData: GoalDetailResponse)
    }

    interface Presenter{
        var view: View
        fun getData(goalId: Long)
        fun getTodoData(goalId: Long)
    }
}