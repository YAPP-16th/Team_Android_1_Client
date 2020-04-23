package com.eroom.domain.api.service

import com.eroom.data.request.NewGoalRequest
import com.eroom.data.response.NewGoalResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface GoalService {
    @POST("goal")
    fun postNewGoal(@Body newGoalRequest: NewGoalRequest): Single<NewGoalResponse>
}