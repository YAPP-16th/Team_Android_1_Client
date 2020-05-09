package com.eroom.erooja.feature.addMyGoalJoin

import android.annotation.SuppressLint
import com.eroom.data.entity.TodoList
import com.eroom.data.request.AddMyGoalRequest
import com.eroom.domain.api.usecase.membergoal.PostAddMyGoalUseCase
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class AddMyListPresenter (override var view: AddMyListContract.View,
                          var getTodoListUseCase: GetTodoListUseCase,
                          private val postAddMyGoalUseCase: PostAddMyGoalUseCase)
    : AddMyListContract.Presenter {
    private val compositeDisposable = CompositeDisposable()


    override fun addMyGoal(
        goalId: Long?,
        ownerUid: String?,
        endDt: String,
        todoList: ArrayList<String>
    ) {
        val todoListResult = ArrayList<TodoList>()
        var index: Long = 0
        for (todo in todoList) {
            todoListResult.add(TodoList(todo, index))
            index++
        }

        postAddMyGoalUseCase.postAddMyGoal(
            AddMyGoalRequest(goalId, ownerUid,
                endDt, todoList = todoListResult
            )
        )
            .subscribe({
                view.redirectNewGoalFinish(it.body()!!.goalId)
            },{
                view.failRequest()
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

    @SuppressLint("CheckResult")
    override fun getUserTodoData(uid:String, goalId: Long) {
        val todolist = ArrayList<String>()
        getTodoListUseCase.getUserTodoList(uid, goalId)
            .subscribe({
                it.content.map {
                    todolist.add(it.content)
                }
                view.setTodoList(todolist)
            },{
                Timber.e(it.localizedMessage)
            })
    }
}