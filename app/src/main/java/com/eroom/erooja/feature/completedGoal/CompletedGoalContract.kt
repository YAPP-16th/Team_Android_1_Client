package com.eroom.erooja.feature.completedGoal

import com.eroom.data.entity.UserSimpleData

interface CompletedGoalContract {
    interface View{
        fun getAllView(list : UserSimpleData)

    }

    interface Presenter{
        var view: View
        fun getData(index: Int)
    }
}