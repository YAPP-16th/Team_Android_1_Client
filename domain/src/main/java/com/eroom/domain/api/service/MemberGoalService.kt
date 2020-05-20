package com.eroom.domain.api.service

import com.eroom.data.request.AddMyGoalRequest
import com.eroom.data.request.GoalAbandonedRequest
import com.eroom.data.request.GoalReParticipateRequest
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
        @Query("end") end: Boolean
    ): Single<ParticipatedGoalsResponse>

    @GET("membergoal/{goalId}/todo")
    fun getTodoByGoalId(@Path("goalId") goalId: Long,
                        @Query("sort") sort: String,
                        @Query("size") size: Int): Single<TodoGoalListResponse>

    @GET("membergoal/{goalId}")
    fun getParticepantedList(
        @Path("goalId") goalId: Long,
        @Query("size") size: Int,
        @Query("page") page: Int
    ): Single<ParticipantedListResponse>

    @POST("membergoal")
    fun postAddMyGoal(@Body addMyGoalRequest: AddMyGoalRequest): Single<Response<AddGoalResponse>>

    @GET("membergoal/{goalId}/info")
    fun getInfoByGoalId(@Path("goalId") goalId: Long): Single<Response<ParticipatedGoalInfoResponse>>

    @PUT("membergoal/{goalId}")
    fun putGoalIsAbandoned(@Path("goalId") goalId: Long, @Body goalAbandonedRequest: GoalAbandonedRequest): Single<ParticipatedGoalInfoResponse>

    @PUT("membergoal/{goalId}")
    fun putGoalReparticipate(@Path("goalId") goalId: Long, @Body reParticipateRequest: GoalReParticipateRequest): Single<Response<ParticipatedGoalInfoResponse>>

}
