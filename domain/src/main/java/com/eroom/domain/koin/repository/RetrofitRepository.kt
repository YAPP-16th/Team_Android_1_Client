package com.eroom.domain.koin.repository

import retrofit2.Retrofit

interface RefreshRetrofitRepository {
    fun getRefreshRetrofit(): Retrofit
}

interface AccessRetrofitRepository {
    fun getAccessRetrofit(): Retrofit
}

interface GuestRetrofitRepository {
    fun getGuestRetrofit(): Retrofit
}