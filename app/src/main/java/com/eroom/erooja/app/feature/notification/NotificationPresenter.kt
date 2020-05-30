package com.eroom.erooja.app.feature.notification

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.eroom.data.entity.AlarmContent
import com.eroom.data.localclass.SortBy
import com.eroom.domain.api.usecase.alarm.GetAlarmsUseCase
import com.eroom.domain.api.usecase.alarm.PutAlarmIdUseCase
import timber.log.Timber

class NotificationPresenter(
    override val view: NotificationContract.View,
    private val putAlarmIdUseCase: PutAlarmIdUseCase,
    private val getAlarmsUseCase: GetAlarmsUseCase
) : NotificationContract.Presenter {

    val mAlarmDiffCallback = object : DiffUtil.ItemCallback<AlarmContent>() {
        override fun areItemsTheSame(oldItem: AlarmContent, newItem: AlarmContent): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlarmContent, newItem: AlarmContent): Boolean {
            return (oldItem.title == newItem.title) && (oldItem.id == newItem.id)
        }
    }

    @SuppressLint("CheckResult")
    override fun requestAlarms(page: Int) {
        view.startAnimation()
        getAlarmsUseCase.getAlarms(page = page, size = 10, sortBy = SortBy.CREATED_DT)
            .subscribe({
                view.loadAlarms(it.content)
                if (page == 0 && it.content.size == 0) view.nonAlarmList()
                if (page >= it.totalPages - 1) view.setIsEnd()
                view.stopAnimation()
            }, {
                Timber.e(it.localizedMessage)
                view.networkError()
                view.stopAnimation()
            })
    }

    @SuppressLint("CheckResult")
    override fun requestReadRequest(alarmId: Long) {
        putAlarmIdUseCase.readAlarm(alarmId)
            .subscribe({
                Timber.d("Read $alarmId alarm Success")
            }, {
                Timber.e(it.localizedMessage)
            })
    }
}