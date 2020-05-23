package com.eroom.domain.api.service

import com.eroom.data.request.GoalInfoRequest
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
    fun getSearchJobInterest(@Path("interestId") interestId: Long?,
                             @Query("size") size: Int,
                             @Query("page") page: Int,
                             @Query("sort") sort: String,
                             @Query("uid") uid: String
    ): Single<InterestedGoalsResponse>
//    *size : 한 페이지당 크기
//    *page : 페이지번호
//    *sort : 정렬방식
    @GET("goal")
    fun getSearchGoalTitle(
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("goalFilterBy") goalFilterBy: String,
        @Query("keyword") keyword: String?
    ): Single<InterestedGoalsResponse>

    @GET("goal/{goalId}")
    fun getGoalDetail(@Path("goalId") goalId: Long): Single<GoalDetailResponse>

    @PUT("goal/{goalId}")
    fun putGoalInfo(@Path("goalId") goalId: Long, @Body goalInfoRequest: GoalInfoRequest): Single<GoalDetailResponse>
}