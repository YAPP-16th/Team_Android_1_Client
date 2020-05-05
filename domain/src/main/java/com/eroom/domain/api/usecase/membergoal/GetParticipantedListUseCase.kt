package com.eroom.domain.api.usecase.membergoal

import com.eroom.domain.api.service.MemberGoalService
import com.eroom.domain.koin.repository.GuestRetrofitRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetParticipantedListUseCase(guestRetrofitRepository: GuestRetrofitRepository) {
    private val memberGoalService = guestRetrofitRepository
        .getGuestRetrofit()
        .create(MemberGoalService::class.java)

    fun getParticipantedList(goalId: Long, size: Int, page: Int) = memberGoalService
        .getParticepantedList(goalId, size, page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}