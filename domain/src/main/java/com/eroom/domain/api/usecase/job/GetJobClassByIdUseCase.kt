package com.eroom.domain.api.usecase.job

import com.eroom.data.response.JobClassResponse
import com.eroom.domain.api.service.JobService
import com.eroom.domain.koin.repository.GuestRetrofitRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetJobClassByIdUseCase(retrofitRepository: GuestRetrofitRepository) {
    private val jobService = retrofitRepository
        .getGuestRetrofit()
        .create(JobService::class.java)

    fun getJobClass(groupId: Long): Observable<JobClassResponse> = jobService
        .getJobClassById(groupId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}