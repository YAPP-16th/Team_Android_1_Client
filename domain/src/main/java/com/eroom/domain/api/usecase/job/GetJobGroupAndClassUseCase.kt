package com.eroom.domain.api.usecase.job

import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.domain.api.service.JobService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import com.eroom.domain.koin.repository.GuestRetrofitRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetJobGroupAndClassUseCase(retrofitRepository: GuestRetrofitRepository) {
    private val jobService = retrofitRepository
        .getGuestRetrofit()
        .create(JobService::class.java)

    fun getJobGroupAndClass(groupId: Long): Observable<JobGroupAndClassResponse> = jobService
        .getJobClassByGroupId(groupId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}