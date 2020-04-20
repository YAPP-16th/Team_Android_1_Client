package com.eroom.domain.api.service

import com.eroom.data.request.NicknameRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MemberService {
    @POST("member/nickname/duplicity")
    fun getNicknameDuplicity(@Body nickname: NicknameRequest): Single<Boolean>
}