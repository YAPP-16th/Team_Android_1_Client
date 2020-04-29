package com.eroom.domain.api.usecase.goal

import com.eroom.data.response.InterestedGoalsResponse
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

    fun getSearchJobInterest(interestId: Long?): Single<InterestedGoalsResponse> = memberService
        .getSearchJobInterest(interestId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getSearchGoalTitle(goalFilterBy: String,keyword: String? ) : Single<InterestedGoalsResponse> = memberService
        .getSearchGoalTitle(goalFilterBy, keyword)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
