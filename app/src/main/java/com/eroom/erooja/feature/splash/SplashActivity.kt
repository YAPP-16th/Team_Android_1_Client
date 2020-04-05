package com.eroom.erooja.feature.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.eroom.domain.globalconst.UrlConst
import com.eroom.domain.utils.ConverterUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivitySplashBinding
import com.eroom.erooja.feature.login.LoginActivity
import com.eroom.erooja.feature.tab.TabActivity
import timber.log.Timber

class SplashActivity : AppCompatActivity(), SplashContract.View {
    private lateinit var splashBinding: ActivitySplashBinding
    private lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        setTimber()
    }

    private fun initPresenter() {
        presenter = SplashPresenter(this)
    }

    private fun setUpDataBinding() {
        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        splashBinding.activity = this
    }
    
    private fun setTimber() = Timber.plant(Timber.DebugTree())

    override fun onResume() {
        super.onResume()
        splashBinding.logoImageView.alpha = 0f
        initDelay()
    }

    private fun initDelay() {
        Handler().postDelayed(AnimationHandler(), 800)
        Handler().postDelayed(SplashHandler(), 3000)
    }

    inner class SplashHandler : Runnable {
        override fun run() {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }

    inner class AnimationHandler : Runnable {
        override fun run() {
            splashBinding.logoImageView.animate()
                .apply {
                    repeat(10) {
                        alpha(0.1f * it)
                        duration = 200
                    }
                }.start()
        }
    }
}
