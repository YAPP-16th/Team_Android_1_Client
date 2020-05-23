package com.eroom.erooja.feature.endedGoal

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.goal.GetGoalDetailUseCase
import com.eroom.domain.api.usecase.membergoal.GetGoalInfoByGoalIdUseCase
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import com.eroom.erooja.feature.ongoingGoal.OngoingGoalContract
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class EndedGoalPresenter(override var view: EndedGoalContract.View,
                         private val getGoalDetailUseCase: GetGoalDetailUseCase,
                         private val getTodoListUseCase: GetTodoListUseCase,
                         private val getGoalInfoByGoalIdUseCase: GetGoalInfoByGoalIdUseCase
): EndedGoalContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getData(goalId: Long) {
        getGoalDetailUseCase.getGoalDetail(goalId)
            .subscribe({
                view.setGoalData(it)
                view.setIsDateFixed(it.isDateFixed)
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

    @SuppressLint("CheckResult", "SimpleDateFormat")
    override fun getGoalInfoByGoalId(goalId: Long) {
        getGoalInfoByGoalIdUseCase.getInfoByGoalId(goalId)
            .subscribe({
                it.body()?.let { body ->
                    view.setIsAbandoned(body.isEnd)
                    view.settingDate(body.startDt, body.endDt)
                    val endDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(body.endDt)
                    val currentTime: Date = Calendar.getInstance().time
                    val isBeforeEndDt = (currentTime.time - endDate.time) < 0
                    view.setIsBeforeEndDt(isBeforeEndDt)
                }

//                it.body()?.let { body ->
//                    view.setIsAbandoned(body.isEnd)
//
//                    val endDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(body.endDt)
//                    val currentTime: Date = Calendar.getInstance().time
//                    val isBeforeEndDt = (currentTime.time - endDate.time) < 0
//
//                    if(isBeforeEndDt)
//                        view.setIsMyOngoingGoal(!body.isEnd)
//                    else
//                        view.setIsMyOngoingGoal(false)
//                } ?: if (it.code() == 400) view.setIsMyOngoingGoal(false)
            }, {
                Timber.e(it.localizedMessage)
            })
    }

    override fun setTodoEnd(boolean: Boolean) {

    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}