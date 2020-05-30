package com.eroom.erooja.app.feature.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivitySplashBinding
import com.eroom.erooja.app.feature.login.LoginActivity
import com.eroom.erooja.app.feature.onboarding.onboardingframe.OnboardingActivity
import com.eroom.erooja.app.feature.signup.kakao.KakaoSignUpActivity
import com.eroom.erooja.app.feature.tab.TabActivity
import com.eroom.erooja.app.singleton.JobClassHashMap
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
        presenter = SplashPresenter(this, get(), get(), get(), get())
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

    override fun reRequestClassByGroup(jobGroupList: ArrayList<JobGroup>) =
        jobGroupList.map {
            it.id //직군 (디자인 or 개발) 불러오기
        }.toList().let {
            presenter.getJobGroupAndClasses(it)
        }

    //직무 직군 object 에 다 가져오는 함수임
    override fun updateJobGroupAndClass(result: List<JobGroupAndClassResponse>) {
        for (i in result) {
            for (j in i.jobInterests) {
                JobClassHashMap.hashmap[j.id] = j.name
            }
        }
    }

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

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}
