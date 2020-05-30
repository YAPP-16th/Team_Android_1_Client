package com.eroom.erooja.feature.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.eroom.erooja.databinding.ItemCircleJoinCountBinding

@SuppressLint("ViewConstructor")
class ItemCircleJoinCount(context: Context, count: Int) : FrameLayout(context) {
    init {
        initView(context, count)
    }

    @SuppressLint("SetTextI18n")
    private fun initView(context: Context, count: Int) {
        val binding = ItemCircleJoinCountBinding.inflate(LayoutInflater.from(context), this, true)

        binding.count.text = "+$count"
    }
}