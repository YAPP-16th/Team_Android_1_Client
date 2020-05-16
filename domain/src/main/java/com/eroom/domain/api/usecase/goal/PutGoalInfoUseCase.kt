package com.eroom.domain.api.usecase.goal

import com.eroom.data.request.GoalInfoRequest
import com.eroom.domain.api.service.GoalService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PutGoalInfoUseCase(accessTokenRepository: AccessRetrofitRepository) {
    private val goalService = accessTokenRepository.getAccessRetrofit()
        .create(GoalService::class.java)

    fun putGoalInfo(goalId: Long, title: String, description: String) = goalService
        .putGoalInfo(goalId, GoalInfoRequest(title, description))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}