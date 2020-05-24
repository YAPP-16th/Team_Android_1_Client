package com.eroom.erooja.feature.addGoal.newGoalFrame

import com.eroom.data.entity.TodoList

interface NewGoalContract {
    interface View {
        fun redirectNewGoalFinish(resultId: Long)
        fun failRequest()
        fun stopAnimation()
    }

    interface Presenter {
        val view: View
        fun addNewGoal(
            title: String,
            description: String,
            isDateFixed: Boolean,
            endDt: String,
            interestIdList: ArrayList<Long>,
            todoList: ArrayList<String>
        )
        fun onCleared()
    }
}