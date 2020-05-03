package com.eroom.erooja.feature.otherList

import com.eroom.data.entity.MinimalTodoListContent
import com.eroom.data.entity.MinimalTodoListDetail

interface OtherListContract {
    interface View{
        fun setAllView(todoList:ArrayList<MinimalTodoListDetail>)

    }

    interface Presenter{
        var view: View
        fun getData(uid:String, goalId: Long)
    }
}