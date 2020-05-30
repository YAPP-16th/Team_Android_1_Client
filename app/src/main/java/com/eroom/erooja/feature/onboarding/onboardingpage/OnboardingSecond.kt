package com.eroom.erooja.feature.onboarding.onboardingpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.fragment_onboarding1.*
import kotlinx.android.synthetic.main.fragment_onboarding2.*
import kotlinx.android.synthetic.main.fragment_onboarding3.view.*

class OnboardingSecond : Fragment(), OnboardingPage {

    companion object {
        @JvmStatic
        fun newInstance() = OnboardingSecond()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding2, container, false)
        return view
    }

    override fun loadLottieAnimation() {
        imageView3.playAnimation()
    }
}