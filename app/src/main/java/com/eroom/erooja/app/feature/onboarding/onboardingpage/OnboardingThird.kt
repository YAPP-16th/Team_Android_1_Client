package com.eroom.erooja.app.feature.onboarding.onboardingpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eroom.domain.utils.loadGif
import com.eroom.erooja.R
import kotlinx.android.synthetic.main.fragment_onboarding3.view.*

class OnboardingThird : Fragment(), OnboardingPage {
    companion object {
        @JvmStatic
        fun newInstance() = OnboardingThird()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding3, container, false)
        view.imageView4.loadGif(R.raw.onboarding3)
        return view
    }

    override fun loadLottieAnimation() {}
}