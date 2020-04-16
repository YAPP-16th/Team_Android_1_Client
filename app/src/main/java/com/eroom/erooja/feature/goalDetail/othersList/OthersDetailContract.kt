package com.eroom.erooja.feature.goalDetail.othersList

import com.eroom.data.entity.UserSimpleData

interface OthersDetailContract {
    interface View{
        fun getAllView(list : UserSimpleData)

    }

    interface Presenter{
        var view:View
        fun getData(index: Int)
    }
}