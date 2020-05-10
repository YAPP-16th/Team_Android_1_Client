package com.eroom.domain.customview.listeners

import com.google.android.material.tabs.TabLayout

abstract class OnSingleTabSelectedListener: TabLayout.OnTabSelectedListener {

    abstract fun onSingleTabClick(tab: TabLayout.Tab?)

    override fun onTabSelected(tab: TabLayout.Tab?) {
        onSingleTabClick(tab)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        onSingleTabClick(tab)
    }
    override fun onTabUnselected(p0: TabLayout.Tab?) {}
}