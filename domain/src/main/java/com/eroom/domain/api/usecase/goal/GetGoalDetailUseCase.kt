package com.eroom.domain.api.usecase.goal

import com.eroom.data.response.GoalDetailResponse
import com.eroom.domain.api.service.GoalService
import com.eroom.domain.koin.repository.GuestRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetGoalDetailUseCase(retrofitRepository: GuestRetrofitRepository) {
    private val goalService = retrofitRepository
        .getGuestRetrofit()
        .create(GoalService::class.java)

    fun getGoalDetail(interestId: Long): Single<GoalDetailResponse> = goalService
        .getGoalDetail(interestId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}