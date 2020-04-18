package com.eroom.domain.koin.modules

import com.eroom.domain.api.usecase.PostKakaoLoginUseCase
import com.eroom.domain.api.usecase.PostRefreshTokenUseCase
import org.koin.dsl.module

val usecaseModule = module {
    factory { PostRefreshTokenUseCase(get()) }

    factory { PostKakaoLoginUseCase(get()) }
}