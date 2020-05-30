package com.eroom.domain.api.usecase.membergoal

import com.eroom.data.request.GoalAbandonedRequest
import com.eroom.data.response.ParticipatedGoalInfoResponse
import com.eroom.domain.api.service.MemberGoalService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PutGoalIsAbandonedUseCase(accessRetrofitRepository: AccessRetrofitRepository) {
    private val memberGoalService = accessRetrofitRepository
        .getAccessRetrofit()
        .create(MemberGoalService::class.java)

    fun putGoalIsAbandoned(goalId: Long, goalAbandonedRequest: GoalAbandonedRequest) : Single<ParticipatedGoalInfoResponse> = memberGoalService
        .putGoalIsAbandoned(goalId, goalAbandonedRequest)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
