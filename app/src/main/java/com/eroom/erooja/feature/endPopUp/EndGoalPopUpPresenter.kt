package com.eroom.erooja.feature.endPopUp

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.alarm.PutAlarmIdUseCase
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import com.eroom.domain.customview.parcelizeclass.ParcelizeAlarmContent
import com.eroom.erooja.singleton.UserInfo
import timber.log.Timber

class EndGoalPopUpPresenter(
    override var view: EndGoalPopUpContract.View,
    private val getTodoListUseCase: GetTodoListUseCase,
    private val putAlarmIdUseCase: PutAlarmIdUseCase
) : EndGoalPopUpContract.Presenter {

    override fun getData(content: ParcelizeAlarmContent) {
        content.goalId?.let {
            getTodoListUseCase.getUserTodoList(UserInfo.myUId, it)
                .subscribe({ response ->
                    val totalSize = response.content.size
                    var endSize = 0
                    response.content.forEach { todo ->
                        if (todo.isEnd) endSize++
                    }
                    view.setView(content.content, ((endSize.toDouble() / totalSize) * 100).toInt())
                    view.stopAnimation()
                }, { throwable ->
                    Timber.e(throwable.localizedMessage)
                    view.stopAnimation()
                })
        }
    }

    @SuppressLint("CheckResult")
    override fun readAlarmRequest(alarmId: Long) {
        putAlarmIdUseCase.readAlarm(alarmId)
            .subscribe({
                Timber.d("alarmId: $alarmId read Request Success")
            }, {
                Timber.e(it.localizedMessage)
            })
    }
}