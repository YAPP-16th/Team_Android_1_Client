package com.eroom.erooja.feature.onboarding.onboardingframe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eroom.erooja.R

class OnboardingActivity : AppCompatActivity(), OnboardingContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_onboarding3)
    }
}
