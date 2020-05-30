package com.eroom.erooja.feature.onboarding.onboardingpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.fragment_onboarding1.*


class OnboardingFirst : Fragment(), OnboardingPage {

    companion object {
        @JvmStatic
        fun newInstance() = OnboardingFirst()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding1, container, false)
    }

    override fun loadLottieAnimation() {
        image1.playAnimation()
    }

}
