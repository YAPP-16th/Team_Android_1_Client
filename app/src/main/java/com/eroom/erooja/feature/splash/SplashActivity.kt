package com.eroom.erooja.feature.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivitySplashBinding
import com.eroom.erooja.feature.login.LoginActivity
import com.eroom.erooja.feature.tab.TabActivity

class SplashActivity : AppCompatActivity(), SplashContract.View {
    private lateinit var splashBinding: ActivitySplashBinding
    private lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initDelay()
    }

    private fun initPresenter() {
        presenter = SplashPresenter(this)
    }

    private fun setUpDataBinding() {
        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        splashBinding.activity = this
    }

    private fun initDelay() {
        val handler = Handler()
        handler.postDelayed(SplashHandler(), 1000)
    }

    inner class SplashHandler : Runnable {
        override fun run() {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }
}
