package com.eroom.domain.koin.modules

import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.koin.repositoryimpl.SharedPrefRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val sharedPrefModule = module {
    single<SharedPrefRepository> { SharedPrefRepositoryImpl(androidApplication()) }
}