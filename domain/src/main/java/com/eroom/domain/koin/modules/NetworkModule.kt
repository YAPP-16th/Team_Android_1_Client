package com.eroom.domain.koin.modules

import com.eroom.domain.koin.repository.HttpClientRepository
import com.eroom.domain.koin.repository.RetrofitRepository
import com.eroom.domain.koin.repositoryimpl.HttpClientRepositoryImpl
import com.eroom.domain.koin.repositoryimpl.RetrofitRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val networkModule = module {
    single<HttpClientRepository> { HttpClientRepositoryImpl(get(), get()) }

    single<RetrofitRepository> { RetrofitRepositoryImpl(get()) }
}