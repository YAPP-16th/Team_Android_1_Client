package com.eroom.erooja.feature.ongoingGoal

import android.annotation.SuppressLint
import com.eroom.data.request.GoalAbandonedRequest
import com.eroom.domain.api.usecase.goal.GetGoalDetailUseCase
import com.eroom.domain.api.usecase.membergoal.PutGoalIsAbandonedUseCase
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import com.eroom.domain.api.usecase.todo.PutTodoEditUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class OngoingGoalPresenter(override var view: OngoingGoalContract.View,
                           private val getGoalDetailUseCase: GetGoalDetailUseCase,
                           private val getTodoListUseCase: GetTodoListUseCase,
                           private val putTodoEditUseCase: PutTodoEditUseCase,
                           private val putGoalIsAbandonedUseCase: PutGoalIsAbandonedUseCase
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

    @SuppressLint("CheckResult")
    override fun setTodoEnd(todoId: Long, boolean: Boolean) {
        putTodoEditUseCase.putTodoEdit(todoId, boolean)
            .subscribe({
                view.reRequestTodoList()
            },{
                Timber.e(it.localizedMessage)
                view.reRequestTodoList()
            })
    }

    @SuppressLint("CheckResult")
    override fun setGoalIsAbandoned(goalId: Long, abandonedRequest: GoalAbandonedRequest) {
        putGoalIsAbandonedUseCase.putGoalIsAbandoned(goalId, abandonedRequest)
            .subscribe({
                view.onAbandonedSuccess()
            }, {
                Timber.e(it.localizedMessage)
                view.onAbandonedFailure()
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}