package com.eroom.domain.api.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MemberGoalService {
    @GET("membergoal")
    fun getGoalsByUserId(@Query("uid") uid: String,
                         @Query("size") size: Int,
                         @Query("page") page: Int,
                         @Query("sortBy") sortBy: String,
                         @Query("direction") direction: String,
                         @Query("endDtIsBeforeNow") endDtIsBeforeNow: Boolean
    )
}