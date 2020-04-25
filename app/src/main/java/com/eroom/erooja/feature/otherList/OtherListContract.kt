package com.eroom.erooja.feature.otherList

import com.eroom.data.entity.UserSimpleData

interface OtherListContract {
    interface View{
        fun getAllView(list : UserSimpleData)

    }

    interface Presenter{
        var view: View
        fun getData(index: Int)
    }
}