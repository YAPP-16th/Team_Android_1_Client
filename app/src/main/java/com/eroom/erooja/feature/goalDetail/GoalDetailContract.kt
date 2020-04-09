package com.eroom.erooja.feature.goalDetail

import com.eroom.data.entity.UserSimpleData

interface GoalDetailContract {
    interface View{
        fun getAllView(list : ArrayList<UserSimpleData>)
    }

    interface Presenter{
        var view:View
        fun getData()
    }
}