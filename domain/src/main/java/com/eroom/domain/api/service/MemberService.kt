package com.eroom.domain.api.service

import com.eroom.data.request.IdListRequest
import com.eroom.data.request.NicknameRequest
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.data.response.UserInfoResponse
import io.reactivex.Single
import retrofit2.http.*

interface MemberService {
    @POST("member/nickname/duplicity")
    fun postNicknameDuplicity(@Body nickname: NicknameRequest): Single<Boolean>

    @PUT("member/nickname")
    fun putNickname(@Body nickname: NicknameRequest): Single<UserInfoResponse>

    @PUT("member/jobInterests")
    fun putJobInterests(@Body ids: IdListRequest): Single<Long>

    @GET("member")
    fun getMemberInfo(): Single<UserInfoResponse>

    @GET("member/jobInterests")
    fun getMemberJobInterests(): Single<ArrayList<JobGroupAndClassResponse>>
}