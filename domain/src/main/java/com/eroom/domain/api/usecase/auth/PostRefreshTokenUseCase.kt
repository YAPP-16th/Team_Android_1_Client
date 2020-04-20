package com.eroom.domain.api.usecase.auth

import com.eroom.data.response.TokenResponse
import com.eroom.domain.api.service.AuthService
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.RefreshRetrofitRepository
import com.eroom.domain.koin.repository.SharedPrefRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class PostRefreshTokenUseCase(retrofitRepository: RefreshRetrofitRepository, private val sharedPrefRepository: SharedPrefRepository) {
    private val authService = retrofitRepository
        .getRefreshRetrofit()
        .create(AuthService::class.java)

    fun postRefreshToken(): Single<TokenResponse> {
        sharedPrefRepository.writePrefs(Consts.TOKEN_TIME_KEY, Date().time)
        return authService.refreshToken()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}