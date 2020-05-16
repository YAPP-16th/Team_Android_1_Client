package com.eroom.erooja.feature.othersEndedGoal

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.goal.GetGoalDetailUseCase
import com.eroom.domain.api.usecase.membergoal.GetGoalInfoByGoalIdUseCase
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import com.eroom.erooja.feature.endedGoal.EndedGoalContract
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class OthersEndedGoalPresenter(override var view: OthersEndedGoalContract.View,
                               private val getGoalDetailUseCase: GetGoalDetailUseCase,
                               private val getTodoListUseCase: GetTodoListUseCase,
                               private val getGoalInfoByGoalIdUseCase: GetGoalInfoByGoalIdUseCase
): OthersEndedGoalContract.Presenter {

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
        //목표상세조회 API를 통해 기간이 지났는지 확인 후 아래의 usecase를 실행해야한다.
        getGoalInfoByGoalIdUseCase.getInfoByGoalId(goalId)
            .subscribe({
                view.setIsAbandoned(it.isEnd)

                val endDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(it.endDt)
                val currentTime: Date = Calendar.getInstance().time
                val isBeforeEndDt = (currentTime.time - endDate.time) < 0

                if(isBeforeEndDt)
                    view.setIsMyOngoingGoal(!it.isEnd)
                else
                    view.setIsMyOngoingGoal(false)
            }, {
                Timber.e(it.localizedMessage)
                view.setIsMyOngoingGoal(false)
            })
    }
    override fun setTodoEnd(boolean: Boolean) {

    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}