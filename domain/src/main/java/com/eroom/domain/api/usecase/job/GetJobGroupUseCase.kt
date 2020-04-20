package com.eroom.domain.api.usecase.job

import com.eroom.data.entity.JobGroup
import com.eroom.domain.api.service.JobService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetJobGroupUseCase(retrofitRepository: AccessRetrofitRepository) {
    private val jobService = retrofitRepository
        .getAccessRetrofit()
        .create(JobService::class.java)

    fun getJobGroup(): Single<ArrayList<JobGroup>> = jobService
        .getJobGroup()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}