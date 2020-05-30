package com.eroom.erooja.feature.setting.setting_alarm

import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.SharedPrefRepository

class AlarmPresenter(
    override var view: AlarmContract.View,
    private var sharedPrefRepository: SharedPrefRepository
) : AlarmContract.Presenter {

    override fun getAlarmFlag() {
        val alarmFlag = sharedPrefRepository.getPrefsBooleanValue(Consts.ALARM_FLAG, true)
        view.setAlarmSwitch(alarmFlag)
    }

    override fun setAlarmFlag(switch: Boolean) {
        sharedPrefRepository.writePrefs(Consts.ALARM_FLAG, switch)
        view.completeAlarmSetting()
    }
}