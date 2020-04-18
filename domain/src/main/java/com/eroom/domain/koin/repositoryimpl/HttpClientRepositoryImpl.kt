package com.eroom.domain.koin.repositoryimpl

import android.content.Context
import com.eroom.domain.BuildConfig
import com.eroom.domain.api.usecase.auth.PostRefreshTokenUseCase
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.HttpClientRepository
import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.sharedpref.SharedPreferenceHelper
import com.eroom.domain.utils.ConverterUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.*
import java.util.concurrent.TimeUnit

class HttpClientRepositoryImpl(private val sharedPrefRepository: SharedPrefRepository,
                               private val postRefreshTokenUseCase: PostRefreshTokenUseCase): HttpClientRepository {
    override fun getRefreshOkHttp(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain: Interceptor.Chain ->
            val refreshToken = ConverterUtil._Decode(sharedPrefRepository.getPrefsStringValue(Consts.PREF_REFRESH_TOKEN))
            val request = chain.request()
            if (refreshToken.isNullOrEmpty()) {
                request.newBuilder()
                    .method(request.method(), request.body())
                    .addHeader("refreshToken", refreshToken)
                    .build()
            }
            chain.proceed(request)
        }

        //log
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(loggingInterceptor)
        }

        // timeout
        httpClient.readTimeout(1, TimeUnit.MINUTES)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)

        return httpClient.build()
    }

    override fun getAccessOkHttp(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain: Interceptor.Chain ->
            var accessToken = ConverterUtil._Decode(sharedPrefRepository.getPrefsStringValue(Consts.PREF_ACCESS_TOKEN))
            val refreshTime = sharedPrefRepository.getPrefsLongValue(Consts.TOKEN_TIME_KEY)
            val newTime = Date().time
            if (newTime - refreshTime > 1200000) {
                accessToken = postRefreshTokenUseCase.postRefreshToken().blockingGet().token
                sharedPrefRepository.writePrefs(Consts.ACCESS_TOKEN, ConverterUtil._Encode(accessToken))
            }
            val request = chain.request()
            if (accessToken.isNullOrEmpty()) {
                request.newBuilder()
                    .method(request.method(), request.body())
                    .addHeader("accessToken", accessToken)
                    .build()
            }
            chain.proceed(request)
        }

        //log
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(loggingInterceptor)
        }

        // timeout
        httpClient.readTimeout(1, TimeUnit.MINUTES)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)

        return httpClient.build()
    }

    override fun getGuestOkHttp(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            request.newBuilder()
                .method(request.method(), request.body())
                .build()
            chain.proceed(request)
        }

        //log
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(loggingInterceptor)
        }

        // timeout
        httpClient.readTimeout(1, TimeUnit.MINUTES)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)

        return httpClient.build()
    }
}