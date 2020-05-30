package com.eroom.domain.api.usecase.alarm

import com.eroom.data.localclass.SortBy
import com.eroom.domain.api.service.AlarmService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetAlarmsUseCase(accessRetrofitRepository: AccessRetrofitRepository) {
    private val alarmService = accessRetrofitRepository
        .getAccessRetrofit()
        .create(AlarmService::class.java)

    fun getAlarms(page: Int, sortBy: SortBy, size: Int) = alarmService
        .getNotifications(page, sortBy.itemName, size)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}