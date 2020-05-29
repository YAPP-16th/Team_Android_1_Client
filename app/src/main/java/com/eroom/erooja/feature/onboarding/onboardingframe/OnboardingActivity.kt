package com.eroom.erooja.feature.onboarding.onboardingframe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.eroom.domain.utils.loadDrawable
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityOnboardingBinding
import com.eroom.erooja.feature.login.LoginActivity
import com.eroom.erooja.feature.onboarding.onboardingpage.OnboardingFirst
import com.eroom.erooja.feature.onboarding.onboardingpage.OnboardingPage
import com.eroom.erooja.feature.onboarding.onboardingpage.OnboardingSecond
import com.eroom.erooja.feature.onboarding.onboardingpage.OnboardingThird
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity(), OnboardingContract.View {
    private lateinit var onboardBinding: ActivityOnboardingBinding
    private val pagerAdapter by lazy { OnboardingAdapter(supportFragmentManager, this) }
    private val presenter by lazy { OnboardingPresenter(this) }
    private var isLastPage = false
    val fragments : List<OnboardingPage> = listOf(
        OnboardingFirst.newInstance(),
        OnboardingSecond.newInstance(),
        OnboardingThird.newInstance()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initViewPager()
    }

    private fun setUpDataBinding() {
        onboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)
        onboardBinding.activity = this
    }

    private fun initViewPager() {
        onboardBinding.viewPager.adapter = pagerAdapter
        onboardBinding.viewPager.offscreenPageLimit = 2

        onboardBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }
            override fun onPageSelected(p0: Int) {
                circle_indicator.selectDot(p0)
                fragments[p0].loadLottieAnimation()
                if (p0 == 2) {
                    onboardBinding.btnNext.loadDrawable(resources.getDrawable(R.drawable.btn_start_cta, null))
                    isLastPage = true
                }
                else if (isLastPage) {
                    isLastPage = false
                    onboardBinding.btnNext.loadDrawable(resources.getDrawable(R.drawable.btn_next_normal, null))
                }
            }
        })

        onboardBinding.circleIndicator.createDotPanel(3, R.drawable.indicator_dot_off, R.drawable.indicator_dot_on, 0)
    }

    fun onNextClickListener(v: View) {
        val position: Int = onboardBinding.viewPager.currentItem
        if (position == 2) {
            finishOnboarding()
        }
        onboardBinding.viewPager.setCurrentItem(position + 1, true)
    }

    fun onSkipClickListener(v: View) {
        finishOnboarding()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        var position: Int = onboardBinding.viewPager.currentItem
        onboardBinding.viewPager.setCurrentItem(position - 1, true)
    }

    private fun finishOnboarding() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}


