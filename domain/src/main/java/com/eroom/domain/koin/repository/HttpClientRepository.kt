package com.eroom.domain.koin.repository

import okhttp3.OkHttpClient

interface RefreshClientRepository {
    fun getRefreshOkHttp(): OkHttpClient
}

interface AccessClientRepository {
    fun getAccessOkHttp(): OkHttpClient
}

interface GuestClientRepository {
    fun getGuestOkHttp(): OkHttpClient
}