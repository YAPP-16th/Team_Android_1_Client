package com.eroom.erooja.feature.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivitySplashBinding
import com.eroom.erooja.feature.login.LoginActivity
import com.eroom.erooja.feature.onboarding.onboardingframe.OnboardingActivity
import com.eroom.erooja.feature.signup.kakao.KakaoSignUpActivity
import com.eroom.erooja.feature.tab.TabActivity
import org.koin.android.ext.android.get

class SplashActivity : AppCompatActivity(), SplashContract.View {
    private lateinit var splashBinding: ActivitySplashBinding
    private lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
    }

    private fun initPresenter() {
        presenter = SplashPresenter(this, get(), get())
    }

    private fun setUpDataBinding() {
        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        splashBinding.activity = this
    }

    override fun onResume() {
        super.onResume()
        splashBinding.logoImageView.alpha = 0f
        presenter.initDelay()
    }

    override fun animation() =
        splashBinding.logoImageView.animate()
            .apply {
                repeat(10) {
                    alpha(0.1f * it)
                    duration = 200
                }
            }.start()

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    override fun navigateToOnBoarding() {
        startActivity(Intent(this, OnboardingActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    override fun navigateToMain() {
        startActivity(Intent(this, TabActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    override fun navigateToSignUp() {
        startActivity(Intent(this, KakaoSignUpActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}
