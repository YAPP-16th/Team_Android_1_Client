package com.eroom.domain.koin.repository

import okhttp3.OkHttpClient

interface HttpClientRepository {
    fun getRefreshOkHttp(): OkHttpClient

    fun getAccessOkHttp(): OkHttpClient

    fun getGuestOkHttp(): OkHttpClient
}