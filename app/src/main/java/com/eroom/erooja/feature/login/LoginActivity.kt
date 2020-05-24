package com.eroom.erooja.feature.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityLoginBinding
import com.eroom.erooja.feature.signup.kakao.KakaoSignUpActivity
import com.eroom.erooja.feature.tab.TabActivity
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import org.koin.android.ext.android.get
import timber.log.Timber
import java.util.concurrent.Future


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
            LoginPresenter(this, get(), get())
    }

    private fun setUpDataBinding() {
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginBinding.activity = this
    }

    private fun initView() {
        callback = SessionCallback(presenter.requestMe, startBlockAnimation)
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
        presenter.onCleared()
    }

    private class SessionCallback(val requestMe: () -> Future<MeV2Response>, val startAnimation: () -> Unit) : ISessionCallback {
        override fun onSessionOpened() {
            startAnimation()
            requestMe()
        }

        override fun onSessionOpenFailed(exception: KakaoException) {
            Timber.e("$exception")
        }
    }

    override val redirectSignUpActivity = { nickname: String? ->
        val intent = Intent(this, KakaoSignUpActivity::class.java).apply { putExtra(Consts.NICKNAME, nickname ?: "") }
        startActivity(intent)
    }

    override val redirectMainActivity = {
        val intent = Intent(this, TabActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun kakaoLoginButtonClicked() = loginBinding.comKakaoLogin.performClick()

    private val startBlockAnimation = {
        loginBinding.colorLoading.visibility = View.GONE
        loginBinding.blockView.visibility = View.VISIBLE
        loginBinding.whiteLoading.visibility = View.VISIBLE
        loginBinding.colorLoading.cancelAnimation()
        loginBinding.whiteLoading.playAnimation()
    }

    fun startAnimation() {
        loginBinding.blockView.visibility = View.GONE
        loginBinding.whiteLoading.visibility = View.GONE
        loginBinding.colorLoading.visibility = View.VISIBLE
        loginBinding.whiteLoading.cancelAnimation()
        loginBinding.colorLoading.playAnimation()
    }

    override fun stopAnimation() {
        loginBinding.blockView.visibility = View.GONE
        loginBinding.whiteLoading.visibility = View.GONE
        loginBinding.colorLoading.visibility = View.GONE
        loginBinding.whiteLoading.cancelAnimation()
        loginBinding.colorLoading.cancelAnimation()
    }
}
