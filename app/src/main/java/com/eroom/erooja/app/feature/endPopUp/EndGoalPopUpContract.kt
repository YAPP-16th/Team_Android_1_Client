package com.eroom.erooja.app.feature.endPopUp

import com.eroom.domain.customview.parcelizeclass.ParcelizeAlarmContent

interface EndGoalPopUpContract {
    interface View {
        fun setView(goalTitle: String, achieveRate: Int)
        fun navigateToMainPage()
        fun initView()
        fun stopAnimation()
    }

    interface Presenter {
        var view: View
        fun getData(content: ParcelizeAlarmContent)
        fun readAlarmRequest(alarmId: Long)
    }
}