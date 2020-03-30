package com.eroom.domain.koin.repository

import retrofit2.Retrofit

interface RetrofitRepository {
    fun getRefreshRetrofit(): Retrofit

    fun getAccessRetrofit(): Retrofit

    fun getGuestRetrofit(): Retrofit
}