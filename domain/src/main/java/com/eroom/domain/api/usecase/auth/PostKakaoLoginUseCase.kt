package com.eroom.domain.api.usecase.auth

import com.eroom.data.request.KakaoLoginRequest
import com.eroom.data.response.TokenResponse
import com.eroom.domain.api.service.AuthService
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.RetrofitRepository
import com.eroom.domain.koin.repository.SharedPrefRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class PostKakaoLoginUseCase(retrofitRepository: RetrofitRepository, private val sharedPrefRepository: SharedPrefRepository) {
    private val authService = retrofitRepository
        .getGuestRetrofit()
        .create(AuthService::class.java)

    fun postKakaoLogin(kakaoId: Long): Single<TokenResponse> {
        sharedPrefRepository.writePrefs(Consts.TOKEN_TIME_KEY, Date().time)
        return authService
            .kakaoLogin(by = "ID", kakaoLoginRequest = KakaoLoginRequest(kakaoId.toString()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}