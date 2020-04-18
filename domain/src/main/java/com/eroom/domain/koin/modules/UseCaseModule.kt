package com.eroom.domain.koin.modules

import com.eroom.domain.api.usecase.auth.PostKakaoLoginUseCase
import com.eroom.domain.api.usecase.auth.PostRefreshTokenUseCase
import com.eroom.domain.api.usecase.member.GetNicknameDuplicityUseCase
import org.koin.dsl.module

val usecaseModule = module {
    factory { PostRefreshTokenUseCase(get(), get()) }

    factory { PostKakaoLoginUseCase(get(), get()) }

    factory { GetNicknameDuplicityUseCase(get()) }
}