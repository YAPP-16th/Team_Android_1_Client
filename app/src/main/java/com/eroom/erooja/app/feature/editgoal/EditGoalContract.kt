package com.eroom.erooja.app.feature.editgoal

import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.data.entity.Todo

interface EditGoalContract {
    interface View {
        fun setEditList(todoList: ArrayList<MinimalTodoListDetail>)
        fun finishActivity()
        fun changeSuccess()
        fun stopAnimation()
    }

    interface Presenter {
        val view: View
        fun getTodoData(uid: String, goalId: Long)
        fun putTodoList(uid: String, goalId: Long, todoList: ArrayList<Todo>)
        fun putTodoList(goalId: Long, todoList: ArrayList<Todo>)
    }
}