package com.eroom.domain.api.usecase.alarm

import com.eroom.data.localclass.SortBy
import com.eroom.domain.api.service.AlarmService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetUnCheckedAlarmsUseCase(accessRetrofitRepository: AccessRetrofitRepository) {
    private val alarmService = accessRetrofitRepository
        .getAccessRetrofit()
        .create(AlarmService::class.java)

    fun getUncheckedAlarms(page: Int, sortBy: SortBy, size: Int) = alarmService
        .getUnCheckedAlarms(page, sortBy.itemName, size)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}