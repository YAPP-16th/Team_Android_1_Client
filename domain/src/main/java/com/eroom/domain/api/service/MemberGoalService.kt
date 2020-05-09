package com.eroom.domain.api.service

import com.eroom.data.request.AddMyGoalRequest
import com.eroom.data.response.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface MemberGoalService {
    @GET("membergoal")
    fun getGoalsByUserId(
        @Query("uid") uid: String,
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("sortBy") sortBy: String,
        @Query("direction") direction: String,
        @Query("endDtIsBeforeNow") endDtIsBeforeNow: Boolean
    ): Single<ParticipatedGoalsResponse>

    @GET("membergoal/{goalId}/todo")
    fun getTodoByGoalId(@Path("goalId") goalId: Long): Single<TodoGoalListResponse>

    @GET("membergoal/{goalId}")
    fun getParticepantedList(
        @Path("goalId") goalId: Long,
        @Query("size") size: Int,
        @Query("page") page: Int
    ): Single<ParticipantedListResponse>

    @POST("membergoal")
    fun postAddMyGoal(@Body addMyGoalRequest: AddMyGoalRequest): Single<Response<AddGoalResponse>>
}