package com.eroom.domain.api.usecase.alarm

import com.eroom.domain.api.service.AlarmService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PutAlarmIdUseCase(accessRetrofitRepository: AccessRetrofitRepository) {
    private val alarmService = accessRetrofitRepository
        .getAccessRetrofit()
        .create(AlarmService::class.java)

    fun readAlarm(alarmId: Long) = alarmService
        .putAlarmId(alarmId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}