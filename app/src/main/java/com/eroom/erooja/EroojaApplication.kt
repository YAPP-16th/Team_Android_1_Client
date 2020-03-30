package com.eroom.erooja

import android.app.Application
import com.eroom.domain.koin.modules.networkModule
import com.eroom.domain.koin.modules.sharedPrefModule
import com.eroom.domain.koin.modules.usecaseModule
import com.kakao.auth.*
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

        KakaoSDK.init(KakaoSDKAdapter(this))
    }

    private class KakaoSDKAdapter(val application: EroojaApplication) : KakaoAdapter() {
        override fun getSessionConfig(): ISessionConfig {
            return object : ISessionConfig {
                override fun getAuthTypes(): Array<AuthType> {
                    return arrayOf(AuthType.KAKAO_LOGIN_ALL)
                }

                override fun isUsingWebviewTimer(): Boolean {
                    return false
                }

                override fun isSecureMode(): Boolean {
                    return false
                }

                override fun getApprovalType(): ApprovalType? {
                    return ApprovalType.INDIVIDUAL
                }

                override fun isSaveFormData(): Boolean {
                    return true
                }
            }
        }

        override fun getApplicationConfig(): IApplicationConfig {
            return IApplicationConfig {
                application.applicationContext
            }

        }
    }
}