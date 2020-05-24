package com.eroom.erooja.feature.ongoingGoal

import android.annotation.SuppressLint
import com.eroom.data.entity.MinimalTodoListContent
import com.eroom.data.request.GoalAbandonedRequest
import com.eroom.domain.api.usecase.goal.GetGoalDetailUseCase
import com.eroom.domain.api.usecase.membergoal.GetGoalInfoByGoalIdUseCase
import com.eroom.domain.api.usecase.membergoal.PutGoalIsAbandonedUseCase
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import com.eroom.domain.api.usecase.todo.PutTodoEditUseCase
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import timber.log.Timber

class OngoingGoalPresenter(override var view: OngoingGoalContract.View,
                           private val getGoalDetailUseCase: GetGoalDetailUseCase,
                           private val getTodoListUseCase: GetTodoListUseCase,
                           private val putTodoEditUseCase: PutTodoEditUseCase,
                           private val putGoalIsAbandonedUseCase: PutGoalIsAbandonedUseCase,
                           private val getGoalInfoByGoalIdUseCase: GetGoalInfoByGoalIdUseCase
): OngoingGoalContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getData(goalId: Long) {
        getGoalDetailUseCase.getGoalDetail(goalId)
            .subscribe({
                view.setGoalData(it)
            },{
                Timber.e(it.localizedMessage)
                view.stopAnimation()
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

    @SuppressLint("CheckResult")
    override fun getGoalInfo(goalId: Long) {
        getGoalInfoByGoalIdUseCase.getInfoByGoalId(goalId)
            .subscribe({
                it.body()?.let { body ->
                    view.settingDate(body.startDt, body.endDt)
                    view.settingEditButton(body.role == "OWNER")
                } ?: run {
                    view.settingEditButton(false)
                }
            },{
                Timber.e(it.localizedMessage)
                handleError(it)
            })
    }

    private fun handleError(throwable: Throwable) {
        if (throwable is HttpException) {
            val statusCode = throwable.code()
            // handle different HTTP error codes here (4xx)
            if (statusCode == 400) {
                view.settingEditButton(false)
            }
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}