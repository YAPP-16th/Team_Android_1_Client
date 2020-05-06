package com.eroom.erooja.feature.ongoingGoal

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.goal.GetGoalDetailUseCase
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class OngoingGoalPresenter(override var view: OngoingGoalContract.View,
                           private val getGoalDetailUseCase: GetGoalDetailUseCase,
                           private val getTodoListUseCase: GetTodoListUseCase
): OngoingGoalContract.Presenter {

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