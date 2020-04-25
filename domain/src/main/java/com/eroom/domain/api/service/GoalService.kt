package com.eroom.domain.api.service

import com.eroom.data.request.NewGoalRequest
import com.eroom.data.response.GoalDetailResponse
import com.eroom.data.response.InterestedGoalsResponse
import com.eroom.data.response.NewGoalResponse
import com.eroom.data.response.SearchGoalResponse
import io.reactivex.Single
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GoalService {
    @POST("goal")
    fun postNewGoal(@Body newGoalRequest: NewGoalRequest): Single<NewGoalResponse>

    @GET("goal/interest/{interestId}")
    fun getInterestedGoals(
        @Path("interestId") interestId: Long,
        @Query("size") size: Int,
        @Query("page") page: Int
    ): Single<InterestedGoalsResponse>

    @GET("goal")
    fun getSearchGoal(@Query("jobInterestIds") jobInterestIds: Long): Single<ArrayList<SearchGoalResponse>>

    @GET("goal/{goalId}")
    fun getGoalDetail(@Path("goalId") goalId: Long): Single<GoalDetailResponse>
}