package com.eroom.domain.koin.repositoryimpl

import android.annotation.SuppressLint
import com.eroom.domain.BuildConfig
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.globalconst.UrlConst
import com.eroom.domain.koin.repository.HttpClientRepository
import com.eroom.domain.koin.repository.RetrofitRepository
import com.eroom.domain.utils.ConverterUtil
import com.eroom.domain.utils.add
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRepositoryImpl(private val okHttpRepo: HttpClientRepository): RetrofitRepository {
    private val versionInfo = "api/v1/"

    @SuppressLint("DefaultLocale")
    override fun getRefreshRetrofit(): Retrofit {
        val client = okHttpRepo.getRefreshOkHttp()
        val baseUrl: String = if (BuildConfig.Mode.toLowerCase().contains(Consts.APP_STAGING))
            ConverterUtil._Decode(UrlConst.PROD_URL) else ConverterUtil._Decode(UrlConst.DEV_URL)
        return Retrofit.Builder()
            .baseUrl(baseUrl add versionInfo)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @SuppressLint("DefaultLocale")
    override fun getAccessRetrofit(): Retrofit {
        val client = okHttpRepo.getAccessOkHttp()
        val baseUrl: String = if (BuildConfig.Mode.toLowerCase().contains(Consts.APP_STAGING))
            ConverterUtil._Decode(UrlConst.PROD_URL) else ConverterUtil._Decode(UrlConst.DEV_URL)
        return Retrofit.Builder()
            .baseUrl(baseUrl add versionInfo)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @SuppressLint("DefaultLocale")
    override fun getGuestRetrofit(): Retrofit {
        val client = okHttpRepo.getGuestOkHttp()
        val baseUrl: String = if (BuildConfig.Mode.toLowerCase().contains(Consts.APP_STAGING))
            ConverterUtil._Decode(UrlConst.PROD_URL) else ConverterUtil._Decode(UrlConst.DEV_URL)
        return Retrofit.Builder()
            .baseUrl(baseUrl add versionInfo)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }
}
