package com.eroom.erooja.feature.addMyGoalJoin

import com.eroom.data.entity.MinimalTodoListDetail

interface AddMyListContract {
    interface View {
        fun redirectNewGoalFinish(resultId: Long)
        fun failRequest()
        fun setTodoList(todoList:ArrayList<String>)

    }

    interface Presenter {
        val view: View

        fun addMyGoal(
            goalId: Long?,
            ownerUid: String?,
            endDt: String,
            todoList: ArrayList<String>
        )
        fun onCleared()

        fun getUserTodoData(uid:String, goalId: Long)
    }
}