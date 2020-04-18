package com.eroom.domain.api.usecase

import com.eroom.data.request.KakaoLoginRequest
import com.eroom.data.response.TokenResponse
import com.eroom.domain.api.service.AuthService
import com.eroom.domain.koin.repository.RetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PostKakaoLoginUseCase(retrofitRepository: RetrofitRepository) {
    private val authService = retrofitRepository
        .getGuestRetrofit()
        .create(AuthService::class.java)

    fun postKakaoLogin(kakaoId: Long): Single<TokenResponse> = authService
        .kakaoLogin(by = "ID", kakaoLoginRequest = KakaoLoginRequest(kakaoId.toString()))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}