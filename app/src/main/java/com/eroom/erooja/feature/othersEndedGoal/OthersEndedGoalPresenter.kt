package com.eroom.erooja.feature.othersEndedGoal

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.goal.GetGoalDetailUseCase
import com.eroom.domain.api.usecase.membergoal.GetGoalInfoByGoalIdUseCase
import com.eroom.domain.api.usecase.membergoal.GetGoalInfoByUIdAndGoalIdUseCase
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class OthersEndedGoalPresenter(
    override var view: OthersEndedGoalContract.View,
    private val getGoalDetailUseCase: GetGoalDetailUseCase,
    private val getTodoListUseCase: GetTodoListUseCase,
    private val getGoalInfoByGoalIdUseCase: GetGoalInfoByGoalIdUseCase,
    private val getGoalInfoByUIdAndGoalIdUseCase: GetGoalInfoByUIdAndGoalIdUseCase
) : OthersEndedGoalContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getData(goalId: Long) {
        view.startAnimation()
        getGoalDetailUseCase.getGoalDetail(goalId)
            .subscribe({
                view.setGoalData(it)
                view.setIsDateFixed(it.isDateFixed)
                view.stopAnimation()
            }, {
                Timber.e(it.localizedMessage)
                view.stopAnimation()
            })
    }

    @SuppressLint("CheckResult")
    override fun getTodoData(uid: String, goalId: Long) {
        getTodoListUseCase.getUserTodoList(uid, goalId)
            .subscribe({
                view.setTodoList(it.content)
            }, {
                Timber.e(it.localizedMessage)
            })
    }

    @SuppressLint("CheckResult", "SimpleDateFormat")
    override fun getMyGoalInfoByGoalId(goalId: Long) {
        //목표상세조회 API를 통해 기간이 지났는지 확인 후 아래의 usecase를 실행해야한다.
        getGoalInfoByGoalIdUseCase.getInfoByGoalId(goalId)
            .subscribe({
                it.body()?.let { body ->
                    view.setIsExistedInMyPage(true)
                    view.setIsAbandoned(body.isEnd)

                    val endDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(body.endDt)
                    val currentTime: Date = Calendar.getInstance().time
                    val isBeforeEndDt = (currentTime.time - endDate.time) < 0

                    if (isBeforeEndDt) {
                        view.setIsMyOngoingGoal(!body.isEnd) //mypage 참여중 탭에있는 경우 true
                    } else {
                        view.setIsMyOngoingGoal(false)
                    }
                } ?: if (it.code() == 400) {
                    view.setIsExistedInMyPage(false)
                    view.setIsMyOngoingGoal(false)
                }
            }, {
                Timber.e(it.localizedMessage)
                view.setIsExistedInMyPage(false)
                view.setIsMyOngoingGoal(false)
            })
    }

    @SuppressLint("CheckResult", "SimpleDateFormat")
    override fun getOthersGoalInfoByUIdAndGoalId(goalId: Long, uid: String) {
        getGoalInfoByUIdAndGoalIdUseCase.getParticipatedInfoByUIdAndGoalId(goalId, uid)
            .subscribe({
                it.body()?.let { body ->
                    view.settingDate(body.startDt, body.endDt)

                    val endDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(body.endDt)
                    val currentTime: Date = Calendar.getInstance().time
                    val isBeforeEndDt = (currentTime.time - endDate.time) < 0

                    view.setIsBeforeEndDt(isBeforeEndDt)
                } ?: if (it.code() == 400) {
                    view.settingDate("0000", "0000")
                    view.setIsBeforeEndDt(false)
                }
            }, {
                Timber.e(it.localizedMessage)
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}