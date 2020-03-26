package com.eroom.domain.koin.repositoryimpl

import com.eroom.domain.BuildConfig
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.globalconst.UrlConst
import com.eroom.domain.koin.repository.HttpClientRepository
import com.eroom.domain.koin.repository.RetrofitRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRepositoryImpl(private val okHttpRepo: HttpClientRepository): RetrofitRepository {
    override fun getRefreshRetrofit(): Retrofit {
        val client = okHttpRepo.getRefreshOkHttp()
        val baseUrl: String = if (BuildConfig.Mode.toLowerCase().contains(Consts.APP_STAGING)) UrlConst.PROD_URL else UrlConst.DEV_URL
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    override fun getAccessRetrofit(): Retrofit {
        val client = okHttpRepo.getAccessOkHttp()
        val baseUrl: String = if (BuildConfig.Mode.toLowerCase().contains(Consts.APP_STAGING)) UrlConst.PROD_URL else UrlConst.DEV_URL
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    override fun getGuestRetrofit(): Retrofit {
        val client = okHttpRepo.getGuestOkHttp()
        val baseUrl: String = if (BuildConfig.Mode.toLowerCase().contains(Consts.APP_STAGING)) UrlConst.PROD_URL else UrlConst.DEV_URL
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }
}
