package com.eroom.domain.api.usecase.membergoal

import com.eroom.data.response.ParticipatedGoalInfoResponse
import com.eroom.domain.api.service.MemberGoalService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class GetGoalInfoByUIdAndGoalIdUseCase(accessRetrofitRepository: AccessRetrofitRepository) {
    private val memberGoalService = accessRetrofitRepository
        .getAccessRetrofit()
        .create(MemberGoalService::class.java)

    fun getParticipatedInfoByUIdAndGoalId(goalId: Long, uid: String): Single<Response<ParticipatedGoalInfoResponse>> = memberGoalService
        .getParticipatedInfoByUIdAndGoalId(goalId, uid)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}