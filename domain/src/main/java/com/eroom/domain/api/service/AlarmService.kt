package com.eroom.domain.api.service

import com.eroom.data.response.AlarmsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AlarmService {
    @GET("alarm/unchecked")
    fun getUnCheckedAlarms(
        @Query("page") page: Int,
        @Query("sort") sort: String,
        @Query("size") size: Int
    ): Single<AlarmsResponse>

    @PUT("alarm/{alarmId}")
    fun putAlarmId(@Path("alarmId") alarmId: Long): Single<Long>

    @GET("alarm")
    fun getNotifications(
        @Query("page") page: Int,
        @Query("sort") sort: String,
        @Query("size") size: Int
    ): Single<AlarmsResponse>
}