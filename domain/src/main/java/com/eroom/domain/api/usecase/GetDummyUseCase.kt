package com.eroom.domain.api.usecase

import com.eroom.data.response.ResponseExample
import com.eroom.domain.api.service.ServiceExample
import com.eroom.domain.koin.repository.GuestRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetDummyUseCase(retrofitRepository: GuestRetrofitRepository) {
    private val examService = retrofitRepository
        .getGuestRetrofit()
        .create(ServiceExample::class.java)

    fun getDummyData(): Single<ResponseExample> = examService.getDummy()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}