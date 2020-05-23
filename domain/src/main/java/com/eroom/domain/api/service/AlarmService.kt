package com.eroom.domain.api.service

import com.eroom.data.response.UnCheckedAlarmsResponse
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
    ): Single<UnCheckedAlarmsResponse>

    @PUT("alarm/{alarmId}")
    fun putAlarmId(@Path("alarmId") alarmId: Long): Single<Long>
}