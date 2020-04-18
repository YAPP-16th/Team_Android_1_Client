package com.eroom.erooja.feature.splash

interface SplashContract {
    interface View {
        fun animation()
        fun navigateToLogin()
        fun navigateToOnBoarding()
        fun navigateToMain()
        fun navigateToSignUp()
    }

    interface Presenter {
        val view: View
        fun initDelay()
    }
}