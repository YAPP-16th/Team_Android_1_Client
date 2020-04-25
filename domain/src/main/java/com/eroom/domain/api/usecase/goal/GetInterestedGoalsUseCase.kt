package com.eroom.domain.api.usecase.goal

import com.eroom.data.response.InterestedGoalsResponse
import com.eroom.domain.api.service.GoalService
import com.eroom.domain.koin.repository.GuestRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetInterestedGoalsUseCase(retrofitRepository: GuestRetrofitRepository) {
    private val goalService = retrofitRepository
        .getGuestRetrofit()
        .create(GoalService::class.java)

    fun getInterestedGoals(interestId: Long, size: Int, page: Int): Single<InterestedGoalsResponse> = goalService
        .getInterestedGoals(interestId, size, page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}