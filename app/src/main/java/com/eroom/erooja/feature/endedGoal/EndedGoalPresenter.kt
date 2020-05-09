package com.eroom.erooja.feature.endedGoal

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.goal.GetGoalDetailUseCase
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import com.eroom.erooja.feature.ongoingGoal.OngoingGoalContract
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class EndedGoalPresenter(override var view: EndedGoalContract.View,
                           private val getGoalDetailUseCase: GetGoalDetailUseCase,
                           private val getTodoListUseCase: GetTodoListUseCase
): EndedGoalContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getData(goalId: Long) {
        getGoalDetailUseCase.getGoalDetail(goalId)
            .subscribe({
                view.setGoalData(it)
            },{
                Timber.e(it.localizedMessage)
            })
    }

    @SuppressLint("CheckResult")
    override fun getTodoData(uid: String, goalId: Long) {
        getTodoListUseCase.getUserTodoList(uid, goalId)
            .subscribe({
                view.setTodoList(it.content)
            },{
                Timber.e(it.localizedMessage)
            })
    }

    override fun setTodoEnd(boolean: Boolean) {

    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}