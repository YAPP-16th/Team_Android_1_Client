package com.eroom.erooja.feature.endPopUp

import com.eroom.domain.customview.parcelizeclass.ParcelizeAlarmContent

interface EndGoalPopUpContract {
    interface View {
        fun setView(goalTitle: String, achieveRate: Int)
        fun navigateToMainPage()
        fun initView()
    }

    interface Presenter {
        var view: View
        fun getData(content: ParcelizeAlarmContent)
        fun readAlarmRequest(alarmId: Long)
    }
}