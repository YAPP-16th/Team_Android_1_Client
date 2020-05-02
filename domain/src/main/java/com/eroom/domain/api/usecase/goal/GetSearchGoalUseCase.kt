package com.eroom.domain.api.usecase.goal

import com.eroom.data.localclass.SortBy
import com.eroom.data.response.InterestedGoalsResponse
import com.eroom.domain.api.service.GoalService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class GetSearchGoalUseCase(retrofitRepository: AccessRetrofitRepository) {
    private val goalService = retrofitRepository
        .getAccessRetrofit()
        .create(GoalService::class.java)

    fun getSearchJobInterest(interestId: Long?, size: Int, page: Int, sortBy: SortBy): Single<InterestedGoalsResponse> = goalService
        .getSearchJobInterest(interestId, size, page, sort = sortBy.itemName)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getSearchGoalTitle(goalFilterBy: SortBy, keyword: String?, size: Int, page: Int) : Single<InterestedGoalsResponse> = goalService
        .getSearchGoalTitle(size, page, goalFilterBy.itemName, keyword)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
