package com.eroom.domain.api.service

import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface JobService {
    @GET("jobGroup")
    fun getJobGroup(): Single<ArrayList<JobGroup>>

    @GET("jobGroup/{jobGroupId}")
    fun getJobClassByGroupId(@Path("jobGroupId") jobGroupId: Long): Single<JobGroupAndClassResponse>
}