package com.eroom.erooja.app.feature.main

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.eroom.domain.utils.dpToPx
import com.eroom.domain.utils.loadUrl
import com.eroom.erooja.databinding.ItemCircleProfileBinding

class ItemCircleProfile(context: Context, profile: String, margin: Float) : FrameLayout(context) {
    init {
        initView(context, profile, margin)
    }

    private fun initView(context: Context, profile: String, margin: Float) {
        val binding = ItemCircleProfileBinding.inflate(LayoutInflater.from(context), this, true)

        val layoutSetting = LayoutParams(dpToPx(context, 27f).toInt(), dpToPx(context, 27f).toInt())
        layoutSetting.rightMargin = dpToPx(context, margin).toInt()
        binding.profile.loadUrl(profile)
        binding.profile.layoutParams = layoutSetting
    }
}