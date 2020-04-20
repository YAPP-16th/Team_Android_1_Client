package com.eroom.domain.api.service

import com.eroom.data.request.KakaoLoginRequest
import com.eroom.data.response.TokenResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @GET("auth/token/refresh")
    fun refreshToken(): Single<TokenResponse>

    @POST("auth/kakao")
    fun kakaoLogin(@Query("by") by: String, @Body kakaoLoginRequest: KakaoLoginRequest): Single<TokenResponse>
}