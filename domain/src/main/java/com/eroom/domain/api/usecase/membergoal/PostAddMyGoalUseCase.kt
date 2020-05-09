package com.eroom.domain.api.usecase.membergoal

import com.eroom.data.request.AddMyGoalRequest
import com.eroom.data.response.AddGoalResponse
import com.eroom.domain.api.service.MemberGoalService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class PostAddMyGoalUseCase(accessRetrofitRepository: AccessRetrofitRepository) {
    private val memberGoalService = accessRetrofitRepository
        .getAccessRetrofit()
        .create(MemberGoalService::class.java)

    fun postAddMyGoal(addMyGoalRequest: AddMyGoalRequest) : Single<Response<AddGoalResponse>> = memberGoalService
        .postAddMyGoal(addMyGoalRequest)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}