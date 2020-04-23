package com.eroom.domain.koin.modules

import com.eroom.domain.koin.repository.*
import com.eroom.domain.koin.repositoryimpl.*
import org.koin.dsl.module

val networkModule = module {
    single<RefreshClientRepository> { RefreshClientRepositoryImpl(get()) }

    single<AccessClientRepository> { AccessClientRepositoryImpl(get(), get()) }

    single<GuestClientRepository> { GuestClientRepositoryImpl() }

    single<RefreshRetrofitRepository> { RefreshRetrofitRepositoryImpl(get()) }

    single<AccessRetrofitRepository> { AccessRetrofitRepositoryImpl(get()) }

    single<GuestRetrofitRepository> { GuestRetrofitRepositoryImpl(get()) }
}