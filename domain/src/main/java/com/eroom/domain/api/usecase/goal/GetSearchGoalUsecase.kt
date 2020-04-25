package com.eroom.domain.api.usecase.goal

import com.eroom.data.response.SearchGoalResponse
import com.eroom.domain.api.service.GoalService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class GetSearchGoalUsecase(retrofitRepository: AccessRetrofitRepository) {
    private val memberService = retrofitRepository
        .getAccessRetrofit()
        .create(GoalService::class.java)

    fun getSearchGoal(jobInterestIds: Long): Single<ArrayList<SearchGoalResponse>> = memberService
        .getSearchGoal(jobInterestIds)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
