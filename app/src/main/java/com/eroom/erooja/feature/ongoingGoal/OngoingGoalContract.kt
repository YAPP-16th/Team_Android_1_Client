package com.eroom.erooja.feature.ongoingGoal

import com.eroom.data.entity.UserSimpleData

interface OngoingGoalContract {
    interface View{
        fun getAllView(list : ArrayList<UserSimpleData>)
    }

    interface Presenter{
        var view: View
        fun getData()
    }
}