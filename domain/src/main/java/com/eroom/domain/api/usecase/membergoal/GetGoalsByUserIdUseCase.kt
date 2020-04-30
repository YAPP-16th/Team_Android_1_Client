package com.eroom.domain.api.usecase.membergoal

import com.eroom.data.response.ParticipatedGoalsResponse
import com.eroom.domain.api.service.MemberGoalService
import com.eroom.domain.koin.repository.GuestRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetGoalsByUserIdUseCase(guestRetrofitRepository: GuestRetrofitRepository) {
    private val memberGoalService = guestRetrofitRepository
        .getGuestRetrofit()
        .create(MemberGoalService::class.java)

    fun getGoalsByUserId(
        uid: String,
        size: Int,
        page: Int,
        sortBy: String,
        direction: String,
        endDtIsBeforeNow: Boolean
    ): Single<ParticipatedGoalsResponse> = memberGoalService
        .getGoalsByUserId(uid, size, page, sortBy, direction, endDtIsBeforeNow)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
