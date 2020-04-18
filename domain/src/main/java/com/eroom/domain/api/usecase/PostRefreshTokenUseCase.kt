package com.eroom.domain.api.usecase

import com.eroom.data.response.TokenResponse
import com.eroom.domain.api.service.AuthService
import com.eroom.domain.koin.repository.RetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PostRefreshTokenUseCase(retrofitRepository: RetrofitRepository) {
    private val authService = retrofitRepository
        .getRefreshRetrofit()
        .create(AuthService::class.java)

    fun postRefreshToken(): Single<TokenResponse> = authService.refreshToken()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}