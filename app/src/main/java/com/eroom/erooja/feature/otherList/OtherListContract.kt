package com.eroom.erooja.feature.otherList

import com.eroom.data.entity.MinimalTodoListContent
import com.eroom.data.entity.MinimalTodoListDetail

interface OtherListContract {
    interface View{
        fun setAllView(todoList:ArrayList<MinimalTodoListDetail>)
        fun setProfileImage(imagePath: String?)
        fun startAnimation()
        fun stopAnimation()
    }

    interface Presenter{
        var view: View
        fun getData(uid:String, goalId: Long)
        fun getProfileImage(uid: String)
    }
}