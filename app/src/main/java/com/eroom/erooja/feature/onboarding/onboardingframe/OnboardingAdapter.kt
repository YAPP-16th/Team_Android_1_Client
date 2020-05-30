package com.eroom.erooja.feature.onboarding.onboardingframe

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.eroom.erooja.feature.onboarding.onboardingpage.OnboardingFirst
import com.eroom.erooja.feature.onboarding.onboardingpage.OnboardingSecond
import com.eroom.erooja.feature.onboarding.onboardingpage.OnboardingThird
import timber.log.Timber

class OnboardingAdapter(fm: FragmentManager, private val activity: OnboardingActivity) :
    FragmentStatePagerAdapter(
        fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> activity.fragments[0] as Fragment
            1 -> activity.fragments[1] as Fragment
            else -> activity.fragments[2] as Fragment
        }
    }

    override fun getCount() = 3

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        //Log.e("FragmentPagerAdapter", "destroyItem position : $position")
        Timber.e("destroy item Position : $position")
    }
}
