package com.eroom.erooja

import android.app.Application
import com.eroom.domain.koin.modules.networkModule
import com.eroom.domain.koin.modules.sharedPrefModule
import com.eroom.domain.koin.modules.usecaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EroojaApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@EroojaApplication)
            modules(mutableListOf(networkModule, usecaseModule, sharedPrefModule))
        }
    }
}