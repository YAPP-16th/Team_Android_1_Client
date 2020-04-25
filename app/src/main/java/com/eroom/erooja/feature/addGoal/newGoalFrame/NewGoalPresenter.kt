package com.eroom.erooja.feature.addGoal.newGoalFrame

import android.annotation.SuppressLint
import com.eroom.data.entity.TodoList
import com.eroom.data.request.NewGoalRequest
import com.eroom.domain.api.usecase.goal.PostNewGoalUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class NewGoalPresenter(override val view: NewGoalContract.View,
                       private val postNewGoalUseCase: PostNewGoalUseCase
) : NewGoalContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    @Suppress("UseWithIndex")
    @SuppressLint("CheckResult")
    override fun addNewGoal(
        title: String,
        description: String,
        isDateFixed: Boolean,
        endDt: String,
        interestIdList: ArrayList<Long>,
        todoList: ArrayList<String>
    ) {
        val todoListResult = ArrayList<TodoList>()
        var index: Long = 0
        for (todo in todoList) {
            todoListResult.add(TodoList(todo, index))
            index++
        }
        postNewGoalUseCase.postNewGoal(NewGoalRequest(title, description, isDateFixed, endDt, interestIdList, todoListResult))
            .subscribe({
                view.redirectNewGoalFinish(it.id)
            },{
                Timber.e(it.localizedMessage)
                view.failRequest()
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}