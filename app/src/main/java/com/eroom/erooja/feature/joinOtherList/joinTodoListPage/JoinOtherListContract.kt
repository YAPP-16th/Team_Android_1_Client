package com.eroom.erooja.feature.joinOtherList.joinTodoListPage

import com.eroom.data.entity.MinimalTodoListDetail

interface JoinOtherListContract {
    interface View{
        fun redirectNewGoalFinish(resultId: Long)
      //  fun setOtherTodoList(todoList:ArrayList<MinimalTodoListDetail>)
    }
    interface Presenter{
        var view: View

        fun addMyGoal(
            goalId: Long?,
            ownerUid: String?,
            endDt: String,
            todoList: ArrayList<String>
        )
        //fun getOtherTodoList(uid: String, goalId: Long)
    }
}