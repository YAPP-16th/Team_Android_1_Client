package com.eroom.erooja.app.feature.notification

import com.eroom.data.entity.AlarmContent

interface NotificationContract {
    interface View {
        fun loadAlarms(list: ArrayList<AlarmContent>)
        fun nonAlarmList()
        fun networkError()
        fun setIsEnd()
        fun stopAnimation()
        fun startAnimation()
    }

    interface Presenter {
        val view: View
        fun requestAlarms(page: Int)
        fun requestReadRequest(alarmId: Long)
    }
}