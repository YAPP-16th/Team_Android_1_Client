package com.eroom.domain.api.service

import com.eroom.data.request.NewGoalRequest
import com.eroom.data.response.InterestedGoalsResponse
import com.eroom.data.response.NewGoalResponse
import io.reactivex.Single
import retrofit2.http.*

interface GoalService {
    @POST("goal")
    fun postNewGoal(@Body newGoalRequest: NewGoalRequest): Single<NewGoalResponse>

    @GET("goal/interest/{interestId}")
    fun getInterestedGoals(
        @Path("interestId") interestId: Long,
        @Query("size") size: Int,
        @Query("page") page: Int
    ): Single<InterestedGoalsResponse>
}