package com.eroom.erooja.app.feature.setting.setting_alarm

interface AlarmContract {
    interface View {
        fun completeAlarmSetting()
        fun setAlarmSwitch(switch: Boolean)
    }

    interface Presenter {
        var view: View
        fun getAlarmFlag()
        fun setAlarmFlag(switch: Boolean)
    }
}