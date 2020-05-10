package com.eroom.erooja.feature.editgoal

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.eroom.data.entity.Todo
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import com.eroom.domain.api.usecase.todo.PutTodoListUseCase
import timber.log.Timber

class EditGoalPresenter(override val view: EditGoalContract.View,
                        private val getTodoListUseCase: GetTodoListUseCase,
                        private val putTodoListUseCase: PutTodoListUseCase
) : EditGoalContract.Presenter {
    val callback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    @SuppressLint("CheckResult")
    override fun getTodoData(uid: String, goalId: Long) {
        getTodoListUseCase.getUserTodoList(uid, goalId)
            .subscribe({
                view.setEditList(it.content)
            },{
                Timber.e(it.localizedMessage)
            })
    }

    @SuppressLint("CheckResult")
    override fun putTodoList(uid: String, goalId: Long, todoList: ArrayList<Todo>) {
        putTodoListUseCase.putTodoList(goalId, todoList)
            .subscribe({
                getTodoData(uid, goalId)
            },{
                Timber.e(it.localizedMessage)
            })
    }

    @SuppressLint("CheckResult")
    override fun putTodoList(goalId: Long, todoList: ArrayList<Todo>) {
        putTodoListUseCase.putTodoList(goalId, todoList)
            .subscribe({
                view.finishActivity()
            },{
                Timber.e(it.localizedMessage)
            })
    }

}