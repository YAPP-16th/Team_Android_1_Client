package com.eroom.erooja.feature.onboarding.onboardingframe

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.eroom.erooja.feature.onboarding.onboardingpage.OnboardingFirst
import com.eroom.erooja.feature.onboarding.onboardingpage.OnboardingSecond
import com.eroom.erooja.feature.onboarding.onboardingpage.OnboardingThird
import timber.log.Timber

class OnboardingAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(
    fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingFirst()
            1 -> OnboardingSecond()
            else -> OnboardingThird()  //??
        }
    }

    override fun getCount() = 3

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        //Log.e("FragmentPagerAdapter", "destroyItem position : $position")
        Timber.e("destroy item Position : $position")
    }
}
