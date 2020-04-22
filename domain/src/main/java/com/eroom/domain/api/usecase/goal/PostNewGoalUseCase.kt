package com.eroom.domain.api.usecase.goal

import com.eroom.data.request.NewGoalRequest
import com.eroom.data.response.NewGoalResponse
import com.eroom.domain.api.service.GoalService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PostNewGoalUseCase(retrofitRepository: AccessRetrofitRepository) {
    private val memberService = retrofitRepository
        .getAccessRetrofit()
        .create(GoalService::class.java)

    fun postNewGoal(newGoalRequest: NewGoalRequest): Single<NewGoalResponse> = memberService
        .postNewGoal(newGoalRequest)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}