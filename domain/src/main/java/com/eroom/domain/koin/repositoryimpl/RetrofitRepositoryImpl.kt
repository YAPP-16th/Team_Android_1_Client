package com.eroom.domain.koin.repositoryimpl

import android.annotation.SuppressLint
import com.eroom.domain.BuildConfig
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.globalconst.UrlConst
import com.eroom.domain.globalconst.UrlConst.VERSION_INFO
import com.eroom.domain.koin.repository.*
import com.eroom.domain.utils.ConverterUtil
import com.eroom.domain.utils.add
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RefreshRetrofitRepositoryImpl(private val okHttpRepo: RefreshClientRepository): RefreshRetrofitRepository {
    @SuppressLint("DefaultLocale")
    override fun getRefreshRetrofit(): Retrofit {
        val client = okHttpRepo.getRefreshOkHttp()
        val baseUrl: String = if (BuildConfig.Mode.toLowerCase().contains(Consts.APP_STAGING))
            ConverterUtil._Decode(UrlConst.PROD_URL) else ConverterUtil._Decode(UrlConst.DEV_URL)
        return Retrofit.Builder()
            .baseUrl(baseUrl add VERSION_INFO)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }
}

class AccessRetrofitRepositoryImpl(private val okHttpRepo: AccessClientRepository): AccessRetrofitRepository {
    @SuppressLint("DefaultLocale")
    override fun getAccessRetrofit(): Retrofit {
        val client = okHttpRepo.getAccessOkHttp()
        val baseUrl: String = if (BuildConfig.Mode.toLowerCase().contains(Consts.APP_STAGING))
            ConverterUtil._Decode(UrlConst.PROD_URL) else ConverterUtil._Decode(UrlConst.DEV_URL)
        return Retrofit.Builder()
            .baseUrl(baseUrl add VERSION_INFO)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }
}

class GuestRetrofitRepositoryImpl(private val okHttpRepo: GuestClientRepository): GuestRetrofitRepository {
    @SuppressLint("DefaultLocale")
    override fun getGuestRetrofit(): Retrofit {
        val client = okHttpRepo.getGuestOkHttp()
        val baseUrl: String = if (BuildConfig.Mode.toLowerCase().contains(Consts.APP_STAGING))
            ConverterUtil._Decode(UrlConst.PROD_URL) else ConverterUtil._Decode(UrlConst.DEV_URL)
        return Retrofit.Builder()
            .baseUrl(baseUrl add VERSION_INFO)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }
}
