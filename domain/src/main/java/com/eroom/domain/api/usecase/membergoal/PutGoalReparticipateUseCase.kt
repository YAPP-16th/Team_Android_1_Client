package com.eroom.domain.api.usecase.membergoal

import com.eroom.data.request.GoalReParticipateRequest
import com.eroom.data.response.ParticipatedGoalInfoResponse
import com.eroom.domain.api.service.MemberGoalService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class PutGoalReparticipateUseCase(accessRetrofitRepository: AccessRetrofitRepository) {
    private val memberGoalService = accessRetrofitRepository
        .getAccessRetrofit()
        .create(MemberGoalService::class.java)

    fun putGoalReparticate(goalId: Long, goalReParticipateRequest: GoalReParticipateRequest)
            : Single<Response<ParticipatedGoalInfoResponse>> = memberGoalService
        .putGoalReparticipate(goalId, goalReParticipateRequest)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
