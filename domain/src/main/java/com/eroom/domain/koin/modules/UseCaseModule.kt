package com.eroom.domain.koin.modules

import com.eroom.domain.api.usecase.GetDummyUseCase
import org.koin.dsl.module

val usecaseModule = module {
    factory { GetDummyUseCase(get()) }
}