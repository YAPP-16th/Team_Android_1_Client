package com.eroom.erooja.feature.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityLoginBinding
import com.eroom.erooja.feature.signup.kakao.KakaoSignUpActivity
import com.eroom.erooja.feature.tab.TabActivity
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import timber.log.Timber


class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var presenter: LoginPresenter

    private lateinit var callback: SessionCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
    }

    private fun initPresenter() {
        presenter =
            LoginPresenter(this)
    }

    private fun setUpDataBinding() {
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginBinding.activity = this
    }

    private fun initView() {
        callback = SessionCallback(redirectSignUpActivity)
        Session.getCurrentSession().addCallback(callback)
        Session.getCurrentSession().checkAndImplicitOpen()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    private class SessionCallback(val redirectSignUpActivity: () -> Unit) : ISessionCallback {
        override fun onSessionOpened() {
            redirectSignUpActivity()
        }

        override fun onSessionOpenFailed(exception: KakaoException) {
            Timber.e("$exception")
        }
    }

    private val redirectSignUpActivity = {
        val intent = Intent(this, KakaoSignUpActivity::class.java)
        startActivity(intent)
        finish()
    }
}